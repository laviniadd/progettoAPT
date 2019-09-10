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
	
	@OneToMany(mappedBy = "listaSpesa")
	public Set<ElencoProdotti> getElencoProdotti() {
		return elencoProdotti;
	}

	public void setElencoProdotti(Set<ElencoProdotti> elencoProdotti) {
		this.elencoProdotti = elencoProdotti;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((elencoProdotti == null) ? 0 : elencoProdotti.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ListaSpesa other = (ListaSpesa) obj;
		if (elencoProdotti == null) {
			if (other.elencoProdotti != null)
				return false;
		} else if (!elencoProdotti.equals(other.elencoProdotti))
					return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
