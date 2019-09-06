package com.myproject.app.dao;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import com.myproject.app.model.Prodotto;

public class ProdottoDaoTest extends JpaTest {

	private ProdottoDao prodottoDao;
	private Prodotto verdura;
	private Prodotto frutta;

	@Override
	protected void init() throws InitializationError {
		prodottoDao = new ProdottoDao();
	}

	@Test
	public void testsaveProdotto() {
		verdura = new Prodotto();
		verdura.setName("carota");
		prodottoDao.save(verdura);

		assertThat(entityManager.createQuery("select e from Prodotto e where e.name = :name", Prodotto.class)
				.setParameter("name", verdura.getName()).getResultList()).containsExactly(verdura);
	}
	
	@Test
	public void testProdottoFindById() {
		verdura = new Prodotto();
		frutta = new Prodotto();

		addProductToDatabase(verdura);
		addProductToDatabase(frutta);

		entityManager.getTransaction().commit();
		entityManager.clear();

		assertThat(prodottoDao.findById(frutta.getId())).isEqualTo(frutta);
	}
	
	@Test
	public void testProdottoFindByIdNotFound() {
		assertThat(prodottoDao.findById(Long.valueOf(2))).isNull();
	}
	
	@Test
	public void testFindAllProdottiWhenDatabaseIsNotEmpty() {
		frutta = new Prodotto();
		verdura = new Prodotto();

		addProductToDatabase(frutta);
		addProductToDatabase(verdura);

		entityManager.getTransaction().commit();
		entityManager.clear();

		assertThat(prodottoDao.findAll()).containsExactly(frutta, verdura);
	}
	
	@Test
	public void testFindAllProdottiWhenDatabaseIsEmpty() {
		assertThat(prodottoDao.findAll()).isEmpty();
	}

	private void addProductToDatabase(Prodotto prodottoDaPersistere) {
		entityManager = PersistenceManager.getEntityManager();
		entityManager.persist(prodottoDaPersistere);

	}

}
