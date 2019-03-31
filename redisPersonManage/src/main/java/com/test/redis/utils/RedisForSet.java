package com.test.redis.utils;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RedisForSet {

    private RedisTemplate<Object,Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /***
     * 在无序集合中录入多个值
     * @param key
     * @param value
     */
    public long sAdd( String key , Object...value ) {
        try{
            return redisTemplate.opsForSet().add( key, value );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 判断set里面是不是有一个成员
     * @param key
     * @param value
     * @return
     */
    public boolean sIsMember( String key , Object value ) {
        try{
            return redisTemplate.opsForSet().isMember( key , value );
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 返回set中的所有的成员
     * @param key
     * @return
     */
    public Set<Object> sMembers( String key ) {
        try{
            return redisTemplate.opsForSet().members( key );
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将 一个列表中的值,移动到另一个列表中.
     * @param key1
     * @param value
     * @param key2
     * @return
     */
    public boolean sMove( String key1, Object value , String key2 ) {
        try{
            return redisTemplate.opsForSet().move( key1, value, key2 );
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 取出一个或多个随机的值 ,如果count < 0 就取出重复的可能会
     * @param key
     * @param count
     * @return
     */
    public List<Object> sRandomMember (String key , long count ) {
        try {
            if( count != 0 ) {
                return  redisTemplate.opsForSet().randomMembers( key , count );
            }
            List<Object> list = new ArrayList<>();
            list.add( redisTemplate.opsForSet().randomMember( key ) );
            return list;
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回不重复的多个值
     * @param key
     * @param count
     * @return
     */
    public Set<Object> sDistinctRandomMembers( String key , long count ) {
        try {
            return redisTemplate.opsForSet().distinctRandomMembers( key , count );
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除并返回成功删除的数量
     * @param key
     * @param valule
     * @return
     */
    public long sRemove( String key , Object... valule ) {
        try {
            return redisTemplate.opsForSet().remove( key , valule );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 弹出一个值,并返回这个值
     * @param key
     * @return
     */
    public Object sPop ( String key  ) {
        try {
            return redisTemplate.opsForSet().pop( key );
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 集合的值的数量
     * @param key
     * @return
     */
    public long sSize( String key ) {
        try {
            return redisTemplate.opsForSet().size( key );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 得出 key 和 keys里面的集合的交,
     * 同时存在于所有集合中的元素
     * 如果list里面就一个值,使用的是传一个的方法
     * @param key
     * @param keys
     * @return
     */
    public Set<Object> sIntersect( String key , List<String> keys ) {
        try {
            /**
             * 如果两个函数的性能没有区别,这样写完全没必要
             */
            if ( keys.size() == 1 ) {
                return redisTemplate.opsForSet().intersect( key , keys.get(0) );
            } else {
                return redisTemplate.opsForSet().intersect( key , keys );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 同时存在于所有集合中的元素放到destKey里面,并返回destKey的长度.
     * @param key
     * @param keys
     * @param destKey
     * @return
     */
    public long sInterAndStore( String key , List<String> keys , String destKey ) {
        try {
            return redisTemplate.opsForSet().intersectAndStore( key , keys, destKey );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 得出 key 和 keys里面的集合的差,
     * 至少存在一个集合中的元素.
     * 如果list里面就一个值,使用的是传一个的方法
     * @param key
     * @param keys
     * @return
     */
    public Set<Object> sUnion( String key , List<String> keys ) {
        try {
            /**
             * 如果两个函数的性能没有区别,这样写完全没必要
             */
            if ( keys.size() == 1 ) {
                return redisTemplate.opsForSet().union( key , keys.get(0) );
            } else {
                return redisTemplate.opsForSet().union( key , keys );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 至少存在一个集合中的元素放到destKey里面,并返回destKey的长度.
     * @param key
     * @param keys
     * @param destKey
     * @return
     */
    public long sUnionAndStore( String key , List<String> keys , String destKey ) {
        try {
            return redisTemplate.opsForSet().unionAndStore( key , keys, destKey );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 得出 key 和 keys里面的集合的差,
     * 存在与第一个集合,但是不存在其他集合中的元素
     * 如果list里面就一个值,使用的是传一个的方法
     * @param key
     * @param keys
     * @return
     */
    public Set<Object> sDifference( String key , List<String> keys ) {
        try {
            /**
             * 如果两个函数的性能没有区别,这样写完全没必要
             */
            if ( keys.size() == 1 ) {
                return redisTemplate.opsForSet().difference( key , keys.get(0) );
            } else {
                return redisTemplate.opsForSet().difference( key , keys );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将存在key中的不存在keys中的元素放到destKey里面,并返回destKey的长度.
     * @param key
     * @param keys
     * @param destKey
     * @return
     */
    public long sDiffAndStore( String key , List<String> keys , String destKey ) {
        try {
            return redisTemplate.opsForSet().differenceAndStore( key , keys, destKey );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

}
