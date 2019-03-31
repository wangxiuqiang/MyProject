package com.test.redis.controller;

import com.test.redis.utils.RedisForSet;
import com.test.redis.utils.RedisUtilForMap;
import com.test.redis.utils.RedisUtilForStringAndList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RedisController {

    @Autowired
    RedisUtilForStringAndList redisUtilForStringAndList;

    @Autowired
    RedisUtilForMap redisUtilForMap;
    @Autowired
    RedisForSet redisForSet;
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
        String a = "1";
        String b = "2";
        String c = "9";
//        long count = redisForSet.sAdd( "set1" , a , b, c);
//        return String.valueOf( count );
//        int a = 1;
//        int b = 2;
//        int c = 3;
        long count = redisForSet.sAdd( "set1" , a , b, c);
        System.out.println( count );
//        return String.valueOf( count );
        return redisForSet.sMembers( "set1").toString();
    }
}
