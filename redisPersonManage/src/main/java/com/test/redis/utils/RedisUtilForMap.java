package com.test.redis.utils;

import com.test.redis.pojo.User;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisUtilForMap {

    /**
     * String表示键, Object 表示键值对的映射
     */
    public RedisTemplate< String , Object > redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 删除一个Map里面的键值对
     * @param key_name
     * @param key
     */
    public void hashDelete( String key_name , String...key ) {
        try {
            redisTemplate.opsForHash().delete(key_name , key );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * 检测某个hash里面是不是有这个key
     * @param key_name
     * @param key
     * @return
     */
    public boolean hashHasKey ( String key_name , String key ) {

        try {
           return redisTemplate.opsForHash().hasKey( key_name , key );
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 获取一个键里面的所有键值对
     *
     * @param key_name
     * @return
     */
    public Map<Object , Object > hashEntries ( String key_name ) {
        try {
            Map<Object,Object> map = redisTemplate.opsForHash().entries( key_name );
            return map;
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置某个键 里面所有的值,放在一个map里面.注意,这个map的键是类的属性,value是类属性的值,
     * 这里用object,用来包含所有的类型,
     * @param key_name
     * @param map
     */
    public void hashPutAll ( String key_name , Map<Object,Object > map ) {
        try {
            redisTemplate.opsForHash().putAll( key_name , map );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * 给某个hash里面的某个键赋值.
     * @param key_name
     * @param key
     * @param value
     */
    public void hashPut ( String key_name , String key , Object value ) {
        try {
            redisTemplate.opsForHash().put( key_name , key , value );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * 某个hash里面没有这个key的时候才执行.将这个键值对录入.
     * @param key_name
     * @param key
     * @param value
     * @return
     */
    public boolean hashPutIfAbsent ( String key_name , String key , Object value ) {
        boolean result = false;
        try {
            result = redisTemplate.opsForHash().putIfAbsent( key_name , key , value );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取某个hash里面所有的值.
     * @param key_name
     * @return
     */
    public List<Object> hashGetValues( String key_name ) {
        try {
            return redisTemplate.opsForHash().values( key_name );
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回某个hash里面的所有的键
     * @param key_name
     * @return
     */
    public Set<String> hashGetKeys( String key_name ) {
        try {
            Set<Object> keys = redisTemplate.opsForHash().keys( key_name );
            Set<String> strkeys =  new HashSet<>();
            for ( Object k : keys ) {
                strkeys.add(k.toString());
            }
            return strkeys;
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  返回一个hash的数量.
     * @param key_name
     * @return
     */
    public long hashSize( String key_name ) {
        long result = 0;
        try {
            result = redisTemplate.opsForHash().size( key_name );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取某个hash里面的一个键的值,
     * @param key_name
     * @param key
     * @return
     */
    public Object hashGet ( String key_name , String  key ) {
        try {
            return redisTemplate.opsForHash().get( key_name , key );
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查找某个hash里面的所有的键的值.
     * list 是一个实体类的字段的集合.
     * @param key_name
     * @param list
     * @return
     */
    public List<Object> hashMultiGet ( String key_name , List<Object> list ) {
        try {
            return redisTemplate.opsForHash().multiGet( key_name , list  );
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在hash中的某个键上 加上 一个数.
     * @param key_name
     * @param key
     * @param count
     * @return
     */
    public long hashIncrLong( String key_name , String key , long count ) {
        try {
            return redisTemplate.opsForHash().increment( key_name , key , count );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }
    public double hashIncrDouble( String key_name , String key , double count ) {
        try {
            return redisTemplate.opsForHash().increment( key_name , key , count );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

}
