package com.myproject.app.controller;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.myproject.app.dao.ListaDellaSpesaDao;
import com.myproject.app.dao.ProdottoDao;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.view.AppViewInterface;

public class ListaSpesaControllerTest {
	@InjectMocks
	private ListaSpesaController listaSpesaController;
	@Mock
	private ListaDellaSpesaDao listaSpesaDao;
	@Mock
	private ProdottoDao prodottoDao;
	@Mock
	private AppViewInterface listaSpesaView;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAllListaSpesa() {

		List<ListaSpesa> listeSpesa = asList(new ListaSpesa());

		when(listaSpesaDao.findAll()).thenReturn(listeSpesa);

		listaSpesaController.allListeSpesa();

		verify(listaSpesaView).showAllEntities(listeSpesa);

	}

	@Test
	public void testSaveNewListaWithNameWhenListaDoesNotAlreadyExist() {
		ListaSpesa lista = new ListaSpesa("Lista della spesa");
		when(listaSpesaDao.findById(lista.getId())).thenReturn(null);

		listaSpesaController.saveNewLista(lista);

		InOrder inOrder = inOrder(listaSpesaDao, listaSpesaView);
		inOrder.verify(listaSpesaDao).save(lista);
		inOrder.verify(listaSpesaView).showNewEntity(lista);
	}

	@Test
	public void testSaveNewListaWithNameWhenNameListIsEmptyString() {
		ListaSpesa lista = new ListaSpesa(" ");

		when(listaSpesaDao.findById(lista.getId())).thenReturn(null);

		listaSpesaController.saveNewLista(lista);

		verify(listaSpesaView).showError("This shopping list does not have name", null);
		verifyNoMoreInteractions(ignoreStubs(listaSpesaDao, listaSpesaView));
	}

	@Test
	public void testSaveNewListaWithNameWhenNameListIsEmpty() {
		ListaSpesa lista = new ListaSpesa("");
		when(listaSpesaDao.findById(lista.getId())).thenReturn(null);

		listaSpesaController.saveNewLista(lista);

		verify(listaSpesaView).showError("This shopping list does not have name", null);
		verifyNoMoreInteractions(ignoreStubs(listaSpesaDao, listaSpesaView));
	}

	@Test
	public void testSaveNewListaWithNoNameWhenListaDoesNotAlreadyExist() {
		ListaSpesa lista = new ListaSpesa();

		when(listaSpesaDao.findById(lista.getId())).thenReturn(null);

		listaSpesaController.saveNewLista(lista);

		verify(listaSpesaView).showError("This shopping list does not have name", null);
		verifyNoMoreInteractions(ignoreStubs(listaSpesaDao, listaSpesaView));
	}

	@Test
	public void testSaveListaWhenListaAlreadyExist() {
		ListaSpesa listaDaSalvare = new ListaSpesa();

		when(listaSpesaDao.findById(listaDaSalvare.getId())).thenReturn(listaDaSalvare);

		listaSpesaController.saveNewLista(listaDaSalvare);

		verify(listaSpesaView).showError("This shopping list already exist", listaDaSalvare);
		verifyNoMoreInteractions(ignoreStubs(listaSpesaDao, listaSpesaView));
	}

	@Test
	public void testDeleteListaWhenListaAlreadyExists() {
		ListaSpesa listaDaCancellare = new ListaSpesa("Spesa");

		when(listaSpesaDao.findById(listaDaCancellare.getId())).thenReturn(listaDaCancellare);

		listaSpesaController.deleteListaSpesa(listaDaCancellare);

		verify(listaSpesaDao).delete(listaDaCancellare.getId());
		verify(listaSpesaView).showRemovedEntity(listaDaCancellare);
	}

	@Test
	public void testDeleteListaWhenListaNotExists() {
		ListaSpesa listaDaCancellare = new ListaSpesa();

		when(listaSpesaDao.findById(listaDaCancellare.getId())).thenReturn(null);

		listaSpesaController.deleteListaSpesa(listaDaCancellare);

		verify(listaSpesaView).showErrorEntityNotFound("This shopping list does not exist", listaDaCancellare);
		verifyNoMoreInteractions(ignoreStubs(listaSpesaDao, listaSpesaView, prodottoDao));
	}

}
