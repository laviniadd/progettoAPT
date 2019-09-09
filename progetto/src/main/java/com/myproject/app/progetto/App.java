package com.myproject.app.progetto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.myproject.app.model.ElencoProdotti;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;

public class App {
	public static void main(String[] args) {
		 EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("production");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		ListaSpesa listaspesa = new ListaSpesa();
		listaspesa.setName("listaprova");
		Prodotto prodotto = new Prodotto();
		prodotto.setName("banana");
		ElencoProdotti productList = new ElencoProdotti();
		productList.setListaSpesa(listaspesa);
		productList.setProdotto(prodotto);
		productList.setQuantity(2);
		entityManager.getTransaction().begin();
		entityManager.persist(listaspesa);
		entityManager.persist(prodotto);
		entityManager.persist(productList);
		entityManager.getTransaction().commit();
		
		entityManager.close();
		entityManagerFactory.close();
		
	}
}