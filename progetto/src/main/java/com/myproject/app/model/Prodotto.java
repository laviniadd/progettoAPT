package com.myproject.app.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Prodotto extends BaseEntity {

	private String name;
	private int quantity;
	private ListaSpesa listaSpesa;

	public Prodotto(String name, int quantity, ListaSpesa listaSpesa) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.listaSpesa = listaSpesa;
	}

	public Prodotto() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "listaSpesa_id")
	public ListaSpesa getListaSpesa() {
		return listaSpesa;
	}

	public void setListaSpesa(ListaSpesa listaSpesa) {
		this.listaSpesa = listaSpesa;
	}

	@Override
	public String toString() {
		return quantity + " " + name;
	}
}
