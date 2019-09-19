package com.myproject.app.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import com.myproject.app.model.Prodotto;

public class ProdottoDaoTest extends JpaTest {
	private TransactionTemplate transaction;

	private ProdottoDao prodottoDao;
	private Prodotto verdura;
	private Prodotto frutta;

	@Override
	protected void init(TransactionTemplate transaction) throws InitializationError {
		prodottoDao = new ProdottoDao(transaction);
		this.transaction = transaction;
	}

	@Test
	public void testsaveProdotto() {
		verdura = new Prodotto();

		prodottoDao.save(verdura);

		List<Prodotto> retrievedProduct = retrieveProductToDatabase(verdura);
		
		assertThat(retrievedProduct).containsExactly(verdura);
	}

	@Test
	public void testProdottoFindById() {
		verdura = new Prodotto();
		frutta = new Prodotto();

		addProductToDatabase(verdura);
		addProductToDatabase(frutta);

		assertThat(prodottoDao.findById(frutta.getId())).isEqualTo(frutta);
	}
	
	@Test
	public void testProdottoFindByIdNull() {
		assertThat(prodottoDao.findById(null)).isEqualTo(null);
	}

	@Test
	public void testProdottoFindByIdNotFound() {
		assertThat(prodottoDao.findById(Long.valueOf(1))).isNull();
	}

	@Test
	public void testFindAllProdottiWhenDatabaseIsNotEmpty() {
		frutta = new Prodotto();
		verdura = new Prodotto();

		addProductToDatabase(frutta);
		addProductToDatabase(verdura);

		assertThat(prodottoDao.findAll()).containsExactly(frutta, verdura);
	}

	@Test
	public void testFindAllProdottiWhenDatabaseIsEmpty() {
		assertThat(prodottoDao.findAll()).isEmpty();
	}

	@Test
	public void testDeleteProdotto() {
		frutta = new Prodotto();
		addProductToDatabase(frutta);
		
		prodottoDao.delete(frutta.getId());
		
		List<Prodotto> retrievedProduct = retrieveProductToDatabase(frutta);
		assertThat(retrievedProduct).isEmpty();
	}
	
	@Test
	public void testDeleteProdottoNull() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			prodottoDao.delete(null);
		});
	}

	private void addProductToDatabase(Prodotto prodottoDaPersistere) {
		transaction.executeTransaction((em) -> {
			em.persist(prodottoDaPersistere);
			em.clear();
			return null;
		});
	}
	
	private List<Prodotto> retrieveProductToDatabase(Prodotto prodottoDaRecuperare) {
		return transaction.executeTransaction((em) -> {
			return em.createQuery("select e from Prodotto e where e.id = :id", Prodotto.class)
					.setParameter("id", prodottoDaRecuperare.getId()).getResultList();
		});
	}

}
