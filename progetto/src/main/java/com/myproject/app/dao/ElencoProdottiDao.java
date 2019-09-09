package com.myproject.app.dao;

import com.myproject.app.model.ElencoProdotti;

public class ElencoProdottiDao extends BaseRepository<ElencoProdotti>{

	public ElencoProdottiDao(TransactionTemplate transaction) {
		super(transaction, ElencoProdotti.class);
	}

}
