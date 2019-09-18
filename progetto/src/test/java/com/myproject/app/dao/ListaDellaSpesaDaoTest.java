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
	public void testListaDellaSpesaFindById() {
		lista = new ListaSpesa();
		listaSpesa = new ListaSpesa();

		addListToDatabase(lista);
		addListToDatabase(listaSpesa);
				
		assertThat(listaSpesaDao.findById(listaSpesa.getId())).isEqualTo(listaSpesa);
	}

	@Test
	public void testListaDellaSpesaFindByIdNotFound() {

		assertThat(listaSpesaDao.findById(Long.valueOf(1))).isNull();
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