package com.myproject.app.dao;

import com.myproject.app.model.Prodotto;

public class ProdottoDao extends BaseRepository<Prodotto>{

	public ProdottoDao(TransactionTemplate transaction) {
		super(transaction, Prodotto.class);
	}

}
