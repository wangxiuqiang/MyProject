package com.test.redis.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.List;
import java.util.Set;

public class RedisForZset {

    private RedisTemplate<String,String> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /***
     * 添加一个值和他的分数到 zset里面.返回布尔值.
     * @param key
     * @param value
     * @param score
     * @return
     */
    public boolean zAdd ( String key , String value, double score ) {
        try {
           return redisTemplate.opsForZSet().add( key , value , score );
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过redis 自带的TypedTuple接口作为泛型创建的set 进行一个或多个录入,
     * 并返回成功的数量.
     * @param key
     * @param tuples
     * @return
     */
    public long zAddOneAndMore(String key , Set<TypedTuple<String>> tuples ) {
        try {
            return redisTemplate.opsForZSet().add( key , tuples );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将指定的分值加上一个double
     * @param key
     * @param value
     * @param score
     * @return
     */
    public double zIncrScore( String key , String value , double score ) {
        try {
            return redisTemplate.opsForZSet().incrementScore( key, value, score );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 查找一个元素的排名,正序
     * @param key
     * @param member
     * @return
     */
    public long zRank ( String key , String member ) {
        try {
            return redisTemplate.opsForZSet().rank( key , member );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 查找一个元素的排名,倒序
     * @param key
     * @param member
     * @return
     */
    public long zReverseRank ( String key , String member ) {
        try {
            return redisTemplate.opsForZSet().reverseRank( key , member );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取一个元素的分值
     * @param key
     * @param value
     * @return
     */
    public double zGetScore( String key , String value ) {
        try {
            return redisTemplate.opsForZSet().score( key , value );
        } catch ( Exception e ){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 删除一个或多个元素,然后返回成功的数量
     * @param key
     * @param value
     * @return
     */
    public long zRemove( String key , String... value ) {
        try {
            return redisTemplate.opsForZSet().remove( key , value );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据排名顺序删除一个或多个元素,然后返回成功的数量
     * @param key
     * @param start
     * @return
     */
    public long zRemoveRange( String key , long start , long end ) {
        try {
            return redisTemplate.opsForZSet().removeRange( key , start , end );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 根据分数区间删除一个或多个元素,然后返回成功的数量
     * @param key
     * @param min
     * @return
     */
    public long zRemoveByScore( String key ,double min , double max ) {
        try {
            return redisTemplate.opsForZSet().removeRangeByScore( key , min, max );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取分数区间内的元素的数量
     * @param key
     * @param min
     * @param max
     * @return
     */
    public long zCount( String key ,double min , double max ) {
        try {
            return redisTemplate.opsForZSet().count( key , min, max );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 返回集合中的元素的数量
     * @param key
     * @return
     */
    public long zSize( String key ) {
        try {
            return redisTemplate.opsForZSet().size( key  );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 返回集合中的排序区间中的元素,没有分值
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zRange( String key , long start , long end  ) {
        try {
            return redisTemplate.opsForZSet().range( key ,start , end );
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 返回集合中的排序区间中的元素,有分值
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<TypedTuple<String>> zRangeWithSocre( String key , long start , long end  ) {
        try {
            return redisTemplate.opsForZSet().rangeWithScores( key ,start , end );
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据分值找某个区间中的元素,没有分值
     * offest表示限制开始的位置, count从开始位置开始多少个数.
     * 如果count为 0 用没有offest和count的函数
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zRangeByScore( String key , double start , double end , long offest , long count  ) {
        try {
            if ( count <= 0 ) {
                return redisTemplate.opsForZSet().rangeByScore( key ,start , end );
            } else {
                return redisTemplate.opsForZSet().rangeByScore( key ,start , end , offest , count );
            }

        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据分值找某个区间中的元素,没有分值
     * offest表示限制开始的位置, count从开始位置开始多少个数.
     * 如果count为 0 用没有offest和count的函数
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<TypedTuple<String>> zRangeByScoreWithScores( String key , double start , double end , long offest , long count  ) {
        try {
            if ( count <= 0 ) {
                return redisTemplate.opsForZSet().rangeByScoreWithScores( key ,start , end );
            } else {
                return redisTemplate.opsForZSet().rangeByScoreWithScores( key ,start , end , offest , count );
            }

        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 返回集合中的排序区间中的元素,没有分值 ,倒序
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zReverseRange( String key , long start , long end  ) {
        try {
            return redisTemplate.opsForZSet().reverseRange( key ,start , end );
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 返回集合中的排序区间中的元素,有分值 ,倒序
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<TypedTuple<String>> zReverseRangeWithScore( String key , long start , long end  ) {
        try {
            return redisTemplate.opsForZSet().reverseRangeWithScores( key ,start , end );
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据分值找某个区间中的元素,没有分值,倒序
     * offest表示限制开始的位置, count从开始位置开始多少个数.
     * 如果count为 0 用没有offest和count的函数
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zReverseRangeByScore( String key , double start , double end , long offest , long count  ) {
        try {
            if ( count <= 0 ) {
                return redisTemplate.opsForZSet().reverseRangeByScore( key ,start , end );
            } else {
                return redisTemplate.opsForZSet().reverseRangeByScore( key ,start , end , offest , count );
            }

        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据分值找某个区间中的元素,有分值,倒序
     * offest表示限制开始的位置, count从开始位置开始多少个数.
     * 如果count为 0 用没有offest和count的函数
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<TypedTuple<String>> zReverseRangeByScoreWithScore( String key , double start , double end , long offest , long count  ) {
        try {
            if ( count <= 0 ) {
                return redisTemplate.opsForZSet().reverseRangeByScoreWithScores( key ,start , end );
            } else {
                return redisTemplate.opsForZSet().reverseRangeByScoreWithScores( key ,start , end , offest , count );
            }

        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 两个集合做交集,返回数量
     * @param key
     * @param otherKey
     * @param dest_key
     * @return
     */
    public long  zInterAndStore( String key, String otherKey , String dest_key ) {
        try {
            return redisTemplate.opsForZSet().intersectAndStore( key, otherKey, dest_key );
        } catch (Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 多个集合做交集
     * @param key
     * @param otherKey
     * @param dest_key
     * @return
     */
    public long  zInterAndStore(String key, List<String> otherKey , String dest_key ) {
        try {
            return redisTemplate.opsForZSet().intersectAndStore( key, otherKey, dest_key );
        } catch (Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 集合做并集
     * @param key
     * @param otherKey
     * @param dest_key
     * @return
     */
    public long  zUnionAndStore(String key, String otherKey , String dest_key ) {
        try {
            return redisTemplate.opsForZSet().unionAndStore( key, otherKey, dest_key );
        } catch (Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }
    public long  zUnionAndStore(String key, List<String> otherKey , String dest_key ) {
        try {
            return redisTemplate.opsForZSet().unionAndStore( key, otherKey, dest_key );
        } catch (Exception e ) {
            e.printStackTrace();
            return 0;
        }
    }

}
