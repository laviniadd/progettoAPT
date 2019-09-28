package com.myproject.app.model;

import javax.persistence.*;

@Entity
public class ListaSpesa extends BaseEntity {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
