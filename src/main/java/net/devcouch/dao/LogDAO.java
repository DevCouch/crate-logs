package net.devcouch.dao;

import net.devcouch.domain.log.LogMessage;

import java.util.List;

public interface LogDAO extends DAO<LogMessage> {
    boolean save(LogMessage logMessage);

    List<LogMessage> query(String query, String level, int limit);
}
