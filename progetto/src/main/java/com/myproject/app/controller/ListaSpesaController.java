package com.myproject.app.controller;

import java.util.List;

import com.myproject.app.dao.ListaDellaSpesaDao;
import com.myproject.app.dao.ProdottoDao;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;
import com.myproject.app.view.AppViewInterface;

public class ListaSpesaController {

	private AppViewInterface listaView;
	private ListaDellaSpesaDao listaDao;
	private ProdottoDao prodottoDao;

	public ListaSpesaController(AppViewInterface listaView, ListaDellaSpesaDao listaRepository,
			ProdottoDao prodottoDao) {
		this.listaView = listaView;
		this.listaDao = listaRepository;
		this.prodottoDao = prodottoDao;
	}

	public void allListeSpesa() {
		listaView.showAllEntities(listaDao.findAll());
	}

	public void saveNewLista(ListaSpesa lista) {
		ListaSpesa listaAlreadyExist = listaDao.findById(lista.getId());

		if (listaAlreadyExist != null) {
			listaView.showError("This shopping list already exist", listaAlreadyExist);
		} else if (lista.getName() == null || lista.getName().equals("") || lista.getName().equals(" ")) {
			listaView.showError("This shopping list does not have name", listaAlreadyExist);
		} else {
			listaDao.save(lista);
			listaView.showNewEntity(lista);
		}
	}

	public void deleteListaSpesa(ListaSpesa listaDaCancellare) {
		ListaSpesa listaAlreadyExist = listaDao.findById(listaDaCancellare.getId());

		if (listaAlreadyExist == null) {
			listaView.showErrorEntityNotFound("This shopping list does not exist", listaDaCancellare);
		} else {
			List<Prodotto> prodottiDellaListaDaCancellare = prodottoDao.findAllProductOfAList(listaDaCancellare);
			if (prodottiDellaListaDaCancellare.isEmpty()) {
				listaDao.delete(listaAlreadyExist.getId());
				listaView.showRemovedEntity(listaAlreadyExist);
			} else {
				while (!prodottiDellaListaDaCancellare.isEmpty()) {
					Prodotto prodottoDaCancellare = prodottiDellaListaDaCancellare.remove(0);
					prodottoDao.delete(prodottoDaCancellare.getId());
					listaView.showRemovedEntity(prodottoDaCancellare);
				}
				listaDao.delete(listaAlreadyExist.getId());
				listaView.showRemovedEntity(listaAlreadyExist);
			}
		}
	}
}
