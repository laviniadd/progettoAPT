package com.myproject.app.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

import com.myproject.app.model.ListaSpesa;

public class listaDellaSpesaDaoTest extends JpaTest {

	ListaDellaSpesaDao listaDao;
	private ListaSpesa lista;

	@Override
	protected void init() throws InitializationError {
		listaDao = new ListaDellaSpesaDao();
	}

	@Test
	public void testSaveListaDellaSpesa() {
		lista = new ListaSpesa();
		lista.setName("lista1");
		listaDao.save(lista);

		assertThat(entityManager.createQuery("select e from ListaSpesa e where e.name = :name", ListaSpesa.class)
				.setParameter("name", lista.getName()).getResultList()).containsExactly(lista);

	}

	@Test
	public void testFindByIdListaDellaSpesa() {
		lista = new ListaSpesa();
		lista.setName("spesaesselunga");
		entityManager = PersistenceManager.getEntityManager();
		entityManager.persist(lista);
		entityManager.getTransaction().commit();
		entityManager.clear();

		ListaSpesa listaPersisted = listaDao.findById(lista.getId());
		assertThat(listaPersisted).isEqualTo(lista);
	}

}