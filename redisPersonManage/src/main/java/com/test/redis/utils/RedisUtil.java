package com.test.redis.utils;

import com.test.redis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedisUtil {
    /**
     * 实现私有化的构造器,不允许构建实例,当做静态类方法.
     */
    private RedisUtil() {}
    @Autowired
    @Qualifier("redisTemplate")
    private static RedisTemplate redisTemplate;

    /**
     * 设置键的过期时间
     * @param key
     * @param time
     * @return
     */
    public static boolean expire( String key , long time ) {
        try{
            if ( time > 0 ) {
                redisTemplate.expire( key , time , TimeUnit.SECONDS );
            }
            //如果成功了就返回一个true
            return true;
        } catch (Exception e ) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取过期时间,) 返回0代表为永久有效
     * @param key
     * @return
     */
    public static long getExpire( String key ) {
//key不能为0
        if ( key == null ) {
            return -1;
        }
        return redisTemplate.getExpire( key );
    }

    /**
     * 设置字符串的值,
     * @param key
     */
    public static boolean strSet(String key , String value ) {

        try {
            redisTemplate.opsForValue().set( key , value );
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 返回一个字符串的值
     * @param key
     * @return
     */
    public static String strGet( String key ) {
        try {
            String value = ( String ) redisTemplate.opsForValue().get( key );
            return value;
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置新的值value 并返回旧值
     * @param key
     * @param value
     * @return
     */
    public static String strGetSet( String key , String value ) {
        try {
            String result =  ( String ) redisTemplate.opsForValue().getAndSet( key , value ) ;
            return result;
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ;获取字符串的长度
     * @param key
     * @return
     */
    public static long strlen( String key ) {
        long len = redisTemplate.opsForValue().size( key );

        return len;

    }

    /**
     * 自增或自减,返回自增后的值
     * @param key
     * @param count
     * @return
     */
    public static long strIncr( String key , long count ) {
       try {
           long value = redisTemplate.opsForValue().increment( key , count );
           return value;
       } catch ( Exception e ) {
           e.printStackTrace();
           return 0;
       }
    }

    /**
     * 自增或自减,返回之后的值
     * @param key
     * @param count
     * @return
     */
    public static double strIncrByDouble( String key, double count ) {
        double value = redisTemplate.opsForValue().increment( key , count );
        return value;
    }

    /**
     * 在字符串后面添加一段字符,并返回添加后的长度
     * @param key
     * @param value
     * @return
     */
    public static int srtAppend( String key , String value ) {
        try {
           int len = (int) redisTemplate.opsForValue().append( key , value );
           return len;
        } catch (Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 如果这个key不存在的话就录入, 用来实现锁
     * @param key
     * @param value
     * @return
     */
    public static boolean strSetIfAbsent( String key , String value){
        try {
            boolean result = redisTemplate.opsForValue().setIfAbsent( key , value );
            return result;
        } catch ( Exception e ) {
            e.printStackTrace();
            return  false;
        }
    }

    /**
     * 返回多个元素的结果,
     * @param keys
     * @return
     */
    public static List<String> strGetList( String...keys ) {
        try {
            //首先先将参数变为list集合
            List<String> key = new ArrayList<>();
            for ( int i = 0 ; i <  keys.length ; i++ ) {
                key.add( keys[i] );
            }
            List<String> result = redisTemplate.opsForValue().multiGet( key );
            return result;
        } catch (Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 一次性设置多个键值对
     * @param map
     * @return
     */
    public static boolean strSetList(Map<String ,String > map ) {
        try {
            if( map.size() > 0 &&  map != null ){
                redisTemplate.opsForValue().multiSet( map );
            }
            return true;
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 批量录入字符串,如果都没有的话
     * @param map
     * @return
     */
    public static boolean strSetListIfAbsent(Map<String ,String > map ) {
        boolean result =  false;
        try {
            if( map.size() > 0 &&  map != null ){
                result = redisTemplate.opsForValue().multiSetIfAbsent( map );
            }
            return result;
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
    }

}
