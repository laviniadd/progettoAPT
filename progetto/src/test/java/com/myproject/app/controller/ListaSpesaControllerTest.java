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
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.view.ListaSpesaView;

public class ListaSpesaControllerTest {
	@InjectMocks
	private ListaSpesaController listaSpesaController;
	@Mock
	private ListaDellaSpesaDao listaSpesaDao;
	@Mock
	private ListaSpesaView listaSpesaView;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAllListaSpesa() {

		List<ListaSpesa> listeSpesa = asList(new ListaSpesa());

		when(listaSpesaDao.findAll()).thenReturn(listeSpesa);

		listaSpesaController.allListeSpesa();

		verify(listaSpesaView).showAllListeSpesa(listeSpesa);

	}

	@Test
	public void testSaveNewListaWhenListaDoesNotAlreadyExist() {
		ListaSpesa lista = new ListaSpesa();

		when(listaSpesaDao.findById(lista.getId())).thenReturn(null);

		listaSpesaController.saveNewLista(lista);

		InOrder inOrder = inOrder(listaSpesaDao, listaSpesaView);
		inOrder.verify(listaSpesaDao).save(lista);
		inOrder.verify(listaSpesaView).showNewLista(lista);
	}

	@Test
	public void testSaveListaWhenListaAlreadyExist() {
		ListaSpesa listaDaSalvare = new ListaSpesa();

		when(listaSpesaDao.findById(listaDaSalvare.getId())).thenReturn(listaDaSalvare);

		listaSpesaController.saveNewLista(listaDaSalvare);

		verify(listaSpesaView).showError("This shopping list already exist", listaDaSalvare);
		verifyNoMoreInteractions(ignoreStubs(listaSpesaDao));
	}

	@Test
	public void testDeleteListaWhenListaAlreadyExists() {
		ListaSpesa listaDaCancellare = new ListaSpesa();
				
		when(listaSpesaDao.findById(listaDaCancellare.getId())).thenReturn(listaDaCancellare);
		
		listaSpesaController.deleteListaSpesa(listaDaCancellare);
		
		verify(listaSpesaDao).delete(listaDaCancellare.getId());
		verify(listaSpesaView).showRemovedList(listaDaCancellare);
	}
	
	@Test
	public void testDeleteListaWhenListaNotExists() {
		ListaSpesa listaDaCancellare = new ListaSpesa();
				
		when(listaSpesaDao.findById(listaDaCancellare.getId())).thenReturn(null);
		
		listaSpesaController.deleteListaSpesa(listaDaCancellare);
		
		verify(listaSpesaView).showError("This shopping list does not exist", listaDaCancellare);
	}

}
