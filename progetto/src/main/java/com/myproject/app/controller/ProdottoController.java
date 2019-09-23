package com.myproject.app.controller;

import com.myproject.app.dao.ProdottoDao;
import com.myproject.app.model.Prodotto;
import com.myproject.app.view.ProdottoViewInterface;

public class ProdottoController {

	private ProdottoViewInterface prodottoView;
	private ProdottoDao prodottoDao;

	public ProdottoController(ProdottoViewInterface prodottoView, ProdottoDao prodottoRepository) {
		this.prodottoView = prodottoView;
		this.prodottoDao = prodottoRepository;
	}

	public void allProducts() {
		prodottoView.showAllProducts(prodottoDao.findAll());
	}

	public void saveNewProduct(Prodotto prodotto) {
		Prodotto productAlreadyExist = prodottoDao.findById(prodotto.getId());

		if (productAlreadyExist != null) {
			prodottoView.showError("This product already exist", productAlreadyExist);
			return;
		}

		prodottoDao.save(prodotto);
		prodottoView.showNewProduct(prodotto);

	}

	public void deleteProduct(Prodotto prodottoDaCancellare) {
		Prodotto productAlreadyExist = prodottoDao.findById(prodottoDaCancellare.getId());

		if (productAlreadyExist != null) {
			prodottoDao.delete(productAlreadyExist.getId());
			prodottoView.showRemovedProduct(productAlreadyExist);
		}
		prodottoView.showError("This product does not exist", prodottoDaCancellare);
	}

	public void updateProduct(Prodotto prodottoDaModificare, String nuovoNomeProdotto, int nuovaQuantitaProdotto) {
		Prodotto productAlreadyExist = prodottoDao.findById(prodottoDaModificare.getId());
		
		if (productAlreadyExist != null && !nuovoNomeProdotto.isEmpty() && nuovoNomeProdotto != null
				&& nuovaQuantitaProdotto > 0) {
			prodottoDao.updateProduct(prodottoDaModificare, nuovoNomeProdotto, nuovaQuantitaProdotto);
			prodottoView.showNewProduct(productAlreadyExist);
		}
		prodottoView.showError("This product does not exist", prodottoDaModificare);
	}
}