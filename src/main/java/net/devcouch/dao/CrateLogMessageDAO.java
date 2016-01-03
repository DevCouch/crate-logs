package net.devcouch.dao;

import net.devcouch.domain.log.LogMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrateLogMessageDAO implements LogDAO {
    private static final String SQL_INSERT = "INSERT INTO logs (id, level, message, createDate) VALUES (?, ?, ?, ?)";
    private static final String SQL_BASE_SELECT = "SELECT id, level, message, createDate FROM logs";

    @Autowired
    private DataSource dataSource;

    @Override
    public LogMessage findById(String id) {
        return null;
    }

    @Override
    public List<LogMessage> findAll(int limit) {
        List<LogMessage> logMessages = new ArrayList<>();
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            String sql = SQL_BASE_SELECT;
            if (limit > 0) {
                sql += " LIMIT " + limit;
            }
            conn = dataSource.getConnection();
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LogMessage logMessage = new LogMessage.Builder()
                        .id(resultSet.getInt(1))
                        .level(resultSet.getInt(2))
                        .message(resultSet.getString(3))
                        .createDate(new Date(resultSet.getTimestamp(4).getTime()))
                        .build();
                logMessages.add(logMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoHelper.tryClose(conn, statement, resultSet);
        }
        return logMessages;
    }

    @Override
    public boolean save(LogMessage logMessage) {
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            statement = conn.prepareStatement(SQL_INSERT);
            statement.setInt(1, logMessage.id);
            statement.setInt(2, logMessage.level);
            statement.setString(3, logMessage.message);
            Timestamp timestamp = new Timestamp(logMessage.createDate.getTime());
            statement.setTimestamp(4, timestamp);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoHelper.tryClose(conn, statement, null);
        }
        return true;
    }

    @Override
    public List<LogMessage> query(String query, String level, int limit) {
        List<LogMessage> logMessages = new ArrayList<>();
        PreparedStatement statement = null;
        Connection conn = null;
        ResultSet resultSet = null;
        try {
            String sql = SQL_BASE_SELECT;
            if (query != null && !query.isEmpty()) {
                sql += " WHERE MATCH(message_ft, '" + query + "') USING phrase";
                if (level != null && !level.isEmpty()) {
                    sql += " AND level = " + level;
                }
            } else if (level != null && !level.isEmpty()) {
                sql += " WHERE level = " + level;
            }
            if (limit > 0) {
                sql += " LIMIT " + limit;
            }
            conn = dataSource.getConnection();
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                LogMessage logMessage = new LogMessage.Builder()
                        .id(resultSet.getInt(1))
                        .level(resultSet.getInt(2))
                        .message(resultSet.getString(3))
                        .createDate(new Date(resultSet.getTimestamp(4).getTime()))
                        .build();
                logMessages.add(logMessage);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoHelper.tryClose(conn, statement, resultSet);
        }
        return logMessages;
    }
}
