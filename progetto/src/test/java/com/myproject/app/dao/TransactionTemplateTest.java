package com.myproject.app.dao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.myproject.app.model.ListaSpesa;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class TransactionTemplateTest {

	@InjectMocks
	private TransactionTemplate transactionTemplate;
	@Mock
	private EntityManagerFactory emf;
	@Mock
	private EntityManager em;
	@Mock
	private EntityTransaction transaction;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testThrowExceptionWhenABlockOfCodeIsNull() {
		when(emf.createEntityManager()).thenReturn(em);
		when(em.getTransaction()).thenReturn(transaction);

		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
			transactionTemplate.executeTransaction(null);
		});

		verify(em, times(1)).getTransaction();
		verify(transaction, times(1)).begin();
		verify(transaction, times(1)).rollback();
		verify(em, times(1)).close();
	}

	@Test
	public void testWhenABlockOfCodeIsNotNull() {
		when(emf.createEntityManager()).thenReturn(em);
		when(em.getTransaction()).thenReturn(transaction);
		when(em.find(ListaSpesa.class, new ListaSpesa())).thenReturn(new ListaSpesa());

		transactionTemplate.executeTransaction(em -> em.find(ListaSpesa.class, new ListaSpesa()));

		verify(em, times(1)).getTransaction();
		verify(transaction, times(1)).begin();
		verify(transaction, times(1)).commit();
		verifyNoMoreInteractions(ignoreStubs(transaction));
		verify(em, times(1)).close();
	}

	@Test
	public void testWhenTransactionIsNull() {
		when(emf.createEntityManager()).thenReturn(em);
		when(em.getTransaction()).thenReturn(null);

		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
			transactionTemplate.executeTransaction(em -> em.find(ListaSpesa.class, new ListaSpesa()));
		});

		verify(em, times(1)).getTransaction();
		verifyNoMoreInteractions(ignoreStubs(transaction));
		verify(em, times(1)).close();
	}

	@Test
	public void testWhenExecuteTransactionReturnNull() {
		when(emf.createEntityManager()).thenReturn(em);
		when(em.getTransaction()).thenReturn(transaction);
		when(em.find(ListaSpesa.class, new ListaSpesa())).thenReturn(null);

		transactionTemplate.executeTransaction(em -> em.find(ListaSpesa.class, new ListaSpesa()));

		verify(em, times(1)).getTransaction();
		verify(transaction, times(1)).begin();
		verify(transaction, times(1)).commit();
		verify(em, times(1)).close();
	}

}
