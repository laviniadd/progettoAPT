package com.myproject.app.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import com.myproject.app.model.ElencoProdotti;

public class ElencoProdottiDaoTest extends JpaTest {
	private TransactionTemplate transaction;
	private ElencoProdottiDao elencoProdottiDao;
	private ElencoProdotti elencoProdotti;
	private ElencoProdotti elencoProdottiSpesa;

	@Override
	protected void init(TransactionTemplate transaction) throws InitializationError {
		elencoProdottiDao = new ElencoProdottiDao(transaction);
		this.transaction = transaction;
	}

	@Test
	public void testsaveElencoProdotti() {
		elencoProdotti = new ElencoProdotti();
		elencoProdottiDao.save(elencoProdotti);

		List<ElencoProdotti> retrievedElencoProdotti = transaction.executeTransaction((em) -> {
			return em.createQuery("select e from ElencoProdotti e where e.id = :id", ElencoProdotti.class)
					.setParameter("id", elencoProdotti.getId()).getResultList();
		});

		assertThat(retrievedElencoProdotti).containsExactly(elencoProdotti);
	}

	@Test
	public void testElencoProdottiFindById() {
		elencoProdotti = new ElencoProdotti();
		elencoProdottiSpesa = new ElencoProdotti();

		addListOfProductsToDatabase(elencoProdotti);
		addListOfProductsToDatabase(elencoProdottiSpesa);

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

		assertThat(elencoProdottiDao.findAll()).containsExactly(elencoProdotti, elencoProdottiSpesa);
	}

	@Test
	public void testFindAllElencoProdottiWhenDatabaseIsEmpty() {
		assertThat(elencoProdottiDao.findAll()).isEmpty();
	}

	private void addListOfProductsToDatabase(ElencoProdotti elencoProdottoDaPersistere) {
		transaction.executeTransaction((em) -> {
			em.persist(elencoProdottoDaPersistere);
			em.clear();
			return null;
		});

	}

}
