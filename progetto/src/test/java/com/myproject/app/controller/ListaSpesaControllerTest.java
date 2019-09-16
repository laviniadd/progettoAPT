package com.myproject.app.controller;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
	 * @Test public void testAllListaSpesa() { List<ListaSpesa> students;
	 * 
	 * when(studentRepository.findAll()) .thenReturn(students);
	 * schoolController.allStudents(); verify(studentView)
	 * .showAllStudents(students); }
	 */
}
