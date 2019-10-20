package com.myproject.app.view;

import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.myproject.app.controller.ListaSpesaController;
import com.myproject.app.controller.ProdottoController;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;

import org.junit.Test;

@RunWith(GUITestRunner.class)
public class AppSwingViewTest extends AssertJSwingJUnitTestCase {
	private FrameFixture window;
	private AppSwingView appSwingView;

	@Mock
	private ListaSpesaController listaSpesaController;
	@Mock
	private ProdottoController prodottoController;

	@Override
	public void onSetUp() {
		MockitoAnnotations.initMocks(this);
		
		GuiActionRunner.execute(() -> {
			appSwingView = new AppSwingView();
			appSwingView.setViewController(listaSpesaController, prodottoController);
			return appSwingView;
		});
		window = new FrameFixture(robot(), appSwingView);
		window.show(); // shows the frame to test
	}

	@Test

	@GUITest
	public void testControlsInitialStates() {
		window.label(JLabelMatcher.withText("Nome Lista:"));
		window.textBox("nomeListaTextBox").requireEnabled();
		window.button(JButtonMatcher.withText("Crea Lista")).requireDisabled();
		window.list("elencoListe");
		window.button(JButtonMatcher.withText("Cancella Lista selezionata")).requireDisabled();
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).requireDisabled();
		window.label(JLabelMatcher.withText("Prodotto:"));
		window.textBox("prodottoTextBox").requireEnabled();
		window.textBox("prodottoTextBox").requireNotEditable();
		window.label(JLabelMatcher.withText("Quantità:"));
		window.textBox("quantitaTextBox").requireEnabled();
		window.textBox("quantitaTextBox").requireNotEditable();
		window.button(JButtonMatcher.withText("Aggiungi Prodotto")).requireDisabled();
		window.button(JButtonMatcher.withText("Salva Prodotto Modificato")).requireDisabled();
		window.list("elencoProdotti");
		window.button(JButtonMatcher.withText("Cancella Prodotto Selezionato")).requireDisabled();
		window.button(JButtonMatcher.withText("Modifica Prodotto Selezionato")).requireDisabled();

	}

	@Test
	public void testWhenListNameTextBoxIsNotEmptyThenCreateListButtonShouldBeEnabled() {
		window.textBox("nomeListaTextBox").enterText("Lista spesa");
		window.button(JButtonMatcher.withText("Crea Lista")).requireEnabled();
	}

	@Test
	public void testWhenListNameTextBoxIsBlankCreateListButtonShouldBeDisabled() {
		JTextComponentFixture nomeListaTextBox = window.textBox("nomeListaTextBox");

		nomeListaTextBox.enterText(" ");
		window.button(JButtonMatcher.withText("Crea Lista")).requireDisabled();

		nomeListaTextBox.enterText("");
		window.button(JButtonMatcher.withText("Crea Lista")).requireDisabled();
	}

	@Test
	public void testDeleteButtonAndModifyAddButtonUsedForListsShouldBeEnabledOnlyWhenAListIsSelected() {
		ListaSpesa listaSpesa = new ListaSpesa("Spesa esselunga");

		GuiActionRunner.execute(() -> appSwingView.getListaListeSpesaModel().addElement(listaSpesa));
		window.list("elencoListe").selectItem(0);

		JButtonFixture deleteListButton = window.button(JButtonMatcher.withText("Cancella Lista selezionata"));
		JButtonFixture addAndModifyButton = window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti"));
		deleteListButton.requireEnabled();
		addAndModifyButton.requireEnabled();

		window.list("elencoListe").clearSelection();
		deleteListButton.requireDisabled();
		addAndModifyButton.requireDisabled();
	}

	@Test
	public void testWhenProductTextBoxIsNotEmptyAndQuantitaTextBoxIsNotZeroOrNegativeThenAddProductButtonShouldBeEnabled() {
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();

		window.textBox("prodottoTextBox").enterText("Mela");
		window.textBox("quantitaTextBox").setText("");
		window.textBox("quantitaTextBox").enterText("2");

		window.button(JButtonMatcher.withText("Aggiungi Prodotto")).requireEnabled();
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).requireDisabled();
	}

	@Test
	public void testWhenProductTextBoxIsNotEmptyAndQuantityTextBoxIsNegativeThenAddProductButtonShouldBeDisabled() {
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();

		window.textBox("prodottoTextBox").enterText("Mela");
		window.textBox("quantitaTextBox").setText("");
		window.textBox("quantitaTextBox").enterText("-5");

		window.button(JButtonMatcher.withText("Aggiungi Prodotto")).requireDisabled();
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).requireDisabled();
	}

	@Test
	public void testWhenProductTextBoxIsNotEmptyAndQuantityTextBoxIsZeroThenAddProductButtonShouldBeDisabled() {
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();

		window.textBox("prodottoTextBox").enterText("Mela");
		window.textBox("quantitaTextBox").setText("");
		window.textBox("quantitaTextBox").enterText("0");

		window.button(JButtonMatcher.withText("Aggiungi Prodotto")).requireDisabled();
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).requireDisabled();
	}

	@Test
	public void testWhenProductTextBoxAndQuantityTextBoxIsBlankAddProductButtonShouldBeDisabled() {
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();

		JTextComponentFixture prodottoTextBox = window.textBox("prodottoTextBox");
		JTextComponentFixture quantitaTextBox = window.textBox("quantitaTextBox");

		prodottoTextBox.enterText(" ");
		quantitaTextBox.setText("");
		quantitaTextBox.enterText(" ");
		window.button(JButtonMatcher.withText("Aggiungi Prodotto")).requireDisabled();

		prodottoTextBox.enterText("");
		quantitaTextBox.setText("");
		quantitaTextBox.enterText("");
		window.button(JButtonMatcher.withText("Aggiungi Prodotto")).requireDisabled();

		prodottoTextBox.enterText("mela");
		quantitaTextBox.setText("");
		quantitaTextBox.enterText("");
		window.button(JButtonMatcher.withText("Aggiungi Prodotto")).requireDisabled();

		prodottoTextBox.setText("");
		prodottoTextBox.enterText(" ");
		quantitaTextBox.setText("");
		quantitaTextBox.enterText("1");
		window.button(JButtonMatcher.withText("Aggiungi Prodotto")).requireDisabled();
		
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).requireDisabled();
	}

	@Test
	public void testDeleteAndModifyButtonUsedForProductsShouldBeEnabledOnlyWhenAProductIsSelected() {
		Prodotto prodotto = new Prodotto("Mela", 2, null);

		GuiActionRunner.execute(() -> appSwingView.getListaProdottiModel().addElement(prodotto));
		window.list("elencoProdotti").selectItem(0);

		JButtonFixture deleteButton = window.button(JButtonMatcher.withText("Cancella Prodotto Selezionato"));
		JButtonFixture modifyButton = window.button(JButtonMatcher.withText("Modifica Prodotto Selezionato"));
		deleteButton.requireEnabled();
		modifyButton.requireEnabled();

		window.list("elencoProdotti").clearSelection();
		deleteButton.requireDisabled();
		modifyButton.requireDisabled();
	}

	@Test
	public void testWhenModifyProductButtonIsClickedSaveProductSelectedButtonShouldBeEnabled() {
		Prodotto prodotto = new Prodotto("Mela", 2, null);

		GuiActionRunner.execute(() -> appSwingView.getListaProdottiModel().addElement(prodotto));
		window.list("elencoProdotti").selectItem(0);
		window.button(JButtonMatcher.withText("Modifica Prodotto Selezionato")).click();
		window.button(JButtonMatcher.withText("Salva Prodotto Modificato")).requireEnabled();
		window.button(JButtonMatcher.withText("Cancella Prodotto Selezionato")).requireDisabled();
		window.button(JButtonMatcher.withText("Aggiungi Prodotto")).requireDisabled();
		window.textBox("prodottoTextBox").requireEditable();
		window.textBox("prodottoTextBox").text().toString().contains("Mela");
		window.textBox("quantitaTextBox").requireEditable();
		window.textBox("quantitaTextBox").text().toString().contains("2");
		window.button(JButtonMatcher.withText("Modifica Prodotto Selezionato")).requireDisabled();
		window.list("elencoProdotti").requireDisabled();
	}

	// --------------------

	@Test
	public void testsShowAllLists() {
		ListaSpesa lista1 = new ListaSpesa("Spesa coop");
		ListaSpesa lista2 = new ListaSpesa("Spesa pam");
		appSwingView.showAllEntities(Arrays.asList(lista1, lista2));
		String[] listContents = window.list("elencoListe").contents();
		assertThat(listContents).containsExactly(lista1.toString(), lista2.toString());
	}

	@Test
	public void testsShowAllProducts() {
		Prodotto prodotto1 = new Prodotto("mela", 2, null);
		Prodotto prodotto2 = new Prodotto("pera", 1, null);
		appSwingView.showAllEntities(Arrays.asList(prodotto1, prodotto2));
		String[] listContents = window.list("elencoProdotti").contents();
		assertThat(listContents).containsExactly(prodotto1.toString(), prodotto2.toString());
	}

	@Test
	public void testsShowAllListsEmpty() {
		List<ListaSpesa> emptyList = new ArrayList<ListaSpesa>();
		appSwingView.showAllEntities(emptyList);
		String[] listContents = window.list("elencoListe").contents();
		assertThat(listContents).isEmpty();
	}

	@Test
	public void testsShowAllProductsEmpty() {
		List<Prodotto> emptyList = new ArrayList<Prodotto>();
		appSwingView.showAllEntities(emptyList);
		String[] listContents = window.list("elencoProdotti").contents();
		assertThat(listContents).isEmpty();
	}

	@Test
	public void testShowErrorShouldShowTheMessageInTheErrorLabelForList() {
		ListaSpesa lista = new ListaSpesa("spesa");
		appSwingView.showError("This shopping list already exist", lista);
		window.label("errorMessageLabelList").requireText("This shopping list already exist: " + lista);
	}

	@Test
	public void testShowErrorShouldShowTheMessageInTheErrorLabelForProductAlreadyExists() {
		Prodotto prodotto = new Prodotto();
		appSwingView.showError("This product already exist", prodotto);
		window.label("errorMessageProductModifiedLabel").requireText("This product already exist: " + prodotto);
	}

	@Test
	public void testShowErrorShouldShowTheMessageInTheErrorLabelForProductWithNoValidValues() {
		Prodotto prodotto = new Prodotto();
		appSwingView.showError("This product has no valid name or quantity values", prodotto);
		window.label("errorMessageProductModifiedLabel")
				.requireText("This product has no valid name or quantity values: " + prodotto);
	}

	@Test
	public void testShowErrorListNotFound() {
		ListaSpesa lista1 = new ListaSpesa("Spesa coop");
		ListaSpesa lista2 = new ListaSpesa("Spesa pam");
		GuiActionRunner.execute(() -> {
			DefaultListModel<ListaSpesa> listModelOfLists = appSwingView.getListaListeSpesaModel();
			listModelOfLists.addElement(lista1);
			listModelOfLists.addElement(lista2);
		});
		GuiActionRunner
				.execute(() -> appSwingView.showErrorEntityNotFound("This shopping list does not exist", lista1));
		window.label("errorMessageLabelList").requireText("This shopping list does not exist: " + lista1.toString());
		assertThat(window.list("elencoListe").contents()).containsExactly("Spesa pam");
	}
	
	@Test
	public void testShowErrorProductNotFound() {
		Prodotto prodotto1 = new Prodotto();
		Prodotto prodotto2 = new Prodotto();
		GuiActionRunner.execute(() -> {
			DefaultListModel<Prodotto> listModelOfProducts = appSwingView.getListaProdottiModel();
			listModelOfProducts.addElement(prodotto1);
			listModelOfProducts.addElement(prodotto2);
		});
		GuiActionRunner
				.execute(() -> appSwingView.showErrorEntityNotFound("This product does not exist", prodotto1));
		window.label("errorMessageProductLabel").requireText("This product does not exist: " + prodotto1.toString());
		assertThat(window.list("elencoProdotti").contents()).containsExactly(prodotto2.toString());
	}

	@Test
	public void testListsAddedShouldAddListsToTheListAndResetTheErrorLabel() {
		ListaSpesa lista = new ListaSpesa("spesa");
		appSwingView.showNewEntity(lista);
		String[] listContents = window.list("elencoListe").contents();
		assertThat(listContents).containsExactly(lista.toString());
		window.label("errorMessageLabelList").requireText(" ");
	}

	@Test
	public void testProductsAddedShouldAddProductsToTheListAndResetTheErrorLabel() {
		Prodotto prodotto = new Prodotto("Mela", 1, null);
		appSwingView.showNewEntity(prodotto);
		String[] listContents = window.list("elencoProdotti").contents();
		
		window.label("errorMessageLabelList").requireText(" ");
		assertThat(listContents).containsExactly(prodotto.toString());
	}

	@Test
	public void testRemovedListShouldRemoveTheListFromTheListAndResetTheErrorLabel() {
		ListaSpesa lista1 = new ListaSpesa("Spesa coop");
		ListaSpesa lista2 = new ListaSpesa("Spesa pam");

		GuiActionRunner.execute(() -> {
			DefaultListModel<ListaSpesa> listaEntitiesModel = appSwingView.getListaListeSpesaModel();
			listaEntitiesModel.addElement(lista1);
			listaEntitiesModel.addElement(lista2);
		});

		appSwingView.showRemovedEntity(lista1);

		String[] listContents = window.list("elencoListe").contents();

		window.label("errorMessageLabelList").requireText(" ");
		assertThat(listContents).containsExactly(lista2.toString());
	}

	@Test
	public void testRemovedProductShouldRemoveTheProductFromTheListAndResetTheErrorLabel() {
		Prodotto prodotto1 = new Prodotto("spinaci", 1, null);
		Prodotto prodotto2 = new Prodotto("mela", 1, null);

		GuiActionRunner.execute(() -> {
			DefaultListModel<Prodotto> listaEntitiesModel = appSwingView.getListaProdottiModel();
			listaEntitiesModel.addElement(prodotto1);
			listaEntitiesModel.addElement(prodotto2);
		});

		appSwingView.showRemovedEntity(prodotto1);

		String[] listContents = window.list("elencoProdotti").contents();
		assertThat(listContents).containsExactly(prodotto2.toString());
		window.label("errorMessageProductLabel").requireText(" ");
	}

	@Test
	public void testCreateListButtonShouldTriggerListControllerSaveNewList() {
		window.textBox("nomeListaTextBox").enterText("Lista della spesa");
		window.button(JButtonMatcher.withText("Crea Lista")).click();
		verify(listaSpesaController).saveNewLista(new ListaSpesa("Lista della spesa"));
	}

	@Test
	public void testDeleteButtonShouldTriggerListControllerDeleteList() {
		ListaSpesa lista1 = new ListaSpesa("Spesa coop");
		ListaSpesa lista2 = new ListaSpesa("Spesa pam");

		GuiActionRunner.execute(() -> {
			DefaultListModel<ListaSpesa> listaEntitiesModel = appSwingView.getListaListeSpesaModel();
			listaEntitiesModel.addElement(lista1);
			listaEntitiesModel.addElement(lista2);
		});

		window.list("elencoListe").selectItem(1);
		window.button(JButtonMatcher.withText("Cancella Lista selezionata")).click();
		verify(listaSpesaController).deleteListaSpesa(lista2);
	}

	@Test
	public void testAddProductButtonShouldTriggerProductControllerSaveNewProduct() {
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();
		window.textBox("prodottoTextBox").enterText("Mela");
		window.textBox("quantitaTextBox").setText("");
		window.textBox("quantitaTextBox").enterText("2");
		Prodotto prodottoDaSalvare = new Prodotto("Mela", 2, new ListaSpesa());
		window.button(JButtonMatcher.withText("Aggiungi Prodotto")).click();
		verify(prodottoController).saveNewProduct(prodottoDaSalvare);
	}

	@Test
	public void testDeleteButtonShouldDelegateToProductControllerDeleteList() {
		Prodotto prodotto1 = new Prodotto("spinaci", 1, null);
		Prodotto prodotto2 = new Prodotto("mela", 1, null);

		GuiActionRunner.execute(() -> {
			DefaultListModel<Prodotto> listaEntitiesModel = appSwingView.getListaProdottiModel();
			listaEntitiesModel.addElement(prodotto1);
			listaEntitiesModel.addElement(prodotto2);
		});

		window.list("elencoProdotti").selectItem(1);
		window.button(JButtonMatcher.withText("Cancella Prodotto Selezionato")).click();
		verify(prodottoController).deleteProduct(prodotto2);
	}

	@Test
	public void testWhenModifyAddButtonIsClickedProductControllerIsExecuted() {
		ListaSpesa listaSelezionata = new ListaSpesa("Lista della spesa");

		GuiActionRunner.execute(() -> {
			DefaultListModel<ListaSpesa> defaultListModelLista = appSwingView.getListaListeSpesaModel();
			defaultListModelLista.addElement(listaSelezionata);
		});

		window.list("elencoListe").selectItem(0);
		window.button(JButtonMatcher.withText("Modifica/Aggiungi prodotti")).click();
		window.textBox("prodottoTextBox").requireEditable();
		window.textBox("quantitaTextBox").requireEditable();
		verify(prodottoController).allProductsGivenAList(listaSelezionata);
	}

	/*
	 * TESTO CHE CI SIA SOLO PRODOTTO2 PERCHE' QUI NON ESEGUO IL CONTROLLER,
	 * VERIFICO CHE SIA CHIAMATO IL CONTROLLER E CHE SIA TOLTO DALLA LISTA IL
	 * PRODOTTO CHE DEVO MODFICARE
	 */
	@Test
	public void testWhenSaveModifiedProductButtonIsClickedProductControllerIsExecuted() {
		Prodotto prodotto1 = new Prodotto("spinaci", 1, null);
		Prodotto prodotto2 = new Prodotto("mela", 1, null);
		GuiActionRunner.execute(() -> {
			DefaultListModel<Prodotto> defaultListModelProduct = appSwingView.getListaProdottiModel();
			defaultListModelProduct.addElement(prodotto1);
			defaultListModelProduct.addElement(prodotto2);
		});

		window.list("elencoProdotti").selectItem(0);
		window.button(JButtonMatcher.withText("Modifica Prodotto Selezionato")).click();

		window.textBox("prodottoTextBox").setText("");
		window.textBox("prodottoTextBox").enterText("Pera");
		window.textBox("quantitaTextBox").setText("");
		window.textBox("quantitaTextBox").enterText("2");

		window.button(JButtonMatcher.withText("Salva Prodotto Modificato")).click();
		verify(prodottoController).updateProduct(prodotto1, "Pera", 2);
		String[] listContents = window.list("elencoProdotti").contents();
		assertThat(listContents).containsExactly(prodotto2.toString());
	}

}
