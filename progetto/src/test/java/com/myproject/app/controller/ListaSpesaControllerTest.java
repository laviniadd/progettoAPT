package com.myproject.app.controller;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
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

	/*
	 * @Test public void testAllListaSpesa() {
	 * 
	 * List<ListaSpesa> listeSpesa = asList(new ListaSpesa());
	 * 
	 * when(listaSpesaDao.findAll()).thenReturn(listeSpesa);
	 * 
	 * listaSpesaController.allListeSpesa();
	 * 
	 * verify(listaSpesaView).showAllListeSpesa(listeSpesa);
	 * 
	 * }
	 */

}
