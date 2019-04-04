package com.test.redis.service.serviceImpl;

import com.test.redis.pojo.User;
import com.test.redis.service.UserService;
import com.test.redis.utils.RedisPrefix;
import com.test.redis.utils.RedisUtilForMap;
import com.test.redis.utils.RedisUtilForStringAndList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService  {
    @Autowired
    RedisUtilForStringAndList redisUtilForStringAndList;
    @Autowired
    RedisUtilForMap redisUtilForMap;

    @Override
    public boolean addUser(User user) {
        //自增1 ,如果没就创建
        long userId = redisUtilForStringAndList.strIncr( RedisPrefix.USER , 1 );
        user.setId( userId );
        Map<Object,Object> map = new HashMap<>();
        map.put( "id" ,user.getId() );
        map.put( "name" , user.getName() );
        map.put( "phone" ,user.getPhone() );
        redisUtilForMap.hashPutAll( RedisPrefix.USER + userId , map );
        return true;
    }
}
