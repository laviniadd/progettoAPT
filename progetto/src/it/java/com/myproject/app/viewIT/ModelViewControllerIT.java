package com.myproject.app.viewIT;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

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
	private AppSwingView appSwingView;
	private ProdottoController prodottoController;
	private ListaSpesaController listaController;
	private FrameFixture window;

	@BeforeClass
	public static void setUpClass() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("integration");
	}

	@AfterClass
	public static void tearDownClass() {
		entityManagerFactory.close();
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
			listaController = new ListaSpesaController(appSwingView, listaDao, prodottoDao);
			prodottoController = new ProdottoController(appSwingView, prodottoDao);
			appSwingView.setViewController(listaController, prodottoController);
			return appSwingView;
		}));
		window.show(); // shows the frame to test
	}

	protected void onTearDown() {
		transaction.executeTransaction((em) -> {
			em.close();
			return null;
		});
	}

	@Test
	public void testAddList() {
		window.textBox("nomeListaTextBox").enterText("Lista della spesa");
		window.button(JButtonMatcher.withText("Crea Lista")).click();
		
		assertThat(listaDao.findByName("Lista della spesa")).isNotEmpty();
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
		
		assertThat(prodottoDao.findByName("Kiwi")).isNotEmpty();
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
