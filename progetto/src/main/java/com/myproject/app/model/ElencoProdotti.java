package com.myproject.app.model;

import javax.persistence.*;

@Entity
public class ElencoProdotti extends BaseEntity {

	public ElencoProdotti() {
	}

	public ElencoProdotti(ListaSpesa listaSpesa, Prodotto prodotto, int quantity) {
		this.prodotto = prodotto;
		this.listaSpesa = listaSpesa;
		this.quantity = quantity;
	}

	private Prodotto prodotto;
	private ListaSpesa listaSpesa;
	private int quantity;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@ManyToOne
	@JoinColumn(name = "listaspesa_id")
	public ListaSpesa getListaSpesa() {
		return listaSpesa;
	}

	public void setListaSpesa(ListaSpesa listaSpesa) {
		this.listaSpesa = listaSpesa;
	}

	@ManyToOne
	@JoinColumn(name = "prodotto_id")
	public Prodotto getProdotto() {
		return prodotto;
	}

	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}

}
