package com.myproject.app.controllerIT;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.myproject.app.controller.ProdottoController;
import com.myproject.app.dao.ListaDellaSpesaDao;
import com.myproject.app.dao.ProdottoDao;
import com.myproject.app.dao.TransactionTemplate;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;
import com.myproject.app.view.AppViewInterface;

public class ProdottoControllerIT extends ITController {
	private ProdottoController prodottoController;

	@Mock
	private AppViewInterface prodottoView;

	private ProdottoDao prodottoDao;
	private ListaDellaSpesaDao listaSpesaDao;

	@Override
	protected void init(TransactionTemplate transaction) throws InitializationError {
		MockitoAnnotations.initMocks(this);

		prodottoDao = new ProdottoDao(transaction);
		listaSpesaDao = new ListaDellaSpesaDao(transaction);
		prodottoController = new ProdottoController(prodottoView, prodottoDao);
	}

	@Test
	public void testAllProducts() {

		Prodotto prodotto = new Prodotto();

		prodottoDao.save(prodotto);

		prodottoController.allProducts();

		verify(prodottoView).showAllEntities(asList(prodotto));

	}

	@Test
	public void testSaveNewProductWithNameAndQuantityInAListCreatedWhenProductDoesNotAlreadyExist() {
		ListaSpesa lista = new ListaSpesa();
		listaSpesaDao.save(lista);
		Prodotto prodotto = new Prodotto();
		prodotto.setName("Mela");
		prodotto.setQuantity(2);
		prodotto.setListaSpesa(lista);

		prodottoController.saveNewProduct(prodotto);

		verify(prodottoView).showNewEntity(prodotto);
	}

	@Test
	public void testDeleteProductWhenProductAlreadyExists() {
		Prodotto prodottoDaCancellare = new Prodotto();

		prodottoDao.save(prodottoDaCancellare);

		prodottoController.deleteProduct(prodottoDaCancellare);

		verify(prodottoView).showRemovedEntity(prodottoDaCancellare);
	}

	@Test
	public void testUpdateProductWhenProductExists() {
		Prodotto prodottoDaModificare = new Prodotto();
		prodottoDaModificare.setName("mela");
		prodottoDaModificare.setQuantity(2);

		prodottoDao.save(prodottoDaModificare);

		prodottoController.updateProduct(prodottoDaModificare, "pera", 3);

		verify(prodottoView).showNewEntity(prodottoDaModificare);
	}

	@Test
	public void testAllProductsInAGivenList() {
		ListaSpesa listaDaCuiRiprendereProdotti = new ListaSpesa();
		Prodotto prodotto1 = new Prodotto();
		prodotto1.setListaSpesa(listaDaCuiRiprendereProdotti);
		Prodotto prodotto2 = new Prodotto();
		prodotto2.setListaSpesa(listaDaCuiRiprendereProdotti);
		List<Prodotto> prodotti = new ArrayList<Prodotto>();
		prodotti.add(prodotto1);
		prodotti.add(prodotto2);

		listaSpesaDao.save(listaDaCuiRiprendereProdotti);
		prodottoDao.save(prodotto1);
		prodottoDao.save(prodotto2);

		prodottoController.allProductsGivenAList(listaDaCuiRiprendereProdotti);

		verify(prodottoView).showAllEntities(prodotti);
	}
}
