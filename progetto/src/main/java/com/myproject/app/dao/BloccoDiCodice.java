package com.myproject.app.dao;

import javax.persistence.EntityManager;

public interface BloccoDiCodice<T> {
	T executeWithOpenedTransaction(EntityManager em);

}
