package com.myproject.app.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("integration");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		ListaSpesa listaspesa = new ListaSpesa();
		listaspesa.setName("listaprova3");
		Prodotto prodotto = new Prodotto();
		prodotto.setName("banana3");
		prodotto.setListaSpesa(listaspesa);
		prodotto.setQuantity(2);
		
		entityManager.getTransaction().begin();
		entityManager.persist(listaspesa);
		entityManager.persist(prodotto);
		
		entityManager.getTransaction().commit();

		entityManager.close();
		entityManagerFactory.close();

	}
}
