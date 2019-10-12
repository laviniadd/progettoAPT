package com.myproject.app.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.google.inject.Inject;

public class TransactionTemplate {

	private EntityManagerFactory emf;

	//@Inject
	public TransactionTemplate(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public <T> T executeTransaction(BaseRepositoryInterface<T> baseRepository) {
		EntityManager em;
		EntityTransaction transaction;
		try {
			em = this.emf.createEntityManager();
			transaction = em.getTransaction();
			transaction.begin();

			T returnValue = baseRepository.executeWithOpenedTransaction(em);

			transaction.commit();

			return returnValue;
		} catch (Exception e) {
			System.err.println(e);
			throw new NullPointerException();
		}
	}
}