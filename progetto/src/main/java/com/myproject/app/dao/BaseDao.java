package com.myproject.app.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.myproject.app.model.ElencoProdotti;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;

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
		if (classType == Prodotto.class) {
			return entityManager.createQuery("select e from Prodotto e", classType).getResultList();
		}
		if (classType == ElencoProdotti.class) {
			return entityManager.createQuery("select e from ElencoProdotti e", classType).getResultList();
		}
		return null;
	}

}
