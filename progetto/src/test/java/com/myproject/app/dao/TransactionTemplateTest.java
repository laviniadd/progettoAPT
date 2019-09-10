package com.myproject.app.dao;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import org.junit.runners.model.InitializationError;

public class TransactionTemplateTest extends JpaTest {

	private TransactionTemplate transactionTemplate;

	@Override
	protected void init(TransactionTemplate transaction) throws InitializationError {
		this.transactionTemplate = transaction;
	}

	@Test
	public void testExceptionIsActive() {

		Throwable thrown = catchThrowable(() -> {
			transactionTemplate.executeTransaction(null);
		});

		// then
		assertThat(thrown).isInstanceOf(NullPointerException.class);

	}

}
