package com.myproject.app.controller;

import com.myproject.app.dao.ElencoProdottiDao;
import com.myproject.app.model.ElencoProdotti;
import com.myproject.app.view.ElencoProdottiView;

public class ElencoProdottiController {

	private ElencoProdottiView elencoProdottiView;
	private ElencoProdottiDao elencoProdottiDao;

	private ElencoProdottiController(ElencoProdottiView elencoProdottiView, ElencoProdottiDao elencoProdottiDao) {
		super();
		this.elencoProdottiView = elencoProdottiView;
		this.elencoProdottiDao = elencoProdottiDao;
	}

	public void allElencoProdotti() {
		elencoProdottiView.showAllElencoProdotti(elencoProdottiDao.findAll());

	}

	public void saveNewElencoProdotti(ElencoProdotti elencoProdottiDaSalvare) {
		ElencoProdotti elencoProdottiAlreadyExist = elencoProdottiDao.findById(elencoProdottiDaSalvare.getId());

		if (elencoProdottiAlreadyExist != null) {
			elencoProdottiView.showError("This list of products already exist", elencoProdottiAlreadyExist);
		} else {
			elencoProdottiDao.save(elencoProdottiDaSalvare);
			elencoProdottiView.showNewElencoProdotti(elencoProdottiDaSalvare);
		}
	}

}
