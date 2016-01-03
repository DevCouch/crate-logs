package net.devcouch.dao;

import java.util.List;

public interface DAO<T> {
    T findById(String id);
    List<T> findAll(int limit);
}
