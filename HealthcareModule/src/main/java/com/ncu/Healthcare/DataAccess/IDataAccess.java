package com.ncu.Healthcare.DataAccess;

import java.util.List;

public interface IDataAccess<T> {
    List<T> getAll();
    T getById(Long id);
    boolean add(T item);
    boolean update(T item);
    boolean delete(Long id);
}

