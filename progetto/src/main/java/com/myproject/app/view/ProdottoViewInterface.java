package com.myproject.app.view;

import java.util.List;

import com.myproject.app.model.Prodotto;

public interface ProdottoViewInterface {

	void showAllProducts(List<Prodotto> prodottiDaMostrare);

	void showError(String messaggioErrore, Prodotto prodotto);

	void showNewProduct(Prodotto prodotto);

	void showRemovedProduct(Prodotto prodottoDaCancellare);

}
