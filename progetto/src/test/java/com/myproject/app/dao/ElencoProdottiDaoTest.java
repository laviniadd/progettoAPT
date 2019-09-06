package com.myproject.app.dao;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import com.myproject.app.model.ElencoProdotti;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;

public class ElencoProdottiDaoTest extends JpaTest {

	private ElencoProdottiDao elencoProdottiDao;
	private ElencoProdotti elencoProdotti;
	private ElencoProdotti elencoProdottiSpesa;
	
	@Override
	protected void init() throws InitializationError {
		elencoProdottiDao = new ElencoProdottiDao();
	}
	
	@Test
	public void testsaveElencoProdotti() {
		elencoProdotti = new ElencoProdotti();
		elencoProdottiDao.save(elencoProdotti);

		assertThat(entityManager.createQuery("select e from ElencoProdotti e where e.id = :id", ElencoProdotti.class)
				.setParameter("id", elencoProdotti.getId()).getResultList()).containsExactly(elencoProdotti);
	}
	
	@Test
	public void testElencoProdottiFindById() {
		elencoProdotti = new ElencoProdotti();
		elencoProdottiSpesa = new ElencoProdotti();

		addListOfProductsToDatabase(elencoProdotti);
		addListOfProductsToDatabase(elencoProdottiSpesa);

		entityManager.getTransaction().commit();
		entityManager.clear();

		assertThat(elencoProdottiDao.findById(elencoProdottiSpesa.getId())).isEqualTo(elencoProdottiSpesa);
	}
	
	@Test
	public void testElencoProdottiFindByIdNotFound() {
		assertThat(elencoProdottiDao.findById(Long.valueOf(2))).isNull();
	}
	
	@Test
	public void testFindAllElencoProdottiWhenDatabaseIsNotEmpty() {
		elencoProdotti = new ElencoProdotti();
		elencoProdottiSpesa = new ElencoProdotti();

		addListOfProductsToDatabase(elencoProdotti);
		addListOfProductsToDatabase(elencoProdottiSpesa);

		entityManager.getTransaction().commit();
		entityManager.clear();

		assertThat(elencoProdottiDao.findAll()).containsExactly(elencoProdotti, elencoProdottiSpesa);
	}
	
	@Test
	public void testFindAllElencoProdottiWhenDatabaseIsEmpty() {
		assertThat(elencoProdottiDao.findAll()).isEmpty();
	}
	
	private void addListOfProductsToDatabase(ElencoProdotti elencoProdottoDaPersistere) {
		entityManager = PersistenceManager.getEntityManager();
		entityManager.persist(elencoProdottoDaPersistere);

	}
	
}
