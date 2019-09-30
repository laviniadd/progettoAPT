package com.myproject.app.dao;

import java.util.List;

import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;

public class ProdottoDao extends BaseRepository<Prodotto> {

	private TransactionTemplate transaction;

	public ProdottoDao(TransactionTemplate transaction) {
		super(transaction, Prodotto.class);
		this.transaction = transaction;
	}

	public void updateProduct(Prodotto prodottoDaModificare, String nuovoNomeProdotto, int nuovaQuantitaProdotto) {
		if (prodottoDaModificare == null) {
			throw new IllegalArgumentException();
		} else if (prodottoDaModificare.getId() == null) {
			throw new IllegalArgumentException();
		}
		transaction.executeTransaction(em -> {
			Prodotto prodottoInDB = em.find(Prodotto.class, prodottoDaModificare.getId());
			prodottoInDB.setName(nuovoNomeProdotto);
			prodottoInDB.setQuantity(nuovaQuantitaProdotto);
			em.merge(prodottoInDB);
			return null;
		});

	}

	public List<Prodotto> findAllProductOfAList(ListaSpesa lista) {
		if (lista.getId() == null) {
			throw new IllegalArgumentException();
		}
		return transaction.executeTransaction((em) -> {
			return em.createQuery("select e from Prodotto e where e.listaSpesa.id = :listaSpesa", Prodotto.class)
					.setParameter("listaSpesa", lista.getId()).getResultList();
		});
	}
}
