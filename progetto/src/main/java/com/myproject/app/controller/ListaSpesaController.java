package com.myproject.app.controller;

import com.myproject.app.dao.ListaDellaSpesaDao;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.view.AppViewInterface;

public class ListaSpesaController {

	private AppViewInterface listaView;
	private ListaDellaSpesaDao listaDao;

	public ListaSpesaController(AppViewInterface listaView, ListaDellaSpesaDao listaRepository) {
		this.listaView = listaView;
		this.listaDao = listaRepository;
	}

	public void allListeSpesa() {
		listaView.showAllEntity(listaDao.findAll());
	}

	public void saveNewLista(ListaSpesa lista) {
		ListaSpesa listaAlreadyExist = listaDao.findById(lista.getId());

		if (listaAlreadyExist != null) {
			listaView.showError("This shopping list already exist", listaAlreadyExist);
			return;
		}

		listaDao.save(lista);
		listaView.showNewEntity(lista);

	}

	public void deleteListaSpesa(ListaSpesa listaDaCancellare) {
		ListaSpesa listaAlreadyExist = listaDao.findById(listaDaCancellare.getId());

		if (listaAlreadyExist != null) {
			listaDao.delete(listaAlreadyExist.getId());
			listaView.showRemovedEntity(listaAlreadyExist);
		}
		listaView.showError("This shopping list does not exist", listaDaCancellare);
	}

}
