package com.myproject.app.dao;

import java.util.List;

public class BaseRepository<T>{
		
	private TransactionTemplate transaction;
	private Class<T> classType;

	public BaseRepository(TransactionTemplate transaction, Class<T> classTypePassed) {
		this.transaction = transaction;
		this.classType = classTypePassed;
	}

	public void save(T entityDaSalvare) {
		transaction.executeTransaction(em -> {
			em.persist(entityDaSalvare);
			return null;
		});
	}

	public T findById(Long id) {
		if (id == null) {
			return null;
		}
		return transaction.executeTransaction(em -> em.find(classType, id));
	}

	public List<T> findAll() {
		String classTypeString = classType.getCanonicalName();
		return transaction.executeTransaction(
				em -> em.createQuery("SELECT t FROM " + classTypeString + " t", classType).getResultList());
	}

	public void delete(Long idEntityDaCancellare) {
		if (idEntityDaCancellare == null) {
			throw new IllegalArgumentException();
		}
		transaction.executeTransaction(em -> {
			T entityRecuperataDaCancellare = em.find(classType, idEntityDaCancellare);
			em.remove(entityRecuperataDaCancellare);
			return null;

		});
	}
}
