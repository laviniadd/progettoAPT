package com.myproject.app.controller;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.myproject.app.dao.ProdottoDao;
import com.myproject.app.model.Prodotto;
import com.myproject.app.view.ProdottoViewInterface;

public class ProdottoControllerTest {
	@InjectMocks
	private ProdottoController prodottoController;

	@Mock
	private ProdottoDao prodottoDao;

	@Mock
	private ProdottoViewInterface prodottoView;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAllProducts() {

		List<Prodotto> prodotti = asList(new Prodotto());

		when(prodottoDao.findAll()).thenReturn(prodotti);

		prodottoController.allProducts();

		verify(prodottoView).showAllProducts(prodotti);

	}

	@Test
	public void testSaveNewProductWhenProductDoesNotAlreadyExist() {
		Prodotto prodotto = new Prodotto();
		when(prodottoDao.findById(prodotto.getId())).thenReturn(null);
		prodottoController.saveNewProduct(prodotto);
		InOrder inOrder = inOrder(prodottoDao, prodottoView);
		inOrder.verify(prodottoDao).save(prodotto);
		inOrder.verify(prodottoView).showNewProduct(prodotto);
	}

	@Test
	public void testSaveProductWhenProductAlreadyExist() {
		Prodotto prodottoDaSalvare = new Prodotto();

		when(prodottoDao.findById(prodottoDaSalvare.getId())).thenReturn(prodottoDaSalvare);

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
		verify(prodottoView).showRemovedProduct(prodottoDaCancellare);
	}

	@Test
	public void testDeleteProductWhenProductNotExists() {
		Prodotto prodottoDaCancellare = new Prodotto();

		when(prodottoDao.findById(prodottoDaCancellare.getId())).thenReturn(null);

		prodottoController.deleteProduct(prodottoDaCancellare);

		verify(prodottoView).showError("This product does not exist", prodottoDaCancellare);
	}
	
	@Test
	public void testUpdateProductWhenProductExists() {
		Prodotto prodottoDaModificare = new Prodotto();
		prodottoDaModificare.setName("mela");
		prodottoDaModificare.setQuantity(2);

		when(prodottoDao.findById(prodottoDaModificare.getId())).thenReturn(prodottoDaModificare);

		prodottoController.updateProduct(prodottoDaModificare, "pera", 3);

		verify(prodottoDao).updateProduct(prodottoDaModificare, "pera", 3);
		verify(prodottoView).showNewProduct(prodottoDaModificare);
	}
	
	@Test
	public void testUpdateProductWhenProductNotExists() {
		Prodotto prodottoDaModificare = new Prodotto();
		prodottoDaModificare.setName("mela");
		prodottoDaModificare.setQuantity(2);

		when(prodottoDao.findById(prodottoDaModificare.getId())).thenReturn(null);

		prodottoController.updateProduct(prodottoDaModificare, "pera", 3);

		verify(prodottoView).showError("This product does not exist", prodottoDaModificare);
	}
	
	@Test
	public void testUpdateProductWithNoNewNameWhenProductExists() {
		Prodotto prodottoDaModificare = new Prodotto();
		prodottoDaModificare.setName("mela");
		prodottoDaModificare.setQuantity(2);

		when(prodottoDao.findById(prodottoDaModificare.getId())).thenReturn(null);

		prodottoController.updateProduct(prodottoDaModificare, null, 3);

		verify(prodottoView).showError("This product does not exist", prodottoDaModificare);
	}
	
	@Test
	public void testUpdateProductWithNoNewQuantityWhenProductExists() {
		Prodotto prodottoDaModificare = new Prodotto();
		prodottoDaModificare.setName("mela");
		prodottoDaModificare.setQuantity(2);

		when(prodottoDao.findById(prodottoDaModificare.getId())).thenReturn(null);

		prodottoController.updateProduct(prodottoDaModificare, "pera", -1);

		verify(prodottoView).showError("This product does not exist", prodottoDaModificare);
	}



}
