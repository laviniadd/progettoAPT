package com.myproject.app.controller;

import java.util.List;
import static java.util.Arrays.asList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.myproject.app.model.Prodotto;

public class ElencoProdottiControllerTest {
	@InjectMocks
	private ElencoProdottiController elencoProdottiController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAllProductAndQuantity() {

	}
}
