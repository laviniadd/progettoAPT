package com.myproject.app.dao;

public interface BaseDaoInterface<T> {
	public void save(T domain);
}
