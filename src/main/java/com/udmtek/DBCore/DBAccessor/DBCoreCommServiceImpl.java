package com.udmtek.DBCore.DBAccessor;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.udmtek.DBCore.ComException.DBAccessException;

/**
 * Implementation  of the DBCoreCommService
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
@Component(value="DBCoreCommService")
@DependsOn({"DBManager"})
public class DBCoreCommServiceImpl implements DBCoreCommService {
	private static Logger logger=LoggerFactory.getLogger(DBCoreCommServiceImpl.class);
	
			
	@Autowired
	@Qualifier("DBManager")
	private DBCoreSessionManager sessionManager;
	/**
	 * execute using query String
	 * @param queryString
	 * @return List<Map<String,Object>>
	 * @exception DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> executeNativeQuery(String queryString) throws DBAccessException{
		List<Map<String,Object>> resultList = null;
		DBCoreSession currSession = DBCoreSessionManager.getCurrentSession();
		boolean sessionOpenHere=false;
		boolean sessionBeginHere=false;
		if (  currSession == null )
		{
			currSession=sessionManager.openSession(3, 100);
			sessionOpenHere=true;
		}
		SessionStateEnum sessionState=currSession.getSessionState();
		if (sessionState == SessionStateEnum.OPEN ) {
			sessionState=currSession.beginTransaction(true);
			sessionBeginHere=true;
		}
		//execute Native Query
		try {
			Session session= currSession.getThisSession();
			SQLQuery query=session.createSQLQuery(queryString);
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			resultList = (List<Map<String, Object>>) query.list();
		}
		catch (Exception e) {
			logger.error("{} {}", DBCoreSessionManager.getSessionInfo(),e.getMessage());
			throw new DBAccessException(e.getMessage());
		}
		finally {
			if (sessionState == SessionStateEnum.BEGIN && sessionBeginHere) {
				sessionState=currSession.endTransaction(false);
			}
			if (sessionState == SessionStateEnum.OPEN  && sessionOpenHere ) {
				sessionManager.closeSession(currSession);
			}
		}
		return resultList;
	}
	/**
	 * execute using query String with parameter
	 * @param queryString
	 * @param Map<String, Object> params
	 * @return List<Map<String,Object>>
	 * @exception DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> executeNativeQuery(String queryString, Map<String, Object> params ) {
		List<Map<String,Object>> resultList = null;
		DBCoreSession currSession = DBCoreSessionManager.getCurrentSession();
		boolean sessionOpenHere=false;
		boolean sessionBeginHere=false;
		if (  currSession == null )
		{
			currSession=sessionManager.openSession(3, 100);
			sessionOpenHere=true;
		}
		SessionStateEnum sessionState=currSession.getSessionState();
		if (sessionState == SessionStateEnum.OPEN ) {
			sessionState=currSession.beginTransaction(true);
			sessionBeginHere=true;
		}
		
		try {
			//execute Native Query
			Session session= currSession.getThisSession();
			SQLQuery query=session.createSQLQuery(queryString);
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			resultList = (List<Map<String, Object>>) query.list();
		}
		catch (Exception e) {
			logger.error("{} {}", DBCoreSessionManager.getSessionInfo(),e.getMessage());
			throw new DBAccessException(e.getMessage());
		}
		finally {
			if (sessionState == SessionStateEnum.BEGIN && sessionBeginHere) {
				sessionState=currSession.endTransaction(false);
			}
			if (sessionState == SessionStateEnum.OPEN && sessionOpenHere ) {
				sessionManager.closeSession(currSession);
			}
		}
		return resultList;
	}
	/**
	 * update using query string 
	 * @param queryString
	 * @exception DBAccessException
	 */
	public void executeUpdate(String queryString) throws DBAccessException{
		DBCoreSession currSession = DBCoreSessionManager.getCurrentSession();
		boolean sessionOpenHere=false;
		boolean sessionBeginHere=false;
		if (  currSession == null )
		{
			currSession=sessionManager.openSession(3, 100);
			sessionOpenHere=true;
		}
		SessionStateEnum sessionState=currSession.getSessionState();
		if (sessionState == SessionStateEnum.OPEN ) {
			sessionState=currSession.beginTransaction(true);
			sessionBeginHere=true;
		}
		try {
			//execute Native Query
			Session session= currSession.getThisSession();	
			SQLQuery query=session.createSQLQuery(queryString);
			query.executeUpdate();
		}
		catch (Exception e) {
			logger.error("{} {}", DBCoreSessionManager.getSessionInfo(),e.getMessage());
			throw new DBAccessException(e.getMessage());
		}
		finally {
			if (sessionState == SessionStateEnum.BEGIN && sessionBeginHere) {
				sessionState=currSession.endTransaction(false);
			}
			if (sessionState == SessionStateEnum.OPEN && sessionOpenHere) {
				sessionManager.closeSession(currSession);
			}
		}
	}
	/**
	 * update using query string with parameter
	 * @param Map<String, Object> params
	 * @param params
	 * @exception DBAccessException
	 */
	public void executeUpdate(String queryString, Map<String, Object> params) throws DBAccessException{
		DBCoreSession currSession = DBCoreSessionManager.getCurrentSession();
		boolean sessionOpenHere=false;
		boolean sessionBeginHere=false;
		if (  currSession == null )
		{
			currSession=sessionManager.openSession(3, 100);
			sessionOpenHere=true;
		}
		SessionStateEnum sessionState=currSession.getSessionState();
		if (sessionState == SessionStateEnum.OPEN ) {
			sessionState=currSession.beginTransaction(true);
			sessionBeginHere=true;
		}
		try {
			//execute Native Query
			Session session= currSession.getThisSession();	
			SQLQuery query=session.createSQLQuery(queryString);
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
			query.executeUpdate();
		}
		catch (Exception e) {
			logger.error("{} {}", DBCoreSessionManager.getSessionInfo(),e.getMessage());
			throw new DBAccessException(e.getMessage());
		}
		finally {
			if (sessionState == SessionStateEnum.BEGIN && sessionBeginHere) {
				sessionState=currSession.endTransaction(false);
			}
			if (sessionState == SessionStateEnum.OPEN && sessionOpenHere) {
				sessionManager.closeSession(currSession);
			}
		}
	}
}
