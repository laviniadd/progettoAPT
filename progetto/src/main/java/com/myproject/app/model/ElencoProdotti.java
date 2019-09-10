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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((listaSpesa == null) ? 0 : listaSpesa.hashCode());
		result = prime * result + ((prodotto == null) ? 0 : prodotto.hashCode());
		result = prime * result + quantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElencoProdotti other = (ElencoProdotti) obj;
		if (listaSpesa == null) {
			if (other.listaSpesa != null)
				return false;
		} else if (!listaSpesa.equals(other.listaSpesa))
					return false;
		if (prodotto == null) {
			if (other.prodotto != null)
				return false;
		} else if (!prodotto.equals(other.prodotto))
					return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

}
