package com.myproject.app.controller;

import com.myproject.app.dao.ListaDellaSpesaDao;
import com.myproject.app.dao.ProdottoDao;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;
import com.myproject.app.view.AppViewInterface;

public class ProdottoController {

	private String productNotExists = "This product does not exist";
	private AppViewInterface prodottoView;
	private ProdottoDao prodottoDao;
	private ListaDellaSpesaDao listaDellaSpesaDao;

	public ProdottoController(AppViewInterface prodottoView, ProdottoDao prodottoRepository,
			ListaDellaSpesaDao listaDellaSpesaDao) {
		this.prodottoView = prodottoView;
		this.prodottoDao = prodottoRepository;
		this.listaDellaSpesaDao = listaDellaSpesaDao;
	}

	public void allProducts() {
		prodottoView.showAllEntities(prodottoDao.findAll());
	}

	public void allProductsGivenAList(ListaSpesa lista) {
		ListaSpesa listAlreadyExist = listaDellaSpesaDao.findById(lista.getId());
		if (listAlreadyExist == null) {
			prodottoView.showErrorEntityNotFound("This shopping list does not exist", lista);
		} else {
			prodottoView.showAllEntities(prodottoDao.findAllProductOfAList(lista));
		}
	}

	public void saveNewProduct(Prodotto prodotto) {
		ListaSpesa existedList = new ListaSpesa();	
		Prodotto productAlreadyExist = prodottoDao.findById(prodotto.getId());
		if(prodotto.getListaSpesa()!=null) {
			existedList = listaDellaSpesaDao.findById(prodotto.getListaSpesa().getId());
		}
		
		if (productAlreadyExist != null) {
			prodottoView.showError("This product already exist", productAlreadyExist);
		} else if (prodotto.getName() == null || prodotto.getName().equals("") || prodotto.getName().equals(" ")
				|| prodotto.getQuantity() <= 0
				|| prodotto.getListaSpesa() == null || 
				existedList == null) {
			prodottoView.showError("This product has no valid name or quantity values", prodotto);
		} else {
			prodottoDao.save(prodotto);
			prodottoView.showNewEntity(prodotto);
		}
	}

	public void deleteProduct(Prodotto prodottoDaCancellare) {
		Prodotto productAlreadyExist = prodottoDao.findById(prodottoDaCancellare.getId());

		if (productAlreadyExist == null) {
			prodottoView.showErrorEntityNotFound(productNotExists, prodottoDaCancellare);
		} else {
			prodottoDao.delete(productAlreadyExist.getId());
			prodottoView.showRemovedEntity(productAlreadyExist);
		}
	}

	public void updateProduct(Prodotto prodottoDaModificare, String nuovoNomeProdotto, int nuovaQuantitaProdotto) {
		Prodotto productAlreadyExist = prodottoDao.findById(prodottoDaModificare.getId());

		if (nuovoNomeProdotto == null || productAlreadyExist == null) {
			prodottoView.showErrorEntityNotFound(productNotExists, prodottoDaModificare);
		} else {
			if (nuovoNomeProdotto.equals("") || nuovoNomeProdotto.equals(" ") || nuovaQuantitaProdotto <= 0) {
				prodottoView.showErrorEntityNotFound(productNotExists, prodottoDaModificare);
			} else {
				Prodotto prodottoModificato = prodottoDao.updateProduct(prodottoDaModificare, nuovoNomeProdotto,
						nuovaQuantitaProdotto);
				prodottoView.showNewEntity(prodottoModificato);
			}
		}

	}

}
