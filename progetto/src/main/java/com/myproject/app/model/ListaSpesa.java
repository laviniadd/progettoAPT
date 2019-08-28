package com.myproject.app.model;

import java.util.Set;

import javax.persistence.*;

@Entity
public class ListaSpesa extends BaseEntity {
	private String name;
	
	private Set<ElencoProdotti> elencoProdotti;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@OneToMany(mappedBy = "listaSpesa")
	public Set<ElencoProdotti> getElencoProdotti() {
		return elencoProdotti;
	}

	public void setElencoProdotti(Set<ElencoProdotti> elencoProdotti) {
		this.elencoProdotti = elencoProdotti;
	}
}
