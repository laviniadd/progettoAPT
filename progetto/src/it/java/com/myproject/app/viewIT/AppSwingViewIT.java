package com.myproject.app.viewIT;

import javax.persistence.EntityManager;
import static org.assertj.swing.timing.Pause.pause;
import static org.assertj.swing.timing.Timeout.timeout;
import org.assertj.swing.timing.Condition;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.*;

import com.myproject.app.controller.ListaSpesaController;
import com.myproject.app.controller.ProdottoController;
import com.myproject.app.dao.ListaDellaSpesaDao;
import com.myproject.app.dao.ProdottoDao;
import com.myproject.app.dao.TransactionTemplate;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;
import com.myproject.app.view.AppSwingView;

@RunWith(GUITestRunner.class)
public class AppSwingViewIT extends AssertJSwingJUnitTestCase {
	private static final long TIMEOUT = 5000;
	protected EntityManager entityManager;
	private static EntityManagerFactory entityManagerFactory;
	private TransactionTemplate transaction;
	private FrameFixture window;
	private AppSwingView appSwingView;
	private ListaSpesaController listaController;
	private ProdottoController prodottoController;
	private ListaDellaSpesaDao listaDao;
	private ProdottoDao prodottoDao;

	@BeforeClass
	public static void setUpClass() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("test");
	}

	@AfterClass
	public static void tearDownClass() {
		entityManagerFactory.close();
	}

	@Override
	protected void onSetUp() {
		transaction = new TransactionTemplate(entityManagerFactory);

		transaction.executeTransaction((em) -> {
			em.createNativeQuery("TRUNCATE SCHEMA public AND COMMIT").executeUpdate();
			return null;
		});

		listaDao = new ListaDellaSpesaDao(transaction);
		prodottoDao = new ProdottoDao(transaction);
		GuiActionRunner.execute(() -> {
			appSwingView = new AppSwingView();
			listaController = new ListaSpesaController(appSwingView, listaDao);
			prodottoController = new ProdottoController(appSwingView, prodottoDao);

			appSwingView.setViewController(listaController, prodottoController);
			return appSwingView;
		});
		window = new FrameFixture(robot(), appSwingView);
		window.show(); // shows the frame to test
	}

	@Test

	@GUITest
	public void testAllLists() {
		ListaSpesa listaSpesaPam = new ListaSpesa("lista pam");
		ListaSpesa listaSpesaCoop = new ListaSpesa("lista coop");

		listaDao.save(listaSpesaPam);
		listaDao.save(listaSpesaCoop);
		GuiActionRunner.execute(() -> listaController.allListeSpesa());

		assertThat(window.list("elencoListe").contents()).containsExactly(listaSpesaPam.toString(),
				listaSpesaCoop.toString());
	}

	@Test

	@GUITest
	public void testCreateButtonSuccess() {
		window.textBox("nomeListaTextBox").enterText("Lista spesa");
		window.button(JButtonMatcher.withText("Crea Lista")).click();
		assertThat(window.list("elencoListe").contents()).containsExactly("Lista spesa");
	}

	@Test

	@GUITest
	public void testDeleteSelectedListButtonSuccess() {
		GuiActionRunner.execute(() -> listaController.saveNewLista(new ListaSpesa("Lista della spesa")));
		window.list("elencoListe").selectItem(0);
		window.button(JButtonMatcher.withText("Cancella Lista selezionata")).click();
		assertThat(window.list("elencoListe").contents()).isEmpty();
		assertThat(window.label("errorMessageLabelList").requireText(" "));
	}

	@Test

	@GUITest
	public void testDeleteSelectedListButtonError() {
		ListaSpesa lista = new ListaSpesa("Lista esselunga");
		GuiActionRunner.execute(() -> appSwingView.getListaListeSpesaModel().addElement(lista));
		window.list("elencoListe").selectItem(0);
		window.button(JButtonMatcher.withText("Cancella Lista selezionata")).click();
		assertThat(window.list("elencoListe").contents()).isEmpty();
		window.label("errorMessageLabelList").requireText("This shopping list does not exist: " + lista);
	}

	@Test

	@GUITest
	public void testAllProductsGivenAList() {
		ListaSpesa lista = new ListaSpesa("Lista esselunga");
		listaDao.save(lista);
		GuiActionRunner.execute(() -> appSwingView.getListaListeSpesaModel().addElement(lista));
		Prodotto prodotto1 = new Prodotto("Mela", 1, lista);
		Prodotto prodotto2 = new Prodotto("Pera", 1, lista);
		prodottoDao.save(prodotto1);
		prodottoDao.save(prodotto2);
		window.list("elencoListe").selectItem(0);
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();

		assertThat(window.list("elencoProdotti").contents()).containsExactly(prodotto1.toString(),
				prodotto2.toString());
	}

	@Test

	@GUITest
	public void testAddProductsButtonSuccess() {
		window.textBox("nomeListaTextBox").enterText("Lista spesa");
		window.button(JButtonMatcher.withText("Crea Lista")).click();
		window.list("elencoListe").selectItem(0);
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();
		window.textBox("prodottoTextBox").enterText("Mela");
		window.textBox("quantitaTextBox").setText("");
		window.textBox("quantitaTextBox").enterText("1");
		window.button(JButtonMatcher.withText("Aggiungi Prodotto")).click();
		assertThat(window.list("elencoProdotti").contents()).containsExactly("1 Mela");
	}

	@Test

	@GUITest
	public void testAddProductsButtonError() {
		ListaSpesa lista = new ListaSpesa("Lista spesa");
		listaDao.save(lista);
		GuiActionRunner.execute(() -> appSwingView.getListaListeSpesaModel().addElement(lista));
		window.list("elencoListe").selectItem(0);
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();
		window.textBox("prodottoTextBox").enterText("Mela");
		window.textBox("quantitaTextBox").setText("");
		window.textBox("quantitaTextBox").enterText("-1");
		window.button(JButtonMatcher.withText("Aggiungi Prodotto")).click();
		window.label("errorMessageProductModifiedLabel")
				.requireText("This product has no valid name or quantity values: " + null);
	}

	@Test

	@GUITest
	public void testDeleteSelectedProductButtonSuccess() {
		ListaSpesa lista = new ListaSpesa("Lista esselunga");
		listaDao.save(lista);
		GuiActionRunner.execute(() -> prodottoController.saveNewProduct(new Prodotto("mela", 1, lista)));

		window.list("elencoProdotti").selectItem(0);
		window.button(JButtonMatcher.withText("Cancella Prodotto Selezionato")).click();
		assertThat(window.list("elencoProdotti").contents()).isEmpty();
		assertThat(window.label("errorMessageProductLabel").requireText(" "));
	}

	@Test

	@GUITest
	public void testDeleteSelectedProductButtonShowError() {
		ListaSpesa lista = new ListaSpesa("Lista esselunga");
		listaDao.save(lista);
		Prodotto prodotto1 = new Prodotto("mela", 1, lista);

		GuiActionRunner.execute(() -> appSwingView.getListaProdottiModel().addElement(prodotto1));
		window.list("elencoProdotti").selectItem(0);
		window.button(JButtonMatcher.withText("Cancella Prodotto Selezionato")).click();
		assertThat(window.list("elencoProdotti").contents()).isEmpty();
		window.label("errorMessageProductLabel").requireText("This product does not exist: " + prodotto1);
	}

	@Test

	@GUITest
	public void testUpdateProductButtonSuccess() {
		Prodotto prodottoDaModificare = new Prodotto("Pera", 1, null);
		prodottoDao.save(prodottoDaModificare);
		GuiActionRunner.execute(() -> appSwingView.getListaProdottiModel().addElement(prodottoDaModificare));
		window.list("elencoProdotti").selectItem(0);
		window.button(JButtonMatcher.withText("Modifica Prodotto Selezionato")).click();
		window.textBox("prodottoTextBox").setText("");
		window.textBox("prodottoTextBox").enterText("Mela");
		window.textBox("quantitaTextBox").setText("");
		window.textBox("quantitaTextBox").enterText("2");
		window.button(JButtonMatcher.withText("Salva Prodotto Modificato")).click();
		assertThat(prodottoDaModificare.getName() == "Mela" && prodottoDaModificare.getQuantity() == 2);
	}

	@Test

	@GUITest
	public void testUpdateProductButtonError() {
		Prodotto prodottoDaModificare = new Prodotto("Pera", 1, null);
		GuiActionRunner.execute(() -> appSwingView.getListaProdottiModel().addElement(prodottoDaModificare));
		window.list("elencoProdotti").selectItem(0);
		window.button(JButtonMatcher.withText("Modifica Prodotto Selezionato")).click();
		window.textBox("prodottoTextBox").setText("");
		window.textBox("prodottoTextBox").enterText("Mela");
		window.textBox("quantitaTextBox").setText("");
		window.textBox("quantitaTextBox").enterText("2");
		window.button(JButtonMatcher.withText("Salva Prodotto Modificato")).click();
		window.label("errorMessageProductModifiedLabel")
				.requireText("This product does not exist: " + prodottoDaModificare);
	}

}
