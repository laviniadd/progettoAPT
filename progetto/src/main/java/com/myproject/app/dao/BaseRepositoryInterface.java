package com.myproject.app.dao;

import javax.persistence.EntityManager;

public interface BaseRepositoryInterface<T> {
	T executeWithOpenedTransaction(EntityManager em);
}
