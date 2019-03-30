package com.test.redis.controller;

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
    @RequestMapping( "/index")
    public String index() {

//        long test = redisUtilForStringAndList.listLpush( "list1" , "abc");
//        List<String> result =  redisUtilForStringAndList.listRange("list1",0,-1);
//        System.out.println( result.get(0) );
//        System.out.println( "1" );
        Map<Object , Object> map = new HashMap<>();
        map.put( "name" , "myName" );
        map.put( "id", 1);
        map.put( "phone" , "12345678910" );
        redisUtilForMap.hashPutAll( "user1" , map );
        Map<Object ,Object> map1 = redisUtilForMap.hashEntries( "user1" );
        return String.valueOf( map1 );
    }
}
