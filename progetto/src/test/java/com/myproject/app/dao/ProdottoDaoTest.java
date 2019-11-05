package com.myproject.app.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import com.myproject.app.model.ListaSpesa;
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
	public void testSave() {
		verdura = new Prodotto();
		prodottoDao.save(verdura);
		List<Prodotto> retrievedProduct = retrieveProductToDatabase();
		assertThat(retrievedProduct).containsExactly(verdura);
	}

	@Test
	public void testSaveNull() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			prodottoDao.save(null);
		});
	}

	@Test
	public void testFindById() {
		verdura = new Prodotto();
		frutta = new Prodotto();
		addProductToDatabase(verdura);
		addProductToDatabase(frutta);
		assertThat(prodottoDao.findById(frutta.getId())).isEqualTo(frutta);
	}

	@Test
	public void testFindByIdNull() {
		assertThat(prodottoDao.findById(null)).isNull();
	}

	@Test
	public void testProductNotAlreadySavedFindByIdNull() {
		Prodotto prodottoNotSaved = new Prodotto();
		assertThat(prodottoDao.findById(prodottoNotSaved.getId())).isNull();
	}

	@Test
	public void testProductFindByIdNotFound() {
		assertThat(prodottoDao.findById(Long.valueOf(1))).isNull();
	}

	public void testProductFindByName() {
		frutta = new Prodotto("mela", 1, null);
		addProductToDatabase(frutta);
		assertThat(prodottoDao.findByName(frutta.getName())).containsExactly(frutta);
	}

	@Test
	public void testProductFindByNameNull() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			prodottoDao.findByName(null);
		});
	}

	@Test
	public void testProductFindByNameEmpty() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			prodottoDao.findByName("");
		});
	}

	@Test
	public void testProductFindByNameEmptySpace() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			prodottoDao.findByName(" ");
		});
	}

	@Test
	public void testProductNotAlreadySavedFindByName() {
		verdura = new Prodotto("carota", 1, null);
		assertThat(prodottoDao.findByName(verdura.getName())).isEmpty();
	}

	@Test
	public void testFindByNameNotFound() {
		assertThat(prodottoDao.findByName("mela")).isEmpty();
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
	public void testFindAllProductsWhenDatabaseIsEmpty() {
		assertThat(prodottoDao.findAll()).isEmpty();
	}

	@Test
	public void testDelete() {
		frutta = new Prodotto();
		addProductToDatabase(frutta);

		prodottoDao.delete(frutta.getId());

		List<Prodotto> retrievedProduct = retrieveProductToDatabase();
		assertThat(retrievedProduct).isEmpty();
	}

	@Test
	public void testDeleteProductWithList() {
		ListaSpesa lista = new ListaSpesa("spesa");
		addListToDatabase(lista);		
		Prodotto prodotto = new Prodotto("mela", 2, lista);
		addProductToDatabase(prodotto);
		
		prodottoDao.delete(prodotto.getId());
		
		List<Prodotto> retrievedProducts = retrieveProductToDatabase();
		List<ListaSpesa> retrievedShoppingList = retrieveShoppingListToDatabase();
		assertThat(retrievedProducts).isEmpty();
		assertThat(retrievedShoppingList).containsExactly(lista);
	}
	
	@Test
	public void testDeleteNull() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			prodottoDao.delete(null);
		});
	}
	
	@Test
	public void testUpdateFromDatabase() {
		frutta = new Prodotto("mela", 3, null);
		addProductToDatabase(frutta);
		prodottoDao.updateProduct(frutta, "pera", 4);
		List<Prodotto> retrievedProduct = retrieveProductToDatabase();
		List<String> names = new ArrayList<String>();
		List<String> quantities = new ArrayList<String>();
		for (Prodotto prodotto : retrievedProduct) {
			names.add(prodotto.getName());
			quantities.add(String.valueOf(prodotto.getQuantity()));
		}
		assertThat(names).containsExactly("pera");
		assertThat(quantities).containsExactly("4");
		assertThat(retrievedProduct).containsExactly(frutta);
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
	public void testUpdateProductNull() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			prodottoDao.updateProduct(null, "pera", 1);
		});
	}

	@Test
	public void testUpdateProductIsNotPersisted() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			prodottoDao.updateProduct(new Prodotto(), "pera", 1);
		});
	}

	@Test
	public void testUpdateProductWithNullName() {
		frutta = new Prodotto("mela", 3, null);
		addProductToDatabase(frutta);
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			prodottoDao.updateProduct(frutta, null, 1);
		});
	}
	
	@Test
	public void testUpdateProductWithEmptyName() {
		frutta = new Prodotto("mela", 3, null);
		addProductToDatabase(frutta);
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			prodottoDao.updateProduct(frutta, "", 1);
		});
	}
	
	@Test
	public void testUpdateProductWithSpaceName() {
		frutta = new Prodotto("mela", 3, null);
		addProductToDatabase(frutta);
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			prodottoDao.updateProduct(frutta, " ", 1);
		});
	}

	@Test
	public void testUpdateProductWithNegativeQuantity() {
		frutta = new Prodotto("mela", 3, null);
		addProductToDatabase(frutta);

		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			prodottoDao.updateProduct(frutta, "pera", -1);
		});
	}

	@Test
	public void testUpdateProductWithZeroQuantity() {
		frutta = new Prodotto("mela", 3, null);
		addProductToDatabase(frutta);

		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			prodottoDao.updateProduct(frutta, "pera", 0);
		});
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

	@Test
	public void testFindAllProductsInAEmptyListWhenDatabaseIsEmpty() {
		ListaSpesa lista = new ListaSpesa();
		addListToDatabase(lista);
		assertThat(prodottoDao.findAllProductOfAList(lista)).isEmpty();
	}

	@Test
	public void testFindAllProductsInAEmptyListWhenListIsNull() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			prodottoDao.findAllProductOfAList(null);
		});
	}

	@Test
	public void testFindANotPersistedProduct() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			prodottoDao.findAllProductOfAList(new ListaSpesa());
		});
	}

	
	private void addProductToDatabase(Prodotto prodottoDaPersistere) {
		transaction.executeTransaction((em) -> {
			em.persist(prodottoDaPersistere);
			em.clear();
			return null;
		});
	}

	private void addListToDatabase(ListaSpesa listaDaSalvare) {
		transaction.executeTransaction((em) -> {
			em.persist(listaDaSalvare);
			em.clear();
			return null;
		});
	}

	private List<Prodotto> retrieveProductToDatabase() {
		return transaction.executeTransaction((em) -> {
			return em.createQuery("select e from Prodotto e ", Prodotto.class).getResultList();
		});
	}
	private List<ListaSpesa> retrieveShoppingListToDatabase() {
		return transaction.executeTransaction((em) -> {
			return em.createQuery("select e from ListaSpesa e", ListaSpesa.class).getResultList();
		});
	}


}
