package com.myproject.app.model;

import java.util.HashMap;
import javax.persistence.*;

@Entity
public class ElencoProdotti extends BaseEntity {
	
	private HashMap<Prodotto, Integer> prodottiAndQuantity;
	private ListaSpesa listaSpesa;

	public ElencoProdotti() {
	}

	public ElencoProdotti(ListaSpesa listaSpesa, HashMap<Prodotto, Integer> prodottiAndQuantity) {
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
	public HashMap<Prodotto, Integer> getProdottiAndQuantity() {
		return prodottiAndQuantity;
	}

	public void setProdottiAndQuantity(HashMap<Prodotto, Integer> prodottiAndQuantity) {
		this.prodottiAndQuantity = prodottiAndQuantity;
	}
	
	
}
