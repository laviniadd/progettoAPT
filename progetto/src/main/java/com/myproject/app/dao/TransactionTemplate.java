package com.myproject.app.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class TransactionTemplate {

	private EntityManagerFactory emf;

	public TransactionTemplate(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public <T> T executeTransaction(BloccoDiCodice<T> bloccoDiCodice) {
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction transaction = null;
		try {
			transaction = em.getTransaction();
			transaction.begin();

			T returnValue = bloccoDiCodice.executeWithOpenedTransaction(em);

			transaction.commit();
			return returnValue;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new RuntimeException(e);
		} finally {
			em.close();
		}
	}
}