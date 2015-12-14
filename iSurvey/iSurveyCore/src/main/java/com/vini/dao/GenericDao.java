package com.vini.dao;

import java.io.Serializable;

public interface GenericDao<T> extends Serializable{
    
    T create(T t);
    
    void delete(Object id);
    
    T findById(Object id);
    
    T update(T t);   
}
