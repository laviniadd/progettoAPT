package com.myproject.app.daoIT;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import com.myproject.app.dao.ProdottoDao;
import com.myproject.app.dao.TransactionTemplate;
import com.myproject.app.model.Prodotto;

public class ProdottoDaoIT extends ITDao{
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
		verdura.setName("carota");
		prodottoDao.save(verdura);

		List<Prodotto> retrievedProduct = transaction.executeTransaction((em) -> {
			return em.createQuery("select e from Prodotto e where e.name = :name", Prodotto.class)
					.setParameter("name", verdura.getName()).getResultList();
		});
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
	public void testFindAllProdottiWhenDatabaseIsNotEmpty() {
		frutta = new Prodotto();
		verdura = new Prodotto();

		addProductToDatabase(frutta);
		addProductToDatabase(verdura);

		assertThat(prodottoDao.findAll()).containsExactly(frutta, verdura);
	}
	
	private void addProductToDatabase(Prodotto prodottoDaPersistere) {
		transaction.executeTransaction((em) -> {
			em.persist(prodottoDaPersistere);
			em.clear();
			return null;
		});
	}

}
