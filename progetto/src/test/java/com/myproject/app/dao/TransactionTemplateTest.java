package com.myproject.app.dao;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.model.InitializationError;

public class TransactionTemplateTest extends JpaTest {

	private TransactionTemplate transaction;

	@Override
	protected void init(TransactionTemplate transaction) throws InitializationError {
		this.transaction = transaction;
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testExceptionIsNotActive() {

		thrown.expect(IllegalArgumentException.class);
		transaction.executeTransaction((em) -> {
			em.getTransaction().rollback();
			return null;
		});
	}

	@Test
	public void testExceptionIsActive() {
		
		thrown.expect(IllegalArgumentException.class);
		transaction.executeTransaction(null);
	}

}
