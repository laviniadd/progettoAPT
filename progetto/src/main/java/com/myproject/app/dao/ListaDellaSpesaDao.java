package com.myproject.app.dao;

import com.myproject.app.model.ListaSpesa;

public class ListaDellaSpesaDao extends BaseRepository<ListaSpesa> {

	public ListaDellaSpesaDao(TransactionTemplate transaction) {
		super(transaction, ListaSpesa.class);
	}

}