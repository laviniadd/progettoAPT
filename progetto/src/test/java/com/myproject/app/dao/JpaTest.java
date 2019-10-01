package com.myproject.app.dao;

import javax.persistence.*;
import org.junit.*;
import org.junit.runners.model.InitializationError;

public abstract class JpaTest {
	protected EntityManager entityManager;
	private static EntityManagerFactory entityManagerFactory;
	private TransactionTemplate transaction;

	@BeforeClass
	public static void setUpClass() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("test");
	}

	protected abstract void init(TransactionTemplate transaction) throws InitializationError;

	@Before
	public void setUp() throws InitializationError {
		transaction = new TransactionTemplate(entityManagerFactory);

		transaction.executeTransaction((em) -> {
			em.createNativeQuery("TRUNCATE SCHEMA public AND COMMIT").executeUpdate();
			return null;
		});

		init(transaction);
	}

	@After
	public void tearDown() {
		transaction.executeTransaction((em) -> {
			em.close();
			return null;
		});
	}

	@AfterClass
	public static void tearDownClass() {
		entityManagerFactory.close();
	}
}
