package com.myproject.app.controller;

import com.myproject.app.dao.ListaDellaSpesaDao;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.view.ListaSpesaView;

public class ListaSpesaController {

	private ListaSpesaView listaView;
	private ListaDellaSpesaDao listaDao;

	public ListaSpesaController(ListaSpesaView listaView, ListaDellaSpesaDao listaRepository) {
		this.listaView = listaView;
		this.listaDao = listaRepository;
	}

	public void allListeSpesa() {
		listaView.showAllListeSpesa(listaDao.findAll());
	}

	public void saveNewLista(ListaSpesa lista) {
		ListaSpesa listaAlreadyExist = listaDao.findById(lista.getId());

		if (listaAlreadyExist != null) {
			listaView.showError("This shopping list already exist", listaAlreadyExist);

			return;
		}

		listaDao.save(lista);
		listaView.showNewLista(lista);
	}

	public void deleteListaSpesa(ListaSpesa listaDaCancellare) {
		ListaSpesa listaAlreadyExist = listaDao.findById(listaDaCancellare.getId());

		if (listaAlreadyExist != null) {
			listaDao.delete(listaAlreadyExist.getId());
			listaView.showRemovedList(listaAlreadyExist);
		} else {
			listaView.showError("This shopping list does not exist", listaDaCancellare);
		}
	}

}
