package com.myproject.app.controllerIT;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runners.model.InitializationError;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.myproject.app.controller.ListaSpesaController;
import com.myproject.app.dao.ListaDellaSpesaDao;
import com.myproject.app.dao.ProdottoDao;
import com.myproject.app.dao.TransactionTemplate;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;
import com.myproject.app.view.AppViewInterface;

public class ListaSpesaControllerIT extends ITController {

	private ListaSpesaController listaSpesaController;

	@Mock
	private AppViewInterface listaSpesaView;
	private ListaDellaSpesaDao listaSpesaDao;
	private ProdottoDao prodottoDao;

	@Override
	protected void init(TransactionTemplate transaction) throws InitializationError {
		MockitoAnnotations.initMocks(this);

		listaSpesaDao = new ListaDellaSpesaDao(transaction);
		prodottoDao = new ProdottoDao(transaction);
		listaSpesaController = new ListaSpesaController(listaSpesaView, listaSpesaDao, prodottoDao);
	}

	@Test
	public void testAllListaSpesa() {

		ListaSpesa listaSpesa = new ListaSpesa();

		listaSpesaDao.save(listaSpesa);

		listaSpesaController.allListeSpesa();

		verify(listaSpesaView).showAllEntities(asList(listaSpesa));

	}

	@Test
	public void testSaveNewListaWithNameWhenListaDoesNotAlreadyExist() {
		ListaSpesa lista = new ListaSpesa("lista della spesa");
		listaSpesaController.saveNewLista(lista);
		verify(listaSpesaView).showNewEntity(lista);
	}

	@Test
	public void testDeleteListaWithProductsWhenListaAlreadyExists() {
		ListaSpesa listaDaCancellare = new ListaSpesa("spesa");
		listaSpesaDao.save(listaDaCancellare);
		Prodotto prodotto1 = new Prodotto("mela", 2, listaDaCancellare);
		Prodotto prodotto2 = new Prodotto("pera", 1, listaDaCancellare);
		prodottoDao.save(prodotto1);
		prodottoDao.save(prodotto2);

		listaSpesaController.deleteListaSpesa(listaDaCancellare);

		verify(listaSpesaView).showRemovedEntity(listaDaCancellare);
	}
}
