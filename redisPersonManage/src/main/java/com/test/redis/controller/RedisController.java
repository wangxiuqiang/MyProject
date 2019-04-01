package com.test.redis.controller;

import com.test.redis.pojo.Article;
import com.test.redis.pojo.User;
import com.test.redis.service.ArticlePublish;
import com.test.redis.service.UserService;
import com.test.redis.service.serviceImpl.UserServiceImpl;
import com.test.redis.utils.RedisForSet;
import com.test.redis.utils.RedisForZset;
import com.test.redis.utils.RedisUtilForMap;
import com.test.redis.utils.RedisUtilForStringAndList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping( produces = "application/json;charset=utf-8" )
public class RedisController {

    @Autowired
    RedisUtilForStringAndList redisUtilForStringAndList;

    @Autowired
    RedisUtilForMap redisUtilForMap;
    @Autowired
    RedisForSet redisForSet;
    @Autowired
    RedisForZset redisForZset;

    @Autowired
    ArticlePublish articlePublish;

    @Autowired
    UserService userService;
    @RequestMapping( "/index")
    public String index() {

//        long test = redisUtilForStringAndList.listLpush( "list1" , "abc");
//        List<String> result =  redisUtilForStringAndList.listRange("list1",0,-1);
//        System.out.println( result.get(0) );
//        System.out.println( "1" );
//        Map<Object , Object> map = new HashMap<>();
//        map.put( "name" , "myName" );
//        map.put( "id", 1);
//        map.put( "phone" , "12345678910" );
//        redisUtilForMap.hashPutAll( "user1" , map );
//        Map<Object ,Object> map1 = redisUtilForMap.hashEntries( "user1" );
//        return String.valueOf( map1 );
//        String a = "1";
//        String b = "2";
//        String c = "9";
//        long count = redisForSet.sAdd( "set1" , a , b, c);
//        return String.valueOf( count );
//        int a = 1;
//        int b = 2;
//        int c = 3;
//        long count = redisForSet.sAdd( "set1" , a , b, c);
//        System.out.println( count );
//        return String.valueOf( count );
//        return redisForSet.sMembers( "set1").toString();

//         return String.valueOf(redisForZset.zAdd( "zset1","a",1.2));
        /**
         * 加一个d表示是double数据
         */
//        ZSetOperations.TypedTuple<String> tuple =  new DefaultTypedTuple<>("a", 3d);
//        ZSetOperations.TypedTuple<String> tuple1 =  new DefaultTypedTuple<>("g", 4d);
//        ZSetOperations.TypedTuple<String> tuple2 =  new DefaultTypedTuple<>("n", 9d);
//        Set<ZSetOperations.TypedTuple<String>> vo = new HashSet<>();
//        vo.add( tuple );
//        vo.add( tuple1 );
//        vo.add( tuple2 );
//        return String.valueOf( redisForZset.zAddOneAndMore( "zset1" , vo ) );

//        return String.valueOf( redisForZset.zIncrScore( "zset1" , "a" , 2.6 ));
//        return String.valueOf( redisForZset.zRank( "zset1" , "a"));
//        Iterator<ZSetOperations.TypedTuple<String> > iterator =  redisForZset.zRangeWithSocre( "zset1", 0 , -1).iterator();
//        while( iterator.hasNext() ) {
//            ZSetOperations.TypedTuple<String> tuple = iterator.next();
//            System.out.println( tuple.getScore() + "---" + tuple.getValue() );
//        }

//        Article article = new Article();
//        article.setTitle("第一篇文章");
//        articlePublish.publish( article , "1");
////        return String.valueOf( 0 );
//        String title = (String) redisUtilForMap.hashGet( "article:1" , "title");
//        System.out.println( title );
        Map<Object ,Object> map1 = redisUtilForMap.hashEntries( "article:1" );
//        return String.valueOf( map1 );
//        User user = User.newIntance();
//        user.setName( "李四");
//        user.setPhone("1231231230");
//        userService.addUser( user );
        Map<Object , Object> map = redisUtilForMap.hashEntries( "user:2" );
//        return String.valueOf( map );
//        articlePublish.voteArticle("2" , "article:1");
       Iterator<ZSetOperations.TypedTuple<String> > iterator =  redisForZset.zRangeWithSocre( "score:", 0 , -1).iterator();
        while( iterator.hasNext() ) {
            ZSetOperations.TypedTuple<String> tuple = iterator.next();
            System.out.println( tuple.getScore() + "---" + tuple.getValue() );
        }
        Iterator<ZSetOperations.TypedTuple<String> > iterator1 =  redisForZset.zRangeWithSocre( "time:", 0 , -1).iterator();
        while( iterator1.hasNext() ) {
            ZSetOperations.TypedTuple<String> tuple = iterator1.next();
            System.out.println( tuple.getScore() + "---" + tuple.getValue() );
        }

        Set<Object> voteSet = redisForSet.sMembers( "vote:1" );
        Object[] list = voteSet.toArray();
        for (int i = 0; i < list.length; i++) {
            System.out.println( list[i] );
        }
        return String.valueOf( map1 ) + String.valueOf( map );
    }
}
