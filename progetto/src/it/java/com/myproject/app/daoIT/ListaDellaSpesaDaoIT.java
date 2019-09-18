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
