package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.GenericDao;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import static java.text.MessageFormat.format;

/**
 * @author Alexander Savin
 */
public abstract class BaseDao<T, PK> implements GenericDao<T, PK> {

    @PersistenceContext
    protected EntityManager em;

    private Class<T> parametrizedClass;

    public static final String NON_UNIQUE_RESULT_MESSAGE =
            "Named query {0} with parameters {1} returned non-unique result";

    @SuppressWarnings("unchecked")
    public T findByPrimaryKey(PK id) {
        return (T) em.find(getParametrizedClass(), (Serializable) id);
    }

    @SuppressWarnings("unchecked")
    private Class<T> getParametrizedClass() {
        if (parametrizedClass == null) {
            parametrizedClass = getTypeParameters();
        }
        return parametrizedClass;
    }

    private Class getTypeParameters() {
        Class thisClass = this.getClass();
        Type[] genericIfs = thisClass.getGenericInterfaces();
        boolean isGenericDaoInheritor = false;
        Class daoIf = null;
        for (Type genericIf : genericIfs) {
            Class[] superIfs = ((Class) genericIf).getInterfaces();
            for (Class superIf : superIfs) {
                if (superIf.equals(GenericDao.class)) {
                    isGenericDaoInheritor = true;
                    daoIf = (Class) genericIf;
                    break;
                }
            }
        }
        if (isGenericDaoInheritor) {
            Type[] genericIfs1 = daoIf.getGenericInterfaces();
            for (Type type : genericIfs1) {
                ParameterizedType pt = (ParameterizedType) type;
                if (pt.getRawType().equals(GenericDao.class)) {
                    return (Class) pt.getActualTypeArguments()[0];
                }
            }
        }
        throw new RuntimeException(format("Class {0} doesn't implements {1} interface",
                thisClass.getCanonicalName(), GenericDao.class.getCanonicalName()));
    }

    public void create(T object) {
        em.persist(object);
    }

    public void update(T object) {
        em.merge(object);
    }

    public void remove(T object) {
        em.remove(object);
    }

    @SuppressWarnings("unchecked")
    protected T findSingleObject(String namedQuery, Object... params) {
        List<T> objects = findList(namedQuery, params);
        if (objects.size() > 1) {
            throw new NonUniqueResultException(format(NON_UNIQUE_RESULT_MESSAGE, namedQuery, params));
        } else if (objects.isEmpty()) {
            return null;
        } else {
            return objects.get(0);
        }
    }

    @SuppressWarnings("unchecked")
    protected List<T> findList(String namedQuery, Object... params) {
        return (List<T>) findByNamedQuery(namedQuery, params);
    }

    protected List<?> findByNamedQuery(String namedQuery, Object... params) {
        Query query = em.createNamedQuery(namedQuery);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
        }
        return query.getResultList();
    }

    public void flush() {
        em.flush();
    }
}
