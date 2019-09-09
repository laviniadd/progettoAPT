package com.myproject.app.model;

import javax.persistence.*;

@Entity
public class Prodotto extends BaseEntity {
	private String name;
		
	public Prodotto() {
	}

	public Prodotto(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}