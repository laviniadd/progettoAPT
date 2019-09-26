package com.myproject.app.controllerIT;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runners.model.InitializationError;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.myproject.app.controller.ListaSpesaController;
import com.myproject.app.dao.ListaDellaSpesaDao;
import com.myproject.app.dao.TransactionTemplate;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.view.AppViewInterface;

public class ListaSpesaControllerIT extends ITController {

	private ListaSpesaController listaSpesaController;

	@Mock
	private AppViewInterface listaSpesaView;

	private ListaDellaSpesaDao listaSpesaDao;

	@Override
	protected void init(TransactionTemplate transaction) throws InitializationError {
		MockitoAnnotations.initMocks(this);

		listaSpesaDao = new ListaDellaSpesaDao(transaction);

		listaSpesaController = new ListaSpesaController(listaSpesaView, listaSpesaDao);
	}

	@Test
	public void testAllListaSpesa() {

		ListaSpesa listaSpesa = new ListaSpesa();

		listaSpesaDao.save(listaSpesa);

		listaSpesaController.allListeSpesa();

		verify(listaSpesaView).showAllEntities(asList(listaSpesa));

	}

	@Test
	public void testSaveNewListaWhenListaDoesNotAlreadyExist() {
		ListaSpesa lista = new ListaSpesa();
		listaSpesaController.saveNewLista(lista);
		verify(listaSpesaView).showNewEntity(lista);
	}
	
	@Test
	public void testDeleteListaWhenListaAlreadyExists() {
		ListaSpesa listaDaCancellare = new ListaSpesa();
		listaSpesaDao.save(listaDaCancellare);				
		listaSpesaController.deleteListaSpesa(listaDaCancellare);
		
		verify(listaSpesaView).showRemovedEntity(listaDaCancellare);
	}
}
