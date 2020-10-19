package kr.co.codeJ.JPA.DBAccessor;

import java.util.List;
import java.util.Map;

/**
 * This is Interface for execution of query 
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
public interface DBCommService {
	/**
	 * execute using query String
	 * @param queryString
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> executeNativeQuery(String queryString);
	/**
	 * execute using query String with parameter
	 * @param queryString
	 * @param Map<String, Object> params
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> executeNativeQuery(String queryString, Map<String, Object> params );
	/**
	 * update using query string 
	 * @param queryString
	 */
	public void executeUpdate(String queryString);
	/**
	 * update using query string with parameter
	 * @param Map<String, Object> params
	 * @param params
	 */
	public void executeUpdate(String queryString, Map<String, Object> params);
}
