package com.myproject.app.main;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.inject.Guice;
import com.myproject.app.controller.ListaSpesaController;
import com.myproject.app.controller.ProdottoController;
import com.myproject.app.dao.ListaDellaSpesaDao;
import com.myproject.app.dao.ProdottoDao;
import com.myproject.app.dao.TransactionTemplate;
import com.myproject.app.guice.AppSwingMySqlDefaultModule;
import com.myproject.app.view.AppSwingView;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(mixinStandardHelpOptions = true)
public class ShoppingListSwingApp implements Callable<Void> {

	@Option(names = { "--mysql-host" }, description = "mysql host address")
	private String mysqlHost = "jdbc:mysql://localhost:3306";

	@Option(names = { "--db-name" }, description = "Database name")
	private String databaseName = "dbprogetto";

	@Option(names = { "--db-username" }, description = "Database user")
	private String username = "root";

	@Option(names = { "--db-password" }, description = "Database psw")
	private String dbpsw = "root";

	private ProdottoDao prodottoDao;
	private ListaDellaSpesaDao listaDao;
	private ProdottoController prodottoController;
	private ListaSpesaController listaController;
	private TransactionTemplate transaction;
	private EntityManagerFactory entityManagerFactory;

	public static void main(String[] args) {
		CommandLine.call(new ShoppingListSwingApp(), args);
	}

	@Override
	public Void call() throws Exception {
		EventQueue.invokeLater(() -> {
			try {

				/*
				 * Guice.createInjector(new
				 * AppSwingMySqlDefaultModule().mysqlHost(mysqlHost).mySqlUsername(username)
				 * .mySqlPassword(dbpsw)).getInstance(AppSwingView.class).start();
				 */
				Map<String, String> configOverrides = new HashMap<>();
				configOverrides.put("hibernate.connection.url", mysqlHost);
				configOverrides.put("hibernate.connection.password", dbpsw);
				configOverrides.put("hibernate.connection.username", username);
				configOverrides.put("hibernate.hbm2ddl.auto", "update");

				entityManagerFactory = Persistence.createEntityManagerFactory("e2e", configOverrides);
				transaction = new TransactionTemplate(entityManagerFactory);

				listaDao = new ListaDellaSpesaDao(transaction);
				prodottoDao = new ProdottoDao(transaction);
				AppSwingView appSwingView = new AppSwingView();
				prodottoController = new ProdottoController(appSwingView, prodottoDao);
				listaController = new ListaSpesaController(appSwingView, listaDao);
				appSwingView.setViewController(listaController, prodottoController);
				appSwingView.setVisible(true);
				listaController.allListeSpesa();

			} catch (Exception e) {
				System.err.println(e);
			}
		});
		return null;
	}

}
