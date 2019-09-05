package com.myproject.app.dao;

import javax.persistence.*;
import org.junit.*;
import org.junit.runners.model.InitializationError;

public abstract class JpaTest {
	protected EntityManager entityManager;

	@BeforeClass
	public static void setUpClass() {
		PersistenceManager.startEntityManagerFactory();
	}

	protected abstract void init() throws InitializationError;

	@Before
	public void setUp() throws InitializationError {

		entityManager = PersistenceManager.createEntityManager();

		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("TRUNCATE SCHEMA public AND COMMIT").executeUpdate();

		/*
		 * prova con db reale
		 * entityManager.createQuery("DELETE FROM ElencoProdotti").executeUpdate();
		 * entityManager.createQuery("DELETE FROM ListaSpesa").executeUpdate();
		 * entityManager.createQuery("DELETE FROM Prodotto").executeUpdate();
		 */

		entityManager.getTransaction().commit();

		entityManager.getTransaction().begin();
		init();
		entityManager.getTransaction().commit();
		entityManager.clear();

		entityManager.getTransaction().begin();
	}

	@After
	public void tearDown() {
		if (entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().rollback();
		}
		entityManager.close();
	}

	@AfterClass
	public static void tearDownClass() {
		PersistenceManager.closeEntityManagerFactory();
	}

}
