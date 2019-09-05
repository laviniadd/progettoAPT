package com.myproject.app.dao;

import javax.persistence.EntityManager;

public abstract class BaseDao<T> implements BaseDaoInterface<T> {
	
	private EntityManager entityManager;
	private Class<T> classType;

	public BaseDao(Class<T> classTypePassed) {
		this.entityManager = PersistenceManager.getEntityManager();
		this.classType = classTypePassed;
		
	}

	public void save(T entity) {
		entityManager.persist(entity);
	}

	public T findById(Long id) {
		return entityManager.find(classType, id);

	}

}
