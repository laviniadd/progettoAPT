package com.myproject.app.dao;

//import java.util.List;

//import javax.persistence.*;

import com.myproject.app.model.ListaSpesa;

public class ListaDellaSpesaDao extends BaseDao<ListaSpesa> {

	//private EntityManager entityManager;

	public ListaDellaSpesaDao() {
		super(ListaSpesa.class);
//		this.entityManager = PersistenceManager.getEntityManager();
	}


}