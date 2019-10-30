package com.myproject.app.daoIT;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import com.myproject.app.dao.ProdottoDao;
import com.myproject.app.dao.TransactionTemplate;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;

public class ProdottoDaoIT extends ITDao {
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
	public void testSave() {
		verdura = new Prodotto();
		prodottoDao.save(verdura);
		List<Prodotto> retrievedProduct = retrieveProductToDatabase(verdura);
		assertThat(retrievedProduct).containsExactly(verdura);
	}

	@Test
	public void testFindById() {
		verdura = new Prodotto();
		frutta = new Prodotto();
		addProductToDatabase(verdura);
		addProductToDatabase(frutta);
		assertThat(prodottoDao.findById(frutta.getId())).isEqualTo(frutta);
	}


	public void testProductFindByName() {
		frutta = new Prodotto("mela", 1, null);
		addProductToDatabase(frutta);
		assertThat(prodottoDao.findByName(frutta.getName())).containsExactly(frutta);
	}
	
	@Test
	public void testFindAllProductsWhenDatabaseIsNotEmpty() {
		frutta = new Prodotto();
		verdura = new Prodotto();
		addProductToDatabase(frutta);
		addProductToDatabase(verdura);
		assertThat(prodottoDao.findAll()).containsExactly(frutta, verdura);
	}

	@Test
	public void testDelete() {
		frutta = new Prodotto();
		addProductToDatabase(frutta);
		prodottoDao.delete(frutta.getId());
		List<Prodotto> retrievedProduct = retrieveProductToDatabase(frutta);
		assertThat(retrievedProduct).isEmpty();
	}

	@Test
	public void testUpdateFromReturnValue() {
		frutta = new Prodotto("mela", 3, null);
		addProductToDatabase(frutta);
		Prodotto modificato = prodottoDao.updateProduct(frutta, "pera", 4);
		assertThat(modificato.getName()).isEqualTo("pera");
		assertThat(modificato.getQuantity()).isEqualTo(4);
	}


	@Test
	public void testFindAllProductsInAListWithProductsWhenDatabaseIsNotEmpty() {
		ListaSpesa lista = new ListaSpesa();
		addListToDatabase(lista);
		frutta = new Prodotto("Mela", 1, lista);
		verdura = new Prodotto("Pera", 2, lista);
		addProductToDatabase(frutta);
		addProductToDatabase(verdura);
		assertThat(prodottoDao.findAllProductOfAList(lista)).containsExactly(frutta, verdura);
	}

	private void addListToDatabase(ListaSpesa listaDaSalvare) {
		transaction.executeTransaction((em) -> {
			em.persist(listaDaSalvare);
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

	private void addProductToDatabase(Prodotto prodottoDaPersistere) {
		transaction.executeTransaction((em) -> {
			em.persist(prodottoDaPersistere);
			em.clear();
			return null;
		});
	}

}
