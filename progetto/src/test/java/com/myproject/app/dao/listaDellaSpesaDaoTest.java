package com.myproject.app.dao;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import com.myproject.app.model.ListaSpesa;

public class listaDellaSpesaDaoTest extends JpaTest {

	ListaDellaSpesaDao listaDao;

	@Override
	protected void init() throws InitializationError {

		listaDao = new ListaDellaSpesaDao();
	}

	@Test
	public void testSaveListaDellaSpesa() {
		ListaSpesa lista = new ListaSpesa();
		lista.setName("lista1");
		listaDao.save(lista);

		assertThat(entityManager.createQuery("select e from ListaSpesa e where e.name = :name", ListaSpesa.class)
				.setParameter("name", lista.getName()).getResultList()).containsExactly(lista);

	}

}
