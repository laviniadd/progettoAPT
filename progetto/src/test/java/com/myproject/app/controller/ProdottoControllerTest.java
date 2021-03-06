package com.myproject.app.controller;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.myproject.app.dao.ListaDellaSpesaDao;
import com.myproject.app.dao.ProdottoDao;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;
import com.myproject.app.view.AppViewInterface;

public class ProdottoControllerTest {
	@InjectMocks
	private ProdottoController prodottoController;

	@Mock
	private ProdottoDao prodottoDao;

	@Mock
	private ListaDellaSpesaDao listaDao;

	@Mock
	private AppViewInterface prodottoView;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAllProducts() {

		List<Prodotto> prodotti = asList(new Prodotto());
		when(prodottoDao.findAll()).thenReturn(prodotti);
		prodottoController.allProducts();
		verify(prodottoView).showAllEntities(prodotti);
	}

	@Test
	public void testAllProductsInAGivenList() {
		List<Prodotto> prodotti = new ArrayList<Prodotto>();
		Prodotto prodotto1 = new Prodotto();
		Prodotto prodotto2 = new Prodotto();
		prodotti.add(prodotto1);
		prodotti.add(prodotto2);
		ListaSpesa listaDaCuiRiprendereProdotti = new ListaSpesa();

		when(listaDao.findById(listaDaCuiRiprendereProdotti.getId())).thenReturn(listaDaCuiRiprendereProdotti);
		when(prodottoDao.findAllProductOfAList(listaDaCuiRiprendereProdotti)).thenReturn(prodotti);

		prodottoController.allProductsGivenAList(listaDaCuiRiprendereProdotti);

		verify(prodottoView).showAllEntities(prodotti);
	}

	@Test
	public void testAllProductsInAGivenListShowError() {
		ListaSpesa listaDaCuiRiprendereProdotti = new ListaSpesa();

		when(listaDao.findById(listaDaCuiRiprendereProdotti.getId())).thenReturn(null);

		prodottoController.allProductsGivenAList(listaDaCuiRiprendereProdotti);

		verify(prodottoView).showErrorEntityNotFound("This shopping list does not exist", listaDaCuiRiprendereProdotti);
		verifyNoMoreInteractions(ignoreStubs(prodottoDao, prodottoView));
	}

	@Test
	public void testSaveNewProductWithNameAndQuantityInAListCreatedWhenProductDoesNotAlreadyExist() {
		ListaSpesa lista = new ListaSpesa();
		Prodotto prodotto = new Prodotto("Mela", 2, lista);
		when(prodottoDao.findById(prodotto.getId())).thenReturn(null);
		when(listaDao.findById(lista.getId())).thenReturn(lista);

		prodottoController.saveNewProduct(prodotto);

		InOrder inOrder = inOrder(prodottoDao, prodottoView);
		inOrder.verify(prodottoDao).save(prodotto);
		inOrder.verify(prodottoView).showNewEntity(prodotto);
	}

	@Test
	public void testSaveNewProductWithNoNameNoQuantityNoListWhenProductDoesNotAlreadyExist() {
		Prodotto prodotto = new Prodotto();
		when(prodottoDao.findById(prodotto.getId())).thenReturn(null);		
		prodottoController.saveNewProduct(prodotto);
		verify(prodottoView).showError("This product has no valid name or quantity values", prodotto);
		verifyNoMoreInteractions(ignoreStubs(listaDao, prodottoDao));
	}

	@Test
	public void testSaveNewProductWithNoNameWhenProductDoesNotAlreadyExist() {
		ListaSpesa lista = new ListaSpesa();
		Prodotto prodotto = new Prodotto("", 2, lista);
		when(prodottoDao.findById(prodotto.getId())).thenReturn(null);
		when(listaDao.findById(lista.getId())).thenReturn(lista);
		prodottoController.saveNewProduct(prodotto);

		verify(prodottoView).showError("This product has no valid name or quantity values", prodotto);
		verifyNoMoreInteractions(ignoreStubs(prodottoDao));
	}

	@Test
	public void testSaveNewProductWithSpaceNameWhenProductDoesNotAlreadyExist() {
		ListaSpesa lista = new ListaSpesa();
		Prodotto prodotto = new Prodotto(" ", 2, lista);
		when(prodottoDao.findById(prodotto.getId())).thenReturn(null);
		when(listaDao.findById(lista.getId())).thenReturn(lista);

		prodottoController.saveNewProduct(prodotto);

		verify(prodottoView).showError("This product has no valid name or quantity values", prodotto);
		verifyNoMoreInteractions(ignoreStubs(prodottoDao));
	}

	@Test
	public void testSaveNewProductWithNullListWhenProductDoesNotAlreadyExist() {
		Prodotto prodotto = new Prodotto("Mela", 2, null);
		when(prodottoDao.findById(prodotto.getId())).thenReturn(null);

		prodottoController.saveNewProduct(prodotto);

		verify(prodottoView).showError("This product has no valid name or quantity values", prodotto);
		verifyNoMoreInteractions(ignoreStubs(prodottoDao, listaDao));
	}

	@Test
	public void testSaveNewProductWithListNotExistsWhenProductDoesNotAlreadyExist() {
		ListaSpesa lista = new ListaSpesa();
		Prodotto prodotto = new Prodotto("Mela", 2, lista);
		when(prodottoDao.findById(prodotto.getId())).thenReturn(null);
		when(listaDao.findById(lista.getId())).thenReturn(null);

		prodottoController.saveNewProduct(prodotto);

		verify(prodottoView).showError("This product has no valid name or quantity values", prodotto);
		verifyNoMoreInteractions(ignoreStubs(prodottoDao));
	}

	@Test
	public void testSaveNewProductWithInvalidQuantityWhenProductDoesNotAlreadyExist() {
		ListaSpesa lista = new ListaSpesa();
		Prodotto prodotto = new Prodotto("Mela", -2, lista);

		when(prodottoDao.findById(prodotto.getId())).thenReturn(null);
		when(listaDao.findById(lista.getId())).thenReturn(lista);

		prodottoController.saveNewProduct(prodotto);

		verify(prodottoView).showError("This product has no valid name or quantity values", prodotto);
		verifyNoMoreInteractions(ignoreStubs(prodottoDao));
	}

	@Test
	public void testSaveNewProductWithZeroQuantityWhenProductDoesNotAlreadyExist() {
		ListaSpesa lista = new ListaSpesa();
		Prodotto prodotto = new Prodotto("Mela", 0, lista);

		when(prodottoDao.findById(prodotto.getId())).thenReturn(null);
		when(listaDao.findById(lista.getId())).thenReturn(lista);

		prodottoController.saveNewProduct(prodotto);

		verify(prodottoView).showError("This product has no valid name or quantity values", prodotto);
		verifyNoMoreInteractions(ignoreStubs(prodottoDao));
	}

	@Test
	public void testSaveProductWhenProductAlreadyExist() {
		ListaSpesa lista = new ListaSpesa();
		Prodotto prodottoDaSalvare = new Prodotto("Mela", 2, lista);
		when(prodottoDao.findById(prodottoDaSalvare.getId())).thenReturn(prodottoDaSalvare);
		when(listaDao.findById(lista.getId())).thenReturn(lista);

		prodottoController.saveNewProduct(prodottoDaSalvare);

		verify(prodottoView).showError("This product already exist", prodottoDaSalvare);
		verifyNoMoreInteractions(ignoreStubs(prodottoDao));
	}

	@Test
	public void testDeleteProductWhenProductAlreadyExists() {
		Prodotto prodottoDaCancellare = new Prodotto();
		when(prodottoDao.findById(prodottoDaCancellare.getId())).thenReturn(prodottoDaCancellare);

		prodottoController.deleteProduct(prodottoDaCancellare);

		verify(prodottoDao).delete(prodottoDaCancellare.getId());
		verify(prodottoView).showRemovedEntity(prodottoDaCancellare);
	}

	@Test
	public void testDeleteProductWhenProductNotExists() {
		Prodotto prodottoDaCancellare = new Prodotto();

		when(prodottoDao.findById(prodottoDaCancellare.getId())).thenReturn(null);

		prodottoController.deleteProduct(prodottoDaCancellare);

		verify(prodottoView).showErrorEntityNotFound("This product does not exist", prodottoDaCancellare);
	}

	@Test
	public void testUpdateProductWhenProductExists() {
		Prodotto prodottoDaModificare = new Prodotto("Mela", 2, null);
		Prodotto prodottoModificato = new Prodotto("pera", 3, null);

		when(prodottoDao.findById(prodottoDaModificare.getId())).thenReturn(prodottoDaModificare);
		when(prodottoDao.updateProduct(prodottoDaModificare, "pera", 3)).thenReturn(prodottoModificato);

		prodottoController.updateProduct(prodottoDaModificare, "pera", 3);

		verify(prodottoDao).updateProduct(prodottoDaModificare, "pera", 3);
		verify(prodottoView).showNewEntity(prodottoModificato);
	}

	@Test
	public void testUpdateProductWhenProductNotExists() {
		Prodotto prodottoDaModificare = new Prodotto("mela", 2, null);

		when(prodottoDao.findById(prodottoDaModificare.getId())).thenReturn(null);

		prodottoController.updateProduct(prodottoDaModificare, "pera", 3);

		verify(prodottoView).showErrorEntityNotFound("This product does not exist", prodottoDaModificare);
		verifyNoMoreInteractions(ignoreStubs(prodottoDao, prodottoView));

	}

	@Test
	public void testUpdateProductWithNoNewNameWhenProductExists() {
		Prodotto prodottoDaModificare = new Prodotto("Mela", 2, null);

		when(prodottoDao.findById(prodottoDaModificare.getId())).thenReturn(prodottoDaModificare);

		prodottoController.updateProduct(prodottoDaModificare, null, 3);

		verify(prodottoView).showErrorEntityNotFound("This product does not exist", prodottoDaModificare);
		verifyNoMoreInteractions(ignoreStubs(prodottoDao, prodottoView));

	}

	@Test
	public void testUpdateProductWithEmptyNewNameWhenProductExists() {
		Prodotto prodottoDaModificare = new Prodotto("Mela", 2, null);

		when(prodottoDao.findById(prodottoDaModificare.getId())).thenReturn(prodottoDaModificare);

		prodottoController.updateProduct(prodottoDaModificare, "", 1);

		verify(prodottoView).showErrorEntityNotFound("This product does not exist", prodottoDaModificare);
		verifyNoMoreInteractions(ignoreStubs(prodottoDao, prodottoView));

	}

	@Test
	public void testUpdateProductWithSpaceNewNameWhenProductExists() {
		Prodotto prodottoDaModificare = new Prodotto("mela", 2, null);

		when(prodottoDao.findById(prodottoDaModificare.getId())).thenReturn(prodottoDaModificare);

		prodottoController.updateProduct(prodottoDaModificare, " ", 1);

		verify(prodottoView).showErrorEntityNotFound("This product does not exist", prodottoDaModificare);
		verifyNoMoreInteractions(ignoreStubs(prodottoDao, prodottoView));

	}

	@Test
	public void testUpdateProductWithNegativeNewQuantityWhenProductExists() {
		Prodotto prodottoDaModificare = new Prodotto("mela", 2, null);

		when(prodottoDao.findById(prodottoDaModificare.getId())).thenReturn(prodottoDaModificare);

		prodottoController.updateProduct(prodottoDaModificare, "pera", -1);

		verify(prodottoView).showErrorEntityNotFound("This product does not exist", prodottoDaModificare);
		verifyNoMoreInteractions(ignoreStubs(prodottoDao, prodottoView));

	}
	
	@Test
	public void testUpdateProductWithZeroNewQuantityWhenProductExists() {
		Prodotto prodottoDaModificare = new Prodotto("mela", 2, null);

		when(prodottoDao.findById(prodottoDaModificare.getId())).thenReturn(prodottoDaModificare);

		prodottoController.updateProduct(prodottoDaModificare, "Mela", 0);

		verify(prodottoView).showErrorEntityNotFound("This product does not exist", prodottoDaModificare);
	}

	@Test
	public void testUpdateProductWithNullNameWhenProductExists() {
		Prodotto prodottoDaModificare = new Prodotto("mela", 2, null);

		when(prodottoDao.findById(prodottoDaModificare.getId())).thenReturn(prodottoDaModificare);

		prodottoController.updateProduct(prodottoDaModificare, null, 1);

		verify(prodottoView).showErrorEntityNotFound("This product does not exist", prodottoDaModificare);
	}

}
