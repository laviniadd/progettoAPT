package com.myproject.app.model;

import java.util.HashSet;
import javax.persistence.*;

@Entity
public class ElencoProdotti extends BaseEntity {
	
	private HashSet<Prodotto> prodottiAndQuantity;
	private ListaSpesa listaSpesa;

	public ElencoProdotti() {
	}

	public ElencoProdotti(ListaSpesa listaSpesa, HashSet<Prodotto> prodottiAndQuantity) {
		this.prodottiAndQuantity = prodottiAndQuantity;
		this.listaSpesa = listaSpesa;
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
	@JoinColumn(name = "elenco_prodotto_id")
	public HashSet<Prodotto> getProdottiAndQuantity() {
		return prodottiAndQuantity;
	}

	public void setProdottiAndQuantity(HashSet<Prodotto> prodottiAndQuantity) {
		this.prodottiAndQuantity = prodottiAndQuantity;
	}
	
}
