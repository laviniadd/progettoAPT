package com.myproject.app.dao;

import javax.persistence.*;

import com.myproject.app.model.ListaSpesa;

public class ListaDellaSpesaDao {

	EntityManager entityManager;

	public ListaDellaSpesaDao() {

		this.entityManager = PersistenceManager.getEntityManager();
	}

	public void save(ListaSpesa lista) {
		entityManager.persist(lista);
	}

}
