package net.devcouch.dao;

import net.devcouch.domain.tweet.Tweet;

import java.util.List;

public interface TweetDAO extends DAO<Tweet> {

    List<Tweet> getByUserId(String userId);

    List<Tweet> findByKeyword(String keyword);
}
