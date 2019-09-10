package com.myproject.app.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class TransactionTemplate {
	private EntityManagerFactory emf;

	public TransactionTemplate(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public <T> T executeTransaction(BaseRepositoryInterface<T> baseRepository) {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
		//	transaction = em.getTransaction();
			transaction.begin();

			T returnValue = baseRepository.executeWithOpenedTransaction(em);

			transaction.commit();

			return returnValue;
		} catch (Exception e) {
				transaction.rollback();
			throw new NullPointerException();
		} finally {
			em.close();
		}
	}
}
