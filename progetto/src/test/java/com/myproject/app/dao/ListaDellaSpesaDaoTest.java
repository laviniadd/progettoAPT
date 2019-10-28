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
	public void testSave() {
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
	public void testFindById() {
		lista = new ListaSpesa();
		listaSpesa = new ListaSpesa();
		addListToDatabase(lista);
		addListToDatabase(listaSpesa);
		assertThat(listaSpesaDao.findById(listaSpesa.getId())).isEqualTo(listaSpesa);
	}

	@Test
	public void testFindByIdWithNullList() {
		assertThat(listaSpesaDao.findById(null)).isNull();
	}

	@Test
	public void testListNotAlreadySavedFindByIdNull() {
		ListaSpesa listaSpesaNotSaved = new ListaSpesa();
		assertThat(listaSpesaDao.findById(listaSpesaNotSaved.getId())).isNull();
	}

	@Test
	public void testFindByIdNotFound() {
		assertThat(listaSpesaDao.findById(Long.valueOf(1))).isNull();
	}

	@Test
	public void testFindByName() {
		lista = new ListaSpesa("lista");
		listaSpesa = new ListaSpesa("listaSpesa");

		addListToDatabase(lista);
		addListToDatabase(listaSpesa);

		assertThat(listaSpesaDao.findByName(listaSpesa.getName())).containsExactly(listaSpesa);
	}

	@Test
	public void testFindByNameNull() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			listaSpesaDao.findByName(null);
		});
	}

	@Test
	public void testFindByNameEmpty() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			listaSpesaDao.findByName("");
		});
	}

	@Test
	public void testFindByNameSpace() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			listaSpesaDao.findByName(" ");
		});
	}

	@Test
	public void testListNotAlreadySavedFindByName() {
		ListaSpesa listaSpesaNotSaved = new ListaSpesa("Lista");
		assertThat(listaSpesaDao.findByName(listaSpesaNotSaved.getName())).isEmpty();
	}

	@Test
	public void testFindByNameNotFound() {
		assertThat(listaSpesaDao.findByName("Lista")).isEmpty();
	}

	@Test
	public void testFindAllListsWhenDatabaseIsNotEmpty() {
		lista = new ListaSpesa();
		listaSpesa = new ListaSpesa();

		addListToDatabase(lista);
		addListToDatabase(listaSpesa);

		assertThat(listaSpesaDao.findAll()).containsExactly(lista, listaSpesa);
	}

	@Test
	public void testFindAllListsWhenDatabaseIsEmpty() {
		assertThat(listaSpesaDao.findAll()).isEmpty();
	}

	@Test
	public void testDelete() {
		lista = new ListaSpesa();
		addListToDatabase(lista);

		listaSpesaDao.delete(lista.getId());

		List<ListaSpesa> retrievedShoppingList = retrieveShoppingListToDatabase(lista);
		assertThat(retrievedShoppingList).isEmpty();
	}

	@Test
	public void testDeleteNullList() {
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