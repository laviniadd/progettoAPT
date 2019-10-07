package com.myproject.app.dao;

import java.util.List;

import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;

public class BaseRepository<T> {

	private TransactionTemplate transaction;
	private Class<T> classType;

	public BaseRepository(TransactionTemplate transaction, Class<T> classTypePassed) {
		this.transaction = transaction;
		this.classType = classTypePassed;
	}

	public void save(T entityDaSalvare) {
		if (entityDaSalvare == null) {
			throw new IllegalArgumentException();
		}
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

	public List<T> findByName(String name) {
		if (name == null || name == "" || name == " ") {
			throw new IllegalArgumentException();
		}
		String classTypeString = classType.getCanonicalName();
		return transaction.executeTransaction(em ->
			em.createQuery("select e from " + classTypeString + " e where e.name = :name", classType)
					.setParameter("name", name).getResultList());
	}

	public List<T> findAll() {
		if(classType.equals(ListaSpesa.class)) {
			return transaction.executeTransaction(
					em -> em.createQuery("SELECT t FROM ListaSpesa t", classType).getResultList());
		}
		if(classType.equals(Prodotto.class)) {
			return transaction.executeTransaction(
					em -> em.createQuery("SELECT t FROM Prodotto t", classType).getResultList());
		}
		return null;
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
