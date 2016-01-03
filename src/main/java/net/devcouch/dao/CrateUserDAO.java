package net.devcouch.dao;

import net.devcouch.domain.tweet.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrateUserDAO implements UserDAO {
    private static final String SQL_SELECT_ALL = "SELECT user['id'], " +
            "user['description'], user['followers_count'], user['friends_count'], user['location'], " +
            "user['statuses_count'], user['verified'], user['created_at'] FROM tweets";
    private static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL + " where id = ?";

    @Override
    public User findById(String id) {
        User user = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("crate://127.0.0.1:4300");
            statement = conn.prepareStatement(SQL_SELECT_BY_ID);
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.first()) {
                user = createUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoHelper.tryClose(conn, statement, resultSet);
        }
        return user;
    }

    @Override
    public List<User> findAll(int limit) {
        List<User> users = new ArrayList<>();
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
                users.add(createUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoHelper.tryClose(conn, statement, resultSet);
        }
        return users;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User.Builder user = new User.Builder();
        user.id(resultSet.getString(1));
        user.description(resultSet.getString(2));
        user.followerCount(resultSet.getInt(3));
        user.friendsCount(resultSet.getInt(4));
        user.location(resultSet.getString(5));
        user.statusesCount(resultSet.getInt(6));
        user.verified(resultSet.getBoolean(7));
        user.createdAt(resultSet.getTimestamp(8));
        return user.build();
    }
}
