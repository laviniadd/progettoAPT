package com.myproject.app.dao;

import java.util.List;

public class BaseRepository<T> {
	private TransactionTemplate transaction;
	private Class<T> classType;

	public BaseRepository(TransactionTemplate transaction, Class<T> classTypePassed) {
		this.transaction = transaction;
		this.classType = classTypePassed;
	}

	public void save(T entity) {
		transaction.executeTransaction((em) -> {
			em.persist(entity);
			return null;
		});
	}

	public T findById(Long id) {
		return transaction.executeTransaction((em) -> {
			return em.find(classType, id);
		});
	}

	public List<T> findAll() {
		String classTypeString = classType.getCanonicalName();
		return transaction.executeTransaction((em) -> {
			return em.createQuery("SELECT t FROM " + classTypeString + " t", classType).getResultList();
		});
	}
}
