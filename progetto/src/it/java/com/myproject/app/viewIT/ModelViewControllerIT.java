package com.myproject.app.viewIT;

import static org.assertj.core.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MySQLContainer;

import com.myproject.app.controller.ListaSpesaController;
import com.myproject.app.controller.ProdottoController;
import com.myproject.app.dao.ListaDellaSpesaDao;
import com.myproject.app.dao.ProdottoDao;
import com.myproject.app.dao.TransactionTemplate;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;
import com.myproject.app.view.AppSwingView;

@RunWith(GUITestRunner.class)
public class ModelViewControllerIT extends AssertJSwingJUnitTestCase {

	private static EntityManagerFactory entityManagerFactory;
	private TransactionTemplate transaction;
	private ListaDellaSpesaDao listaDao;
	private ProdottoDao prodottoDao;
	private ProdottoController prodottoController;
	private ListaSpesaController listaController;
	private FrameFixture window;
	private AppSwingView appSwingView;

	@SuppressWarnings("rawtypes")
	@ClassRule
	public static MySQLContainer mysql = new MySQLContainer("mysql:8").withDatabaseName("dbprogetto");

	@BeforeClass
	public static void setUpClass() throws Exception {
		Map<String, String> configOverrides = new HashMap<String, String>();
		configOverrides.put("hibernate.connection.url", mysql.getJdbcUrl().toString());
		configOverrides.put("hibernate.connection.password", mysql.getPassword().toString());
		configOverrides.put("hibernate.connection.username", mysql.getUsername().toString());
		configOverrides.put("hibernate.hbm2ddl.auto", "update");

		entityManagerFactory = Persistence.createEntityManagerFactory("integration", configOverrides);
	}

	@Override
	protected void onSetUp() {
		transaction = new TransactionTemplate(entityManagerFactory);

		transaction.executeTransaction((em) -> {
			em.createNativeQuery("DELETE FROM Prodotto").executeUpdate();
			em.createNativeQuery("DELETE FROM ListaSpesa").executeUpdate();
			return null;
		});
		listaDao = new ListaDellaSpesaDao(transaction);
		prodottoDao = new ProdottoDao(transaction);
		window = new FrameFixture(robot(), GuiActionRunner.execute(() -> {
			appSwingView = new AppSwingView();
			listaController = new ListaSpesaController(appSwingView, listaDao);
			prodottoController = new ProdottoController(appSwingView, prodottoDao, listaDao);
			appSwingView.setViewController(listaController, prodottoController);
			return appSwingView;
		}));
		window.show(); // shows the frame to test
	}

	@AfterClass
	public static void tearDownClass() {
		entityManagerFactory.close();
	}

	@Test
	public void testAddList() {
		window.textBox("nomeListaTextBox").enterText("Lista della spesa");
		window.button(JButtonMatcher.withText("Crea Lista")).click();
		assertThat(listaDao.findByName("Lista della spesa").get(0).getName()).isEqualTo("Lista della spesa");
	}

	@Test
	public void testAddProduct() {
		ListaSpesa lista = new ListaSpesa("Lista della spesa");
		listaDao.save(lista);
		GuiActionRunner.execute(() -> listaController.allListeSpesa());
		window.list("elencoListe").selectItem(0);
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();
		window.textBox("prodottoTextBox").enterText("Kiwi");
		window.textBox("quantitaTextBox").setText("");
		window.textBox("quantitaTextBox").enterText("1");
		window.button(JButtonMatcher.withText("Aggiungi Prodotto")).click();

		assertThat(prodottoDao.findByName("Kiwi").get(0).getName()).isEqualTo("Kiwi");
		assertThat(prodottoDao.findByName("Kiwi").get(0).getQuantity()).isEqualTo(1);
	}

	@Test
	public void testDeleteList() {
		ListaSpesa listaDaCancellare = new ListaSpesa("Lista della spesa");
		listaDao.save(listaDaCancellare);
		GuiActionRunner.execute(() -> listaController.allListeSpesa());
		window.list("elencoListe").selectItem(0);
		window.button(JButtonMatcher.withText("Cancella Lista selezionata")).click();

		assertThat(listaDao.findById(listaDaCancellare.getId())).isNull();
	}

	@Test
	public void testDeleteProduct() {
		Prodotto prodottoDaCancellare = new Prodotto();
		prodottoDao.save(prodottoDaCancellare);

		GuiActionRunner.execute(() -> prodottoController.allProducts());
		window.list("elencoProdotti").selectItem(0);
		window.button(JButtonMatcher.withText("Cancella Prodotto Selezionato")).click();

		assertThat(prodottoDao.findById(prodottoDaCancellare.getId())).isNull();
	}

}
