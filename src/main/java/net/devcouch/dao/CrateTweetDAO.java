package net.devcouch.dao;

import net.devcouch.domain.tweet.Tweet;
import net.devcouch.domain.tweet.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrateTweetDAO implements TweetDAO {

    private static final String SQL_SELECT_ALL = "SELECT id, retweeted, source, text, created_at, user['id'], " +
            "user['description'], user['followers_count'], user['friends_count'], user['location'], " +
            "user['statuses_count'], user['verified'], user['created_at'] FROM tweets";
    private static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL + " WHERE id = ?";
    private static final String SQL_SELECT_BY_USER_ID = SQL_SELECT_ALL + " WHERE user['id'] = ?";
    private static final String SQL_QUERY = SQL_SELECT_ALL + " WHERE text LIKE ?";
    private Connection conn;

    public CrateTweetDAO() {
        try {
            Class.forName("io.crate.client.jdbc.CrateDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Tweet findById(String id) {
        Tweet tweet = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("crate://127.0.0.1:4300");
            statement = conn.prepareStatement(SQL_SELECT_BY_ID);
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.first()) {
                tweet = createTweet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoHelper.tryClose(conn, statement, resultSet);
        }

        return tweet;
    }

    @Override
    public List<Tweet> findAll(int limit) {
        List<Tweet> tweets = new ArrayList<>();
        String sql = SQL_SELECT_ALL;
        if (limit > 0) {
            sql += " LIMIT " + limit;
        }
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("crate://127.0.0.1:4300");
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tweets.add(createTweet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoHelper.tryClose(conn, statement, resultSet);
        }
        return tweets;
    }

    private Tweet createTweet(ResultSet resultSet) throws SQLException {
        Tweet.Builder tweet = new Tweet.Builder();
        tweet.id(resultSet.getString(1));
        tweet.retweeted(resultSet.getBoolean(2));
        tweet.source(resultSet.getString(3));
        tweet.text(resultSet.getString(4));
        tweet.createdAt(resultSet.getTimestamp(5));

        User.Builder user = new User.Builder();
        user.id(resultSet.getString(6));
        user.description(resultSet.getString(7));
        user.followerCount(resultSet.getInt(8));
        user.friendsCount(resultSet.getInt(9));
        user.location(resultSet.getString(10));
        user.statusesCount(resultSet.getInt(11));
        user.verified(resultSet.getBoolean(12));
        user.createdAt(resultSet.getTimestamp(13));

        tweet.user(user.build());
        return tweet.build();
    }

    @Override
    public List<Tweet> getByUserId(String userId) {
        List<Tweet> tweets = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("crate://127.0.0.1:4300");
            statement = conn.prepareStatement(SQL_SELECT_BY_USER_ID);
            statement.setString(1, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tweets.add(createTweet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoHelper.tryClose(conn, statement, resultSet);
        }
        return tweets;
    }

    @Override
    public List<Tweet> findByKeyword(String keyword) {
        List<Tweet> tweets = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("crate://127.0.0.1:4300");
            String query = SQL_QUERY.replace("?", "'%" + keyword + "%'");
            statement = conn.prepareStatement(query);
            System.out.println(query);
//            statement.setString(1, "'%" + keyword + "%'");
//            System.out.println(statement.toString());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tweets.add(createTweet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoHelper.tryClose(conn, statement, resultSet);
        }
        return tweets;
    }
}
