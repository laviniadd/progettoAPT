package com.myproject.app.dao;

import com.myproject.app.model.Prodotto;

public class ProdottoDao extends BaseRepository<Prodotto>{

	private TransactionTemplate transaction;

	public ProdottoDao(TransactionTemplate transaction) {
		super(transaction, Prodotto.class);
		this.transaction = transaction;
	}

	public void updateProduct(Prodotto prodottoDaModificare, String nuovoNomeProdotto, int nuovaQuantitaProdotto) {
		if (prodottoDaModificare == null) {
			throw new IllegalArgumentException();
		}
		
		transaction.executeTransaction(em -> {
			Prodotto prodottoInDB = em.find(Prodotto.class, prodottoDaModificare.getId());
			prodottoInDB.setName(nuovoNomeProdotto);
			prodottoInDB.setQuantity(nuovaQuantitaProdotto);
			em.persist(prodottoInDB);
			return null;
		});
	}

}
