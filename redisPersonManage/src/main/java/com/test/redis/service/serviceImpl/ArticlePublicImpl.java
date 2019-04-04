package com.test.redis.service.serviceImpl;

import com.test.redis.pojo.Article;
import com.test.redis.service.ArticlePublish;
import com.test.redis.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 文章的录入和投票 一共使用了两个hash 一个用户,一个文章, 一个set 用来存放某一文章投票的用户
 *
 * 两个zset 用来存放文件的录入时间和分数
 * 两个字符串,用来获取自增的值,
 */
@Service
public class ArticlePublicImpl implements ArticlePublish {
    private String vote = null;
    private String time = null;
    private String user = null;
    private String article = null;
    private String score = null;
    private static final  double incrScore = 432;
    @Autowired
    RedisUtilForStringAndList redisUtilForStringAndList;

    @Autowired
    RedisForSet redisForSet;

    @Autowired
    RedisUtilForMap redisUtilForMap;

    @Autowired
    RedisForZset redisForZset;

    /**
     * 发布文章,将文章的信息写入散列,然后将文章的发布时间和分值分别写入两个zset中
     * 将发部分作为一个投票人放入投票人的行列.设置投票人的set的过期时间.
     * 实现分组的功能,将 分组的信息 也写进去,方便后面使用inter进行分组查询,
     * @param article
     * @param userId
     * @return
     */
    @Override
    public boolean publish(Article article , String  userId) {
        //首先生成一个新的文章的id,如果没有就新建一个,并且录入;
        long articleId = redisUtilForStringAndList.strIncr( RedisPrefix.ARTICLE , 1 );
        article.setId( articleId );
        //设置文章的前缀加文件编号的字符串
        String articleStrId = RedisPrefix.ARTICLE + article.getId();
        //设置某文章投票的人的集合名称
        vote = RedisPrefix.VOTE + articleId;
        //使用不重复无序集合保存录入的人的id
        redisForSet.sAdd( vote , RedisPrefix.USER + userId );
        //设置过期时间为1周
        redisUtilForStringAndList.expire( vote, 24 * 7 * 60 * 60 );
        //设置录入时间
        article.setTime( System.currentTimeMillis() );
        //在hash中录入值
        Map<Object , Object > map = new HashMap<>();
        map.put( "title" , article.getTitle() );
        map.put( "votes" , redisForSet.sSize( vote ) );
        map.put( "time" , article.getTime() );
        map.put( "poster" , RedisPrefix.USER + userId );
        map.put( "id" , article.getId() );
        map.put( "group" , article.getGroup() );
        redisUtilForMap.hashPutAll( articleStrId , map );
        /**
         * 设置文章的发布时间和初始分值
         */
        redisForZset.zAdd( RedisPrefix.TIME , articleStrId , System.currentTimeMillis() );
        redisForZset.zAdd( RedisPrefix.SCORE , articleStrId , System.currentTimeMillis() + incrScore );
        /***
         * 在分组中添加文件的信息.放到一个散列里面
         */
        redisForSet.sAdd( RedisPrefix.GROUP + article.getGroup() , articleStrId  );
        return true;
    }

    /**
     * 实现投票功能, 需要获取文章和用户id
     * 先进行时间判断,看一下是不是能投票
     * 1. 在vote中放上用户的值
     * 2. 在文章的vote属性上加1
     * 3.在分数表里加上一个常量
     *
     */

    public boolean voteArticle( String userId , String articleId ) {
        long time = (long) redisUtilForMap.hashGet( articleId , "time");
        System.out.println( time );
        if ( System.currentTimeMillis() - 7 * 24 * 60 * 60 > time ) {
            return false;
        }
        String[] articleIds = articleId.split(":");
        //1. 在vote中放上用户的值
        redisForSet.sAdd( RedisPrefix.VOTE + articleIds[1] , RedisPrefix.USER + userId );
        //2.在文章的vote属性上加1
        redisUtilForMap.hashIncrDouble( articleId , "vote" , 1);
        //3.在分数表里加上一个常量
        redisForZset.zIncrScore( RedisPrefix.SCORE , articleId , incrScore );
        return true;
    }

    /**
     * 从分数有序集合中倒序(分值从大到小)取出所有的文章的id,放到set集合中,并且将所有文章的信息都取出来
     * 放到List中并返回
     * @param article 用来传值,查找指定的文章信息,没写
     * @return
     */
    public List<Article> getArticle( Article article ) {
        List<Article> lists = new ArrayList<>();
        Article articleGet = new Article();
        //score:的有序散列中包含着所有的hash的key,可以取出所有的值,然后在找hash中的属性和值,倒序
        //如果限制个数的话,可以在这里限制. page * pageSize , page * pageSize + pageSize
        Set<String> articleKeys = redisForZset.zReverseRange("score:" , 0 ,-1 );
        //通过key进行寻找,找到所有的用户的属性,写入List中
        for ( String articleKey : articleKeys ) {
            //测试放在Set中之后是不是有序的.
            System.out.println( articleKey );
            //获取所有的键和值.
            Map<Object , Object > map = redisUtilForMap.hashEntries( articleKey );
            articleGet.setTitle( (String) map.get("title") );
            articleGet.setId( (long) map.get("id") );
            articleGet.setVotes( (int) map.get("votes") );
            articleGet.setGroup( (String) map.get("group") );
            articleGet.setPoster( (String) map.get("poster") );
            articleGet.setTime( (long) map.get("time") );
            lists.add( articleGet );
        }
        return lists;
    }

    /**
     * 先找一下这个key 是不是存在
     * 找到这个分组集合然后和接放着所有文章id信息成绩的集合一起,进行交集.安分组查找的时候也要分值排序
     * 将交集的结果设为60秒自动过期
     * @param groupName
     * @return
     */
    public List<Article> getArticleByGroup( String groupName )  {
        long size= 0;
        //用来记录群组文章的分数值的键
        String groupZset = RedisPrefix.GROUPZSET + groupName;
        //如果不存在的话,就做交集,
        if( !redisUtilForStringAndList.hasKey( groupZset )) {
            size = redisForZset.zInterAndStore( groupZset , RedisPrefix.SCORE , RedisPrefix.GROUPZSET + groupName );
        }
        List<Article> articles = new ArrayList<>();
        Article articleGet = new Article();
        /**
         * 找出新建的集合的全部的信息, 如果找某一部分可以用page,pageSize控制
         * page * pageSize , page * pageSize + pageSize
         */
        Set<String> artileIDs = redisForZset.zRange( groupZset , 0 , -1 );

        for ( String articleid : artileIDs ) {
            Map<Object , Object > map = redisUtilForMap.hashEntries( articleid );
            articleGet.setTitle( (String) map.get("title") );
            articleGet.setId( (long) map.get("id") );
            articleGet.setVotes( (int) map.get("votes") );
            articleGet.setGroup( (String) map.get("group") );
            articleGet.setPoster( (String) map.get("poster") );
            articleGet.setTime( (long) map.get("time") );
            articles.add( articleGet );
        }
        return articles;
    }

}
