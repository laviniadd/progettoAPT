package com.myproject.app.model;

import java.util.Set;

import javax.persistence.*;

@Entity
public class ListaSpesa extends BaseEntity {
	
	private String name;
	
	private Set<ElencoProdotti> elencoProdotti;
	
	public ListaSpesa() {
	}
	
	public ListaSpesa(String name, Set<ElencoProdotti> elencoProdotti) {
		super();
		this.name = name;
		this.elencoProdotti = elencoProdotti;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "listaSpesa")
	public Set<ElencoProdotti> getElencoProdotti() {
		return elencoProdotti;
	}

	public void setElencoProdotti(Set<ElencoProdotti> elencoProdotti) {
		this.elencoProdotti = elencoProdotti;
	}

}
