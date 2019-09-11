package com.myproject.app.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class TransactionTemplate {
	
	private EntityManagerFactory emf;
	private EntityManager em;

	public TransactionTemplate(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public <T> T executeTransaction(BaseRepositoryInterface<T> baseRepository) {
		
		EntityTransaction transaction;
		try {
			em = this.emf.createEntityManager();
			transaction = em.getTransaction();
			transaction.begin();

			T returnValue = baseRepository.executeWithOpenedTransaction(em);

			transaction.commit();

			return returnValue;
		} catch (Exception e) {
			throw new NullPointerException();
		}
	}
}