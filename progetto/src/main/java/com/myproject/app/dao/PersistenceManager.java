package com.myproject.app.dao;

import javax.persistence.*;

public class PersistenceManager {
	
	private PersistenceManager() {
	}

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	
	public static void startEntityManagerFactory() {
	entityManagerFactory = Persistence.createEntityManagerFactory("test");
	//	entityManagerFactory = Persistence.createEntityManagerFactory("production");
	}

	public static EntityManager createEntityManager() {
		 return entityManager = entityManagerFactory.createEntityManager();
	}
	
	public static EntityManager getEntityManager() {
		if(entityManager != null) {
			 return entityManager;
		}
		return null;
	}

	public static void closeEntityManagerFactory() {
		entityManagerFactory.close();
	}
}
