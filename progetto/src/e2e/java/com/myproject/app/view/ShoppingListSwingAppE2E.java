package com.myproject.app.view;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.*;

import java.util.HashMap;
import java.util.Map;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MySQLContainer;

import static org.assertj.core.api.Assertions.*;

import com.myproject.app.dao.TransactionTemplate;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;

@RunWith(GUITestRunner.class)
public class ShoppingListSwingAppE2E extends AssertJSwingJUnitTestCase {

	private static EntityManagerFactory entityManagerFactory;
	private FrameFixture window;
	private TransactionTemplate transaction;
	private ListaSpesa listasenzaProdotti;
	private Prodotto prodotto1;
	private Prodotto prodotto2;

	@SuppressWarnings("rawtypes")
	@ClassRule
	public static MySQLContainer mysql = new MySQLContainer("mysql:8").withDatabaseName("dbprogetto");

	@BeforeClass
	public static void setUpClass() throws Exception {
		//TODO DA' ERRORE PERCHè CCERCA SUBITO DI AVERE I PARAMETRI ANCHE SE STA ANCORA CREANDO IL CONTAINER???
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
		listasenzaProdotti = new ListaSpesa("Lista coop");
		ListaSpesa listaConProdotti = new ListaSpesa("Lista con prodotti");
		addEntityToDatabase(listasenzaProdotti);
		addEntityToDatabase(listaConProdotti);
		prodotto1 = new Prodotto("Mela", 2, listaConProdotti);
		prodotto2 = new Prodotto("Pera", 1, listaConProdotti);
		addEntityToDatabase(prodotto1);
		addEntityToDatabase(prodotto2);

		application("com.myproject.app.main.ShoppingListSwingApp")
				.withArgs("--mysql-host=" + mysql.getJdbcUrl().toString(), "--db-name=" + "dbprogetto",
						"--db-username=" + mysql.getUsername().toString(),
						"--db-password=" + mysql.getPassword().toString())
				.start();

		window = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
			@Override
			protected boolean isMatching(JFrame frame) {
				return "ShoppingListApp".equals(frame.getTitle()) && frame.isShowing();
			}
		}).using(robot());
	}

	@After
	public void closeEm() {
		transaction.executeTransaction((em) -> {
			em.close();
			return null;
		});
	}

	@AfterClass
	public static void tearDownClass() {
		entityManagerFactory.close();
	}

	@Test

	@GUITest
	public void testOnStartAllDatabaseElementsAreShown() {
		assertThat(window.list("elencoListe").contents()).containsExactly("Lista coop", "Lista con prodotti");
	}

	@Test

	@GUITest
	public void testOnStartAllProductsOfAListAreShown() {
		window.list("elencoListe").selectItem("Lista con prodotti");
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();

		assertThat(window.list("elencoProdotti").contents()).containsExactly("2 Mela", "1 Pera");
	}

	@Test

	@GUITest
	public void testAddListButtonSuccess() {
		window.textBox("nomeListaTextBox").enterText("Lista");
		window.button(JButtonMatcher.withText("Crea Lista")).click();
		assertThat(window.list("elencoListe").contents()).anySatisfy(e -> assertThat(e).contains("Lista"));
	}

	@Test

	@GUITest
	public void testDeleteListButtonSuccess() {
		window.list("elencoListe").selectItem("Lista coop");
		window.button(JButtonMatcher.withText("Cancella Lista selezionata")).click();
		assertThat(window.list("elencoListe").contents()).noneMatch(e -> e.contains("Lista coop"));
	}

	@Test

	@GUITest
	public void testDeleteListButtonError() {
		window.list("elencoListe").selectItem("Lista coop");
		removeTestListFromDatabase(listasenzaProdotti.getId(), listasenzaProdotti.getClass());
		window.button(JButtonMatcher.withText("Cancella Lista selezionata")).click();
		assertThat(window.label("errorMessageLabelList").text())
				.contains("This shopping list does not exist: " + listasenzaProdotti.toString());
	}

	@Test

	@GUITest
	public void testAddProductButtonSuccess() {
		window.list("elencoListe").selectItem("Lista coop");
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();
		window.textBox("prodottoTextBox").enterText("Spinaci");
		window.textBox("quantitaTextBox").setText("");
		window.textBox("quantitaTextBox").enterText("3");
		window.button(JButtonMatcher.withText("Aggiungi Prodotto")).click();
		assertThat(window.list("elencoProdotti").contents()).anySatisfy(e -> assertThat(e).contains("3 Spinaci"));
	}

	@Test

	@GUITest
	public void testAddProductButtonError() {
		window.list("elencoListe").selectItem("Lista coop");
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();
		window.textBox("prodottoTextBox").enterText("");
		window.textBox("quantitaTextBox").setText("");
		window.textBox("quantitaTextBox").enterText("2");
		window.button(JButtonMatcher.withText("Aggiungi Prodotto")).click();
		assertThat(window.label("errorMessageProductModifiedLabel").text())
				.contains("This product has no valid name or quantity values");
	}

	@Test

	@GUITest
	public void testDeleteProductButtonSuccess() {
		window.list("elencoListe").selectItem("Lista con prodotti");
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();
		window.list("elencoProdotti").selectItem("2 Mela");
		window.button(JButtonMatcher.withText("Cancella Prodotto Selezionato")).click();
		assertThat(window.list("elencoProdotti").contents()).noneMatch(e -> e.contains("2 Mela"));
	}

	@Test

	@GUITest
	public void testDeleteProductButtonError() {
		window.list("elencoListe").selectItem("Lista con prodotti");
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();
		window.list("elencoProdotti").selectItem("2 Mela");
		removeTestListFromDatabase(prodotto1.getId(), prodotto1.getClass());
		window.button(JButtonMatcher.withText("Cancella Prodotto Selezionato")).click();
		assertThat(window.label("errorMessageProductLabel").text())
				.contains("This product does not exist: " + "2 Mela");
	}

	@Test

	@GUITest
	public void testSaveModifiedProductButtonSuccess() {
		window.list("elencoListe").selectItem("Lista con prodotti");
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();
		window.list("elencoProdotti").selectItem("2 Mela");
		window.button(JButtonMatcher.withText("Modifica Prodotto Selezionato")).click();
		window.textBox("prodottoTextBox").setText("");
		window.textBox("prodottoTextBox").enterText("Pistacchio");
		window.textBox("quantitaTextBox").setText("");
		window.textBox("quantitaTextBox").enterText("10");
		window.button(JButtonMatcher.withText("Salva Prodotto Modificato")).click();

		assertThat(window.list("elencoProdotti").contents()).anySatisfy(e -> e.contains("10 Pistacchio"));
	}

	@Test

	@GUITest
	public void testSaveModifiedProductButtonError() {
		window.list("elencoListe").selectItem("Lista con prodotti");
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();
		window.list("elencoProdotti").selectItem("2 Mela");
		removeTestListFromDatabase(prodotto1.getId(), prodotto1.getClass());
		window.button(JButtonMatcher.withText("Modifica Prodotto Selezionato")).click();
		window.button(JButtonMatcher.withText("Salva Prodotto Modificato")).click();
		assertThat(window.label("errorMessageProductModifiedLabel").text())
				.contains("This product does not exist: " + "2 Mela");
	}

	private <T> void addEntityToDatabase(T entityDaPersistere) {
		transaction.executeTransaction((em) -> {
			em.persist(entityDaPersistere);
			em.clear();
			return null;
		});
	}

	private <T> void removeTestListFromDatabase(Long idEntityDaCancellare, Class<T> classType) {
		transaction.executeTransaction((em) -> {
			T entityRecuperataDaCancellare = em.find(classType, idEntityDaCancellare);
			em.remove(entityRecuperataDaCancellare);
			return null;
		});
	}

}
