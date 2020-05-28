package com.udmtek.DBCore.DBAccessor;

import java.util.List;

public interface DBCoreDAO <T> {
	public T get(DBCoreKey key);
	public List<T> getAll();
	public List<T> getfromJPQL(String jquery);
	public List<T> getfromSQL(String sqlquery);
	public void save(T object);
	public void delete(T object);
}
