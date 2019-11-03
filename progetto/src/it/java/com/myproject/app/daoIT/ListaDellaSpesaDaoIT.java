package com.myproject.app.daoIT;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import com.myproject.app.dao.ListaDellaSpesaDao;
import com.myproject.app.dao.TransactionTemplate;
import com.myproject.app.model.ListaSpesa;

public class ListaDellaSpesaDaoIT extends ITDao {

	private TransactionTemplate transaction;
	private ListaDellaSpesaDao listaSpesaDao;
	private ListaSpesa lista;
	private ListaSpesa listaSpesa;

	@Override
	protected void init(TransactionTemplate transaction) throws InitializationError {
		listaSpesaDao = new ListaDellaSpesaDao(transaction);
		this.transaction = transaction;
	}

	@Test
	public void testSave() {
		lista = new ListaSpesa();
		listaSpesaDao.save(lista);
		List<ListaSpesa> retrievedList = retrieveShoppingListToDatabase();
		assertThat(retrievedList).containsExactly(lista);
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
	public void testFindByName() {
		lista = new ListaSpesa("lista");
		listaSpesa = new ListaSpesa("listaSpesa");
		addListToDatabase(lista);
		addListToDatabase(listaSpesa);
		assertThat(listaSpesaDao.findByName(listaSpesa.getName())).containsExactly(listaSpesa);
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
	public void testDelete() {
		lista = new ListaSpesa();
		addListToDatabase(lista);
		listaSpesaDao.delete(lista.getId());
		List<ListaSpesa> retrievedShoppingList = retrieveShoppingListToDatabase();
		assertThat(retrievedShoppingList).isEmpty();
	}

	private void addListToDatabase(ListaSpesa listaDaSalvare) {
		transaction.executeTransaction((em) -> {
			em.persist(listaDaSalvare);
			return null;
		});
	}

	private List<ListaSpesa> retrieveShoppingListToDatabase() {
		return transaction.executeTransaction((em) -> {
			return em.createQuery("select e from ListaSpesa e", ListaSpesa.class).getResultList();
		});
	}

}
