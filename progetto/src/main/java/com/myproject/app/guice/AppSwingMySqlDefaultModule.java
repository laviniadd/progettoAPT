package com.myproject.app.guice;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.myproject.app.controller.ListaSpesaController;
import com.myproject.app.controller.ProdottoController;
import com.myproject.app.view.AppSwingView;

import picocli.CommandLine.Option;


public class AppSwingMySqlDefaultModule extends AbstractModule{
	/*
	 * private String mysqlHost = "jdbc:mysql://localhost:3306"; private String
	 * databaseName = "dbprogetto"; private String username = "root"; private String
	 * dbpsw = "root";
	 * 
	 * public AppSwingMySqlDefaultModule mysqlHost(String mysqlHost) {
	 * this.mysqlHost = mysqlHost; return this; }
	 * 
	 * public AppSwingMySqlDefaultModule databaseName(String databaseName) {
	 * this.databaseName = databaseName; return this; }
	 * 
	 * public AppSwingMySqlDefaultModule mySqlUsername(String username) {
	 * this.username = username; return this; }
	 * 
	 * public AppSwingMySqlDefaultModule mySqlPassword(String dbpsw) { this.dbpsw =
	 * dbpsw; return this; }
	 * 
	 * @Override protected void configure() {
	 * bind(String.class).annotatedWith(MySqlHost.class).toInstance(mysqlHost);
	 * bind(String.class).annotatedWith(MySqlDbName.class).toInstance(databaseName);
	 * bind(String.class).annotatedWith(MySqlUsername.class).toInstance(username);
	 * bind(String.class).annotatedWith(MySqlPassword.class).toInstance(dbpsw);
	 * 
	 * //bind(StudentRepository.class).to(StudentMongoRepository.class);
	 * 
	 * install(new FactoryModuleBuilder() .implement(ProdottoController.class,
	 * ProdottoController.class).implement(ListaSpesaController.class,
	 * ListaSpesaController.class) .build(ListaSpesaControllerFactory.class));
	 * 
	 * }
	 * 
	 * @Provides EntityManagerFactory emfCreate(@MySqlHost String
	 * host, @MySqlUsername String username, @MySqlPassword String dbpsw) {
	 * 
	 * Map<String, String> configOverrides = new HashMap<String, String>();
	 * configOverrides.put("hibernate.connection.url", host);
	 * configOverrides.put("hibernate.connection.password", dbpsw);
	 * configOverrides.put("hibernate.connection.username", username);
	 * configOverrides.put("hibernate.hbm2ddl.auto", "update");
	 * 
	 * return Persistence.createEntityManagerFactory("e2e", configOverrides); }
	 * 
	 * @Provides AppSwingView appSwingView(ListaSpesaControllerFactory
	 * listaSpesaControllerFactory, ProdottoControllerFactory
	 * prodottoControllerFactory) { AppSwingView view = new AppSwingView();
	 * view.setViewController(listaSpesaControllerFactory.create(view),
	 * prodottoControllerFactory.create(view)); return view; }
	 */
}
