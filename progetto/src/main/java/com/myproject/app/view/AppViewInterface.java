package com.myproject.app.view;

import java.util.List;

import com.myproject.app.model.ListaSpesa;

public interface AppViewInterface {
	
	public void showAllEntity(List<ListaSpesa> entitiesDaMostrare);

	public void showError(String errorMessage, ListaSpesa entity);

	public void showNewEntity(ListaSpesa entity);

	public void showRemovedEntity(ListaSpesa entityCancellate);
}
