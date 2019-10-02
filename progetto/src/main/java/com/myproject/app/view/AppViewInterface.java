package com.myproject.app.view;

import java.util.List;

public interface AppViewInterface {
	
	public <T> void showAllEntities(List<T> entitiesDaMostrare);

	public <T> void showError(String errorMessage, T entity);

	public <T> void showNewEntity(T entity);

	public <T> void showRemovedEntity(T entityCancellate);

	public <T> void showErrorEntityNotFound(String errorMessage, T entity);
}
