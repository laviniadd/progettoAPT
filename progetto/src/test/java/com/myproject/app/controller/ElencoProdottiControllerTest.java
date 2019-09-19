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

import com.myproject.app.dao.ElencoProdottiDao;
import com.myproject.app.model.ElencoProdotti;
import com.myproject.app.view.ElencoProdottiView;

public class ElencoProdottiControllerTest {
	@InjectMocks
	private ElencoProdottiController elencoProdottiController;
	@Mock
	private ElencoProdottiDao elencoProdottiDao;
	@Mock
	private ElencoProdottiView elencoProdottiView;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAllElencoProdotti() {

		List<ElencoProdotti> elencoProdottiInListaSpesa = asList(new ElencoProdotti());

		when(elencoProdottiDao.findAll()).thenReturn(elencoProdottiInListaSpesa);

		elencoProdottiController.allElencoProdotti();

		verify(elencoProdottiView).showAllElencoProdotti(elencoProdottiInListaSpesa);
	}

	@Test
	public void testSaveNewElencoProdottiWhenElencoProdottiDoesNotAlreadyExist() {
		ElencoProdotti elencoProdotti = new ElencoProdotti();

		when(elencoProdottiDao.findById(elencoProdotti.getId())).thenReturn(null);

		elencoProdottiController.saveNewElencoProdotti(elencoProdotti);

		InOrder inOrder = inOrder(elencoProdottiDao, elencoProdottiView);
		inOrder.verify(elencoProdottiDao).save(elencoProdotti);
		inOrder.verify(elencoProdottiView).showNewElencoProdotti(elencoProdotti);
	}

	@Test
	public void testSaveElencoProdottiWhenElencoProdottiAlreadyExist() {
		ElencoProdotti elencoProdotti = new ElencoProdotti();

		when(elencoProdottiDao.findById(elencoProdotti.getId())).thenReturn(elencoProdotti);

		elencoProdottiController.saveNewElencoProdotti(elencoProdotti);

		verify(elencoProdottiView).showError("This list of products already exist", elencoProdotti);
		verifyNoMoreInteractions(ignoreStubs(elencoProdottiDao));
	}

	@Test
	public void testDeleteElencoProdottiWhenElencoProdottiExists() {
		ElencoProdotti elencoProdottiDaCancellare = new ElencoProdotti();

		when(elencoProdottiDao.findById(elencoProdottiDaCancellare.getId())).thenReturn(elencoProdottiDaCancellare);

		elencoProdottiController.deleteElencoProdotti(elencoProdottiDaCancellare);

		InOrder inOrder = inOrder(elencoProdottiDao, elencoProdottiView);
		inOrder.verify(elencoProdottiDao).delete(elencoProdottiDaCancellare.getId());
		inOrder.verify(elencoProdottiView).showElencoProdottiRemoved(elencoProdottiDaCancellare);
	}

	@Test
	public void testDeleteElencoProdottiWhenElencoProdottiAlreadyNotExists() {
		ElencoProdotti elencoProdottiDaCancellare = new ElencoProdotti();

		when(elencoProdottiDao.findById(elencoProdottiDaCancellare.getId())).thenReturn(null);

		elencoProdottiController.deleteElencoProdotti(elencoProdottiDaCancellare);

		verify(elencoProdottiView).showError("This list of products not exist", elencoProdottiDaCancellare);

	}
}
