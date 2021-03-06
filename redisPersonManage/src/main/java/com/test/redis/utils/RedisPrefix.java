package com.test.redis.utils;

/**
 * 管理redis的前缀
 */
public class RedisPrefix {
    private RedisPrefix() {}

    public static final String USER = "user:";
    public static final String ARTICLE = "article:";
    //某文章的投票人id
    public static final String VOTE = "vote:";
    //文章分数
    public static final String SCORE = "score:";
    //文章的发布时间.
    public static final String TIME = "time:";
    //文章的分组信息
    public static final String GROUP = "group:";
    //分组文章的分数列表
    public static final String GROUPZSET = "groupzset:";
}
