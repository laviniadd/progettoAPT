package com.myproject.app.dao;

import javax.persistence.*;

import com.myproject.app.model.ListaSpesa;

public class ListaDellaSpesaDao extends BaseDao<ListaSpesa> {

	private EntityManager entityManager;

	public ListaDellaSpesaDao() {
		super(ListaSpesa.class); //passa il tipo della classe al costruttore della supe class, ovvero basedao
		this.entityManager = PersistenceManager.getEntityManager();
	}

	/*
	 * public ListaSpesa findById(Long id) { return
	 * entityManager.find(ListaSpesa.class, id);
	 * 
	 * }
	 */
	/*
	 * public void save(ListaSpesa lista) { entityManager.persist(lista); }
	 */

}