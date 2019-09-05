package com.myproject.app.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.myproject.app.model.ListaSpesa;

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

	public List<T> findAll() {
		if (classType == ListaSpesa.class) {
			return entityManager.createQuery("select e from ListaSpesa e", classType).getResultList();
		}
		return null;
	}

}
