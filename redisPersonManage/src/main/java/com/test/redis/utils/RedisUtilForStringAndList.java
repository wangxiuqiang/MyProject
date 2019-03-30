package com.test.redis.utils;

import com.test.redis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 字符串和 list都使用的是 键和值 String类型的.
 * 当都是String的时候,这样录入之后在 list里面就不会有引号了, 如果是Object的话 ,就是带着引号
 *
 * 问题, 列表可能存放 int 的数值, 这里返回的是字符串,可以自己转换为数字.
 */
public class RedisUtilForStringAndList {
    /**
     * 实现私有化的构造器,不允许构建实例,当做静态类方法.
     */
//    private RedisUtilForStringAndList() {}
    public RedisUtilForStringAndList() {}

    public  RedisTemplate<String , String > redisTemplate;

//    public RedisUtilForStringAndList(RedisTemplate<String, String> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }


    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置键的过期时间
     * @param key
     * @param time
     * @return
     */
    public  boolean expire( String key , long time ) {
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
    public  long getExpire( String key ) {
//key不能为0
        if ( key == null ) {
            return -1;
        }
        return redisTemplate.getExpire( key );
    }

    /**
     * 设置字符串的值,如果有期限的话就输入期限
     * @param key
     */
    public  boolean strSet(String key , String value , long time  ) {

        try {
            if( time == 0 ) {
                redisTemplate.opsForValue().set( key , value );
            } else  {
                redisTemplate.opsForValue().set( key ,value , time );
//                redisTemplate.opsForValue().set( key , value ,time , TimeUnit.SECONDS);
            }


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
    public  String strGet( String key  ) {
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
    public  String strGetSet( String key , String value ) {
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
    public  long strlen( String key ) {
        long len = redisTemplate.opsForValue().size( key );

        return len;

    }

    /**
     * 自增或自减,返回自增后的值
     * @param key
     * @param count
     * @return
     */
    public  long strIncr( String key , long count ) {
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
    public  double strIncrByDouble( String key, double count ) {
        double value = redisTemplate.opsForValue().increment( key , count );
        return value;
    }

    /**
     * 在字符串后面添加一段字符,并返回添加后的长度
     * @param key
     * @param value
     * @return
     */
    public  int srtAppend( String key , String value ) {
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
    public  boolean strSetIfAbsent( String key , String value){
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
    public  List<String> strGetList( String...keys ) {
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
    public  boolean strSetList(Map<String ,String > map ) {
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
    public  boolean strSetListIfAbsent(Map<String ,String > map ) {
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


    /**
     * 下面是对列表的操作.
     */

    /**
     *  在列表的后面录入值. 录入一个,
     * @param key
     * @param value
     * @return
     */

    public  long listRpush(String key , String value ) {
        try {
//            System.out.println( redisTemplate );
           long result =  redisTemplate.opsForList().rightPush(key, value);
           return result;
        } catch (Exception e ) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * 在列表的后面 ,往存在的列表里面录入值  Present 目前的,现在的
     */
    public  long listRpushIfPresent( String key , String value ) {
        try {
            //这里可以加一个判断,判断这个值是不是存在
            long result = redisTemplate.opsForList().rightPushIfPresent( key ,value );
            return result;
        } catch (Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 列表尾输入多个值到List里面
     * @param key
     * @param value
     * @return
     */
    public  long listRpushAll ( String key , String...value ) {

        try {
            long result = redisTemplate.opsForList().rightPushAll( key , value );
            return result;
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 列表尾通过集合录入多个到list中
     * @param key
     * @param values
     * @return
     */
    public  long listRpushAllByList ( String key , List<String> values ) {
        try {
            return redisTemplate.opsForList().rightPushAll( key ,values );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 列表尾一次录入两个值
     * @param key
     * @param value
     * @param value1
     * @return
     */
    public  long listRpushTwoValue ( String key , String value, String value1 ) {
        try {
            return redisTemplate.opsForList().rightPushAll( key ,value, value1 );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     *  在列表的前面录入值. 录入一个,列表头
     * @param key
     * @param value
     * @return
     */

    public  long listLpush(String key , String value ) {
        try {
//            System.out.println( redisTemplate );
            long result =  redisTemplate.opsForList().leftPush(key, value);
            return result;
        } catch (Exception e ) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * 在列表的前面列表头 ,往存在的列表里面录入值  Present 目前的,现在的
     * 如果不存在就执行失败
     */
    public  long listLpushIfPresent( String key , String value ) {
        try {
            //这里可以加一个判断,判断这个值是不是存在
            long result = redisTemplate.opsForList().leftPushIfPresent( key ,value );
            return result;
        } catch (Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 列表头输入多个值到List里面
     * @param key
     * @param value
     * @return
     */
    public  long listLpushAll ( String key , String...value ) {

        try {
            long result = redisTemplate.opsForList().leftPushAll( key , value );
            return result;
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 列表头通过集合录入多个到list中
     * @param key
     * @param values
     * @return
     */
    public  long listLpushAllByList ( String key , List<String> values ) {
        try {
            return redisTemplate.opsForList().leftPushAll( key ,values );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 列表头一次录入两个值
     * @param key
     * @param value
     * @param value1
     * @return
     */
    public  long listLpushTwoValue ( String key , String value, String value1 ) {
        try {
            return redisTemplate.opsForList().leftPush( key ,value, value1 );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 查找一部分的list的值
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> listRange ( String key , long start , long end ) {
        try {
            return redisTemplate.opsForList().range( key, start, end);
        } catch (Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对List的列表根据给定的区域进行裁剪. 保留下区间内的值,其余的删掉
     * @param key
     * @param start
     * @param end
     */
    public void listTrim( String key , long start , long end ) {
        try {
            redisTemplate.opsForList().trim( key , start , end );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * 返回列表的长度.
     * @param key
     * @return
     */
    public long listLen( String key ) {
        try {
            long size = redisTemplate.opsForList().size( key );
            return size;
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 在指定的位置上放上一个值
     * @param key
     * @param index
     * @param value
     */
    public void listSet( String key , long index, String value ) {
        try {
            redisTemplate.opsForList().set( key , index , value );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定的key 中的某个值(value)的一到多个(count)
     * 返回删除后list的长度
     * count 为0 表示所有, 整数,表示从前往后找的count个,
     * 负数表示从后往前找的 count个
     * @param key
     * @param count
     * @param value
     * @return
     */
    public long listRemove( String key , long count ,  String value ) {

        try {
            return redisTemplate.opsForList().remove( key , count , value );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 查找某个位置上的值
     * @param key
     * @param index
     * @return
     */
    public Object listIndex( String key , long index ) {
        try {
            Object value = ( Object ) redisTemplate.opsForList().index( key , index );
            return value;
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从列表头弹出一个数据
     * 如果有time 表示等待的时间,如果没有直接弹出,并返回弹出的值.
     * @param key
     * @param time
     * @return
     */
    public Object listLpop( String  key , long time ) {
        Object value = null;
        try {
            if( time > 0 ) {
                value = redisTemplate.opsForList().leftPop( key , time , TimeUnit.SECONDS );
            } else {
                value = redisTemplate.opsForList().leftPop( key );
            }
            return value;
        } catch ( Exception e ) {
            e.printStackTrace();
            return value;
        }
    }

    /**
     * 从一个列表的后边弹出,并保存到另一列表的左边, 并返回这个值.
     * @param key
     * @param key2
     * @param time
     * @return
     */
    public Object listRpopLpush ( String key , String key2 , long time ) {
        Object value = null;
        try {
            if( time > 0 ) {
                value = redisTemplate.opsForList().rightPopAndLeftPush( key , key2 , time , TimeUnit.SECONDS );
            } else {
                value = redisTemplate.opsForList().rightPopAndLeftPush( key , key2 );
            }
            return value;
        } catch ( Exception e ) {
            e.printStackTrace();
            return value;
        }
    }
    /**
     * 从列表头弹出一个数据
     * 如果有time 表示等待的时间,如果没有直接弹出,并返回弹出的值.
     * @param key
     * @param time
     * @return
     */
    public Object listRpop( String  key , long time ) {
        Object value = null;
        try {
            if( time > 0 ) {
                value = redisTemplate.opsForList().rightPop( key , time , TimeUnit.SECONDS );
            } else {
                value = redisTemplate.opsForList().rightPop( key );
            }
            return value;
        } catch ( Exception e ) {
            e.printStackTrace();
            return value;
        }
    }



}
