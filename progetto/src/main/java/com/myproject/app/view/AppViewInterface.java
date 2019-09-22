package com.myproject.app.view;

import java.util.List;

import com.myproject.app.model.ListaSpesa;

public interface AppViewInterface {
	
	public void showAllEntity(List<ListaSpesa> entityDaMostrare);

	public void showError(String string, ListaSpesa elencoEntity);

	public void showNewEntity(ListaSpesa elencoEntity);

	public void showRemovedEntity(ListaSpesa elencoEntityCancellate);
}
