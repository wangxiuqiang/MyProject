package com.test.redis.service;

import com.test.redis.pojo.Article;
import org.springframework.stereotype.Service;


public interface ArticlePublish {

    public boolean publish( Article article , String  userId );
    public boolean voteArticle( String userId , String articleId );
}
