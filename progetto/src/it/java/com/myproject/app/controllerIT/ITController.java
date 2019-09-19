package com.myproject.app.controllerIT;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runners.model.InitializationError;

import com.myproject.app.dao.TransactionTemplate;

public abstract class ITController {

	protected EntityManager entityManager;
	private static EntityManagerFactory entityManagerFactory;
	private TransactionTemplate transaction;

	@BeforeClass
	public static void setUpClass() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("integration");
	}

	protected abstract void init(TransactionTemplate transaction) throws InitializationError;

	@Before
	public void setUp() throws InitializationError {
		transaction = new TransactionTemplate(entityManagerFactory);

		transaction.executeTransaction((em) -> {
			em.createNativeQuery("DELETE FROM Prodotto").executeUpdate();
			em.createNativeQuery("DELETE FROM ListaSpesa").executeUpdate();
			em.createNativeQuery("DELETE FROM ElencoProdotti").executeUpdate();
			return null;
		});

		init(transaction);

	}

	@AfterClass
	public static void tearDownClass() {
		entityManagerFactory.close();
	}
}
