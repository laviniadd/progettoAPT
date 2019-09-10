package com.myproject.app.dao;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.model.InitializationError;

import com.arjuna.ats.arjuna.common.recoveryPropertyManager;

public class TransactionTemplateTest extends JpaTest {

	private TransactionTemplate transactionTemplate;

	@Override
	protected void init(TransactionTemplate transaction) throws InitializationError {
		this.transactionTemplate = transaction;
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testExceptionIsNotActive() {
		transactionTemplate.executeTransaction((em) -> {
			em.clear();
			em.getTransaction().isActive();			
			return null;
		});
	}

	@Test
	public void testExceptionIsActive() {

		thrown.expect(NullPointerException.class);
		transactionTemplate.executeTransaction(null);
	}
}
