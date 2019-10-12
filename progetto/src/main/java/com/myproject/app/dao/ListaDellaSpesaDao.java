package com.myproject.app.dao;

import com.google.inject.Inject;
import com.myproject.app.model.ListaSpesa;

public class ListaDellaSpesaDao extends BaseRepository<ListaSpesa> {
	//@Inject
	public ListaDellaSpesaDao(TransactionTemplate transaction) {
		super(transaction, ListaSpesa.class);
	}

}