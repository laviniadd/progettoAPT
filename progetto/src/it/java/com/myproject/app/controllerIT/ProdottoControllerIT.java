package com.myproject.app.controllerIT;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runners.model.InitializationError;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.myproject.app.controller.ProdottoController;
import com.myproject.app.dao.ProdottoDao;
import com.myproject.app.dao.TransactionTemplate;
import com.myproject.app.model.Prodotto;
import com.myproject.app.view.ProdottoViewInterface;

public class ProdottoControllerIT extends ITController{
	private ProdottoController prodottoController;
	
	@Mock
	private ProdottoViewInterface prodottoView;
	
	private ProdottoDao prodottoDao;
	
	@Override
	protected void init(TransactionTemplate transaction) throws InitializationError {
		MockitoAnnotations.initMocks(this);

		prodottoDao = new ProdottoDao(transaction);

		prodottoController = new ProdottoController(prodottoView, prodottoDao);
	}
	
	@Test
	public void testAllProducts() {

		Prodotto prodotto = new Prodotto();

		prodottoDao.save(prodotto);
		
		prodottoController.allProducts();

		verify(prodottoView).showAllProducts(asList(prodotto));

	}
	
	@Test
	public void testSaveNewProductWhenProductDoesNotAlreadyExist() {
		Prodotto prodotto = new Prodotto();
			
		prodottoController.saveNewProduct(prodotto);
		
		verify(prodottoView).showNewProduct(prodotto);
	}
	
	@Test
	public void testDeleteProductWhenProductAlreadyExists() {
		Prodotto prodottoDaCancellare = new Prodotto();

		prodottoDao.save(prodottoDaCancellare);

		prodottoController.deleteProduct(prodottoDaCancellare);

		verify(prodottoView).showRemovedProduct(prodottoDaCancellare);
	}

	@Test
	public void testUpdateProductWhenProductExists() {
		Prodotto prodottoDaModificare = new Prodotto();
		prodottoDaModificare.setName("mela");
		prodottoDaModificare.setQuantity(2);

		prodottoDao.save(prodottoDaModificare);

		prodottoController.updateProduct(prodottoDaModificare, "pera", 3);

		verify(prodottoView).showNewProduct(prodottoDaModificare);
	}
}
