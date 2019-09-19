package com.myproject.app.controllerIT;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import com.myproject.app.controller.ListaSpesaController;
import com.myproject.app.dao.ListaDellaSpesaDao;
import com.myproject.app.dao.TransactionTemplate;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.view.ListaSpesaView;

public class ListaSpesaControllerIT extends ITController{
	private TransactionTemplate transaction;
	private ListaDellaSpesaDao listaSpesaDao;
	private ListaSpesaController listaSpesaController;
	private ListaSpesaView listaSpesaView;
	
	@Override
	protected void init(TransactionTemplate transaction) throws InitializationError {
		listaSpesaDao = new ListaDellaSpesaDao(transaction);
		this.transaction = transaction;
	}
	
	@Test
	public void testAllListaSpesa() {

		ListaSpesa listaDellaSpesa = new ListaSpesa();
		ListaSpesa listaSpesa = new ListaSpesa();
		
		listaSpesaDao.save(listaSpesa);
		listaSpesaDao.save(listaDellaSpesa);
		
		List<ListaSpesa> listeSpesaSalvate = new ArrayList<ListaSpesa>(); 
		
		listaSpesaController.allListeSpesa();

		verify(listaSpesaView).showAllListeSpesa(listeSpesaSalvate);

	}
}
