package com.myproject.app.controller;

import java.util.List;

import javax.xml.ws.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import com.myproject.app.dao.ListaDellaSpesaDao;
import com.myproject.app.model.ListaSpesa;

public class ListaSpesaControllerTest {
	@InjectMocks
	private ListaSpesaController listaSpesaController;
	@Mock
	private ListaDellaSpesaDao listaSpesaDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	/*
	 * @Test public void testAllListaSpesa() {
	 * 
	 * ListaSpesa ls = new ListaSpesa(); List<ListaSpesa> listeSpesa;
	 * listeSpesa.add(ls);
	 * 
	 * when(listaSpesaDao.findAll()).thenReturn(listeSpesa);
	 * 
	 * 
	 * 
	 * Response result = listaSpesaController.allListeSpesa();
	 * 
	 * }
	 */

}
