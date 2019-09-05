package com.myproject.app.dao;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import com.myproject.app.model.ListaSpesa;

public class listaDellaSpesaDaoTest extends JpaTest {

	private ListaDellaSpesaDao listaDao;
	private ListaSpesa lista;
	private ListaSpesa lista2;

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
	public void testListaDellaSpesaFindById() {
		lista = new ListaSpesa();
		lista2 = new ListaSpesa();
		
		addListToDatabase(lista);
		addListToDatabase(lista2);
		
		entityManager.getTransaction().commit();
		entityManager.clear();

		assertThat(listaDao.findById(lista2.getId())).isEqualTo(lista2);
	}

	@Test
	public void testListaDellaSpesaFindByIdNotFound() {
		assertThat(listaDao.findById(Long.valueOf(1))).isNull();
	}

	@Test
	public void testFindAllListaDellaSpesaWhenDatabaseIsNotEmpty() {
		lista = new ListaSpesa();
		lista2 = new ListaSpesa();
		
		addListToDatabase(lista);
		addListToDatabase(lista2);
		
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		assertThat(listaDao.findAll()).containsExactly(lista, lista2);
	}

	@Test
	public void testFindAllListaDellaSpesaWhenDatabaseIsEmpty() {
		assertThat(listaDao.findAll()).isEmpty();
	}

	private void addListToDatabase(ListaSpesa listaDaSalvare) {
		entityManager = PersistenceManager.getEntityManager();
		entityManager.persist(listaDaSalvare);		
		
	}

}