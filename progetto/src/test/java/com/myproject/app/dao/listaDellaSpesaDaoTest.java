package com.myproject.app.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;
import com.myproject.app.model.ListaSpesa;

public class listaDellaSpesaDaoTest extends JpaTest {

	private ListaDellaSpesaDao listaDao;
	private ListaSpesa lista;
	private ListaSpesa listaSpesa;
	private TransactionTemplate transaction;

	@Override
	protected void init(TransactionTemplate transaction) throws InitializationError {
		listaDao = new ListaDellaSpesaDao(transaction);
		this.transaction = transaction;
	}

	@Test
	public void testSaveListaDellaSpesa() {
		lista = new ListaSpesa();
		lista.setName("lista1");
		listaDao.save(lista);

		List<ListaSpesa> retrievedList = transaction.executeTransaction((em) -> {
			return em.createQuery("select e from ListaSpesa e where e.name = :name", ListaSpesa.class)
					.setParameter("name", lista.getName()).getResultList();
		});

		assertThat(retrievedList).containsExactly(lista);
	}

	@Test
	public void testListaDellaSpesaFindById() {
		lista = new ListaSpesa();
		listaSpesa = new ListaSpesa();

		addListToDatabase(lista);
		addListToDatabase(listaSpesa);
				
		assertThat(listaDao.findById(listaSpesa.getId())).isEqualTo(listaSpesa);
	}

	@Test
	public void testListaDellaSpesaFindByIdNotFound() {

		assertThat(listaDao.findById(Long.valueOf(1))).isNull();
	}

	@Test
	public void testFindAllListaDellaSpesaWhenDatabaseIsNotEmpty() {
		lista = new ListaSpesa();
		listaSpesa = new ListaSpesa();

		addListToDatabase(lista);
		addListToDatabase(listaSpesa);

		assertThat(listaDao.findAll()).containsExactly(lista, listaSpesa);
	}

	@Test
	public void testFindAllListaDellaSpesaWhenDatabaseIsEmpty() {
		assertThat(listaDao.findAll()).isEmpty();
	}

	private void addListToDatabase(ListaSpesa listaDaSalvare) {
		transaction.executeTransaction((em) -> {
			em.persist(listaDaSalvare);
			return null;
		});
	}

}