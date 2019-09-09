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
		EntityTransaction transaction = null;
		try {
			transaction = em.getTransaction();
			transaction.begin();

			T returnValue = baseRepository.executeWithOpenedTransaction(em);

			transaction.commit();

			return returnValue;
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}throw new IllegalArgumentException(e);
		} finally {
			em.close();
		}
	}
}
