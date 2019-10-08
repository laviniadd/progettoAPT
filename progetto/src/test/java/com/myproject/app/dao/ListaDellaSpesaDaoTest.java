package com.myproject.app.dao;

import static org.assertj.core.api.Assertions.*;
import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;
import com.myproject.app.model.ListaSpesa;

public class ListaDellaSpesaDaoTest extends JpaTest {

	private ListaDellaSpesaDao listaSpesaDao;
	private ListaSpesa lista;
	private ListaSpesa listaSpesa;
	private TransactionTemplate transaction;

	@Override
	protected void init(TransactionTemplate transaction) throws InitializationError {
		listaSpesaDao = new ListaDellaSpesaDao(transaction);
		this.transaction = transaction;
	}

	@Test
	public void testSaveListaDellaSpesa() {
		lista = new ListaSpesa();

		listaSpesaDao.save(lista);

		List<ListaSpesa> retrievedList = retrieveShoppingListToDatabase(lista);

		assertThat(retrievedList).containsExactly(lista);
	}

	@Test
	public void testSaveNull() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			listaSpesaDao.save(null);
		});
	}

	@Test
	public void testListaDellaSpesaFindById() {
		lista = new ListaSpesa();
		listaSpesa = new ListaSpesa();

		addListToDatabase(lista);
		addListToDatabase(listaSpesa);

		assertThat(listaSpesaDao.findById(listaSpesa.getId())).isEqualTo(listaSpesa);
	}

	@Test
	public void testListaDellaSpesaFindByIdNull() {
		assertThat(listaSpesaDao.findById(null)).isEqualTo(null);
	}

	@Test
	public void testListaDellaSpesaNotAlreadySavedFindByIdNull() {
		ListaSpesa listaSpesaNotSaved = new ListaSpesa();
		assertThat(listaSpesaDao.findById(listaSpesaNotSaved.getId())).isEqualTo(null);
	}

	@Test
	public void testListaDellaSpesaFindByIdNotFound() {

		assertThat(listaSpesaDao.findById(Long.valueOf(1))).isNull();
	}

	@Test
	public void testListaDellaSpesaFindByName() {
		lista = new ListaSpesa("lista");
		listaSpesa = new ListaSpesa("listaSpesa");

		addListToDatabase(lista);
		addListToDatabase(listaSpesa);

		assertThat(listaSpesaDao.findByName(listaSpesa.getName())).containsExactly(listaSpesa);
	}

	@Test
	public void testListaDellaSpesaFindByNameNull() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			listaSpesaDao.findByName(null);
		});
	}

	@Test
	public void testListaDellaSpesaFindByNameEmpty() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			listaSpesaDao.findByName("");
		});
	}

	@Test
	public void testListaDellaSpesaFindByNameEmptySpace() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			listaSpesaDao.findByName(" ");
		});
	}

	@Test
	public void testListaDellaSpesaNotAlreadySavedFindByName() {
		ListaSpesa listaSpesaNotSaved = new ListaSpesa("Lista");
		assertThat(listaSpesaDao.findByName(listaSpesaNotSaved.getName())).isEmpty();
	}

	@Test
	public void testListaDellaSpesaFindByNameNotFound() {
		assertThat(listaSpesaDao.findByName("Lista")).isEmpty();
	}

	@Test
	public void testFindAllListaDellaSpesaWhenDatabaseIsNotEmpty() {
		lista = new ListaSpesa();
		listaSpesa = new ListaSpesa();

		addListToDatabase(lista);
		addListToDatabase(listaSpesa);

		assertThat(listaSpesaDao.findAll()).containsExactly(lista, listaSpesa);
	}

	@Test
	public void testFindAllListaDellaSpesaWhenDatabaseIsEmpty() {
		assertThat(listaSpesaDao.findAll()).isEmpty();
	}

	@Test
	public void testDeleteLista() {
		lista = new ListaSpesa();
		addListToDatabase(lista);

		listaSpesaDao.delete(lista.getId());

		List<ListaSpesa> retrievedShoppingList = retrieveShoppingListToDatabase(lista);
		assertThat(retrievedShoppingList).isEmpty();
	}

	@Test
	public void testDeleteListaNull() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			listaSpesaDao.delete(null);
		});
	}

	private void addListToDatabase(ListaSpesa listaDaSalvare) {
		transaction.executeTransaction((em) -> {
			em.persist(listaDaSalvare);
			return null;
		});
	}

	private List<ListaSpesa> retrieveShoppingListToDatabase(ListaSpesa listaSpesaDaRecuperare) {
		return transaction.executeTransaction((em) -> {
			return em.createQuery("select e from ListaSpesa e where e.id = :id", ListaSpesa.class)
					.setParameter("id", listaSpesaDaRecuperare.getId()).getResultList();
		});
	}

}