package com.vini.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericDaoImpl<T> implements GenericDao<T> {
    private static final long serialVersionUID = 1L;
    
    @PersistenceContext
    protected EntityManager entityManager;
    
    private Class< T > classType;
    
    @SuppressWarnings("unchecked")
    public GenericDaoImpl() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) type;
        this.classType = (Class<T>) pt.getActualTypeArguments()[0];
    }
    
    @Override
    public T create(final T t) {
        this.entityManager.persist(t);
        return t;
    }
    
    @Override
    public void delete(final Object id) {
        this.entityManager.remove(this.entityManager.getReference(classType, id));
    }
    
    @Override
    public T findById(final Object id) {
        return (T) this.entityManager.find(classType, id);
    }
    
    @Override
    public T update(final T t) {
        return this.entityManager.merge(t);    
    }
    
    
}
