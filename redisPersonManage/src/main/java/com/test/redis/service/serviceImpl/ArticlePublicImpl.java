package com.test.redis.service.serviceImpl;

import com.test.redis.pojo.Article;
import com.test.redis.service.ArticlePublish;
import com.test.redis.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
    @Override
    public boolean publish(Article article , String  userId) {
        //首先生成一个新的文章的id,如果没有就新建一个,并且录入;
        long articleId = redisUtilForStringAndList.strIncr( RedisPrefix.ARTICLE , 1 );
        article.setId( RedisPrefix.ARTICLE + articleId );
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
        redisUtilForMap.hashPutAll( article.getId() , map );
        /**
         * 设置文章的发布时间和初始分值
         */
        redisForZset.zAdd( RedisPrefix.TIME , article.getId() , System.currentTimeMillis() );
        redisForZset.zAdd( RedisPrefix.SCORE , article.getId() , System.currentTimeMillis() + incrScore );
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

}
