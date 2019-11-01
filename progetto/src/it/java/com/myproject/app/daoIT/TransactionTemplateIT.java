package com.myproject.app.daoIT;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import com.myproject.app.dao.TransactionTemplate;
import com.myproject.app.model.ListaSpesa;

public class TransactionTemplateIT extends ITDao{
	private TransactionTemplate transaction;
	@Override
	protected void init(TransactionTemplate transaction) throws InitializationError {
		this.transaction = transaction;
	}
	
	@Test
	public void testWhenABlockOfCodeIsNotNull() {
		ListaSpesa lista = new ListaSpesa();
		addListToDatabase(lista);
		
		ListaSpesa listaRipresaDalDb = transaction.executeTransaction(em -> em.find(ListaSpesa.class, lista.getId()));
		
		assertThat(listaRipresaDalDb).isEqualTo(lista);
	}
	
	private void addListToDatabase(ListaSpesa listaDaSalvare) {
		transaction.executeTransaction((em) -> {
			em.persist(listaDaSalvare);
			em.clear();
			return null;
		});
	}
}
