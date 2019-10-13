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
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Exception:" +e);
			return null;
		}
		
	}
}