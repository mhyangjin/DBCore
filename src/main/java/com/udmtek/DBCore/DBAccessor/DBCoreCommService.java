package com.udmtek.DBCore.DBAccessor;

import java.util.List;
import java.util.Map;

public interface DBCoreCommService {
	public List<Map<String,Object>> executeNativeQuery(String queryString);
	public List<Map<String,Object>> executeNativeQuery(String queryString, Map<String, Object> params );
	public void executeUpdate(String queryString);
	public void executeUpdate(String queryString, Map<String, Object> params);
}
