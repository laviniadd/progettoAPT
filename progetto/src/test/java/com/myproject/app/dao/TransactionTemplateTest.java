package com.myproject.app.dao;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

public class TransactionTemplateTest extends JpaTest {

	private TransactionTemplate transaction;

	@Override
	protected void init(TransactionTemplate transaction) throws InitializationError {
		this.transaction = transaction;
	}

	@Test(expected = Exception.class)
	public void testThrowException() {
		transaction.executeTransaction(null);
	}

}
