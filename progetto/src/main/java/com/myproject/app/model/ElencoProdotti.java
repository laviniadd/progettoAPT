package com.myproject.app.model;

import java.util.Set;

import javax.persistence.*;

@Entity
public class ElencoProdotti extends BaseEntity {
	
	private Set<Prodotto> prodotti;
	private ListaSpesa listaSpesa;
	private int quantity;

	public ElencoProdotti() {
	}

	public ElencoProdotti(ListaSpesa listaSpesa, Set<Prodotto> prodotti, int quantity) {
		this.prodotti = prodotti;
		this.listaSpesa = listaSpesa;
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "listaspesa_id")
	public ListaSpesa getListaSpesa() {
		return listaSpesa;
	}

	public void setListaSpesa(ListaSpesa listaSpesa) {
		this.listaSpesa = listaSpesa;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Prodotto.class)
	@JoinColumn(name = "prodotto_id")
	public Set<Prodotto> getProdotti() {
		return prodotti;
	}

	public void setProdotti(Set<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}

	
	
}
