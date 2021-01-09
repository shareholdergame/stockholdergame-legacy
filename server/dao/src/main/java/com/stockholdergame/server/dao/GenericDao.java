package com.stockholdergame.server.dao;

/**
 * @author Alexander Savin
 */
public interface GenericDao<T, PK> {

    T findByPrimaryKey(PK id);

    void create(T object);

    void update(T object);

    void remove(T object);

    void flush();
}
