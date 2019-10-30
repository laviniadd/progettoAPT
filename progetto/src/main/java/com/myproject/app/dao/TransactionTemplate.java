package com.myproject.app.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

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
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Exception in Transaction Template:" +e);
			return null;
		} finally {
			em.close();
		}

	}
}