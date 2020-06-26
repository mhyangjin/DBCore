package com.udmtek.DBCore.DBAccessor;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;

@Service
@DependsOn({"DBManager"})
public class DBCoreCommServiceImpl implements DBCoreCommService {
	@Autowired
	@Qualifier("DBManager")
	private DBCoreSessionManager sessionManager;
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> executeNativeQuery(String queryString) {
		List<Map<String,Object>> resultList = null;
		boolean needClose=false;
		boolean needEnd=false;
		DBCoreSession currSession = DBCoreSessionManager.getCurrentSession();
		if (  currSession == null )
		{
			currSession=sessionManager.openSession(3, 100);
			currSession.beginTransaction(true);
			needClose=true;
			needEnd=true;
		}
		else 
			if (!currSession.isBeginTransaction() ) {
				currSession.beginTransaction(true);
				needEnd=true;
			}
		//execute Native Query
		Session session= currSession.getThisSession();
		try {
			SQLQuery query=session.createSQLQuery(queryString);
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			resultList = (List<Map<String, Object>>) query.list();
		}
		catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
		}
		
		if ( needEnd )
			currSession.endTransaction(false);
		if (needClose)
			sessionManager.closeSession(currSession);
	
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> executeNativeQuery(String queryString, Map<String, Object> params ) {
		List<Map<String,Object>> resultList = null;
		boolean needClose=false;
		boolean needEnd=false;
		DBCoreSession currSession = DBCoreSessionManager.getCurrentSession();
		if (  currSession == null )
		{
			currSession=sessionManager.openSession(3, 100);
			currSession.beginTransaction(true);
			needClose=true;
			needEnd=true;
		}
		else 
			if (!currSession.isBeginTransaction() ) {
				currSession.beginTransaction(true);
				needEnd=true;
			}
		//execute Native Query
		Session session= currSession.getThisSession();
		try {
			SQLQuery query=session.createSQLQuery(queryString);
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			resultList = (List<Map<String, Object>>) query.list();
		}
		catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
		}
		
		if ( needEnd )
			currSession.endTransaction(false);
		if (needClose)
			sessionManager.closeSession(currSession);
	
		return resultList;
	}
	
	public void executeUpdate(String queryString) {
		boolean needClose=false;
		boolean needEnd=false;
		DBCoreSession currSession = DBCoreSessionManager.getCurrentSession();
		if (  currSession == null )
		{
			currSession=sessionManager.openSession(3, 100);
			currSession.beginTransaction(true);
			needClose=true;
			needEnd=true;
		}
		else 
			if (!currSession.isBeginTransaction() ) {
				currSession.beginTransaction(true);
				needEnd=true;
			}
		//execute Native Query
		Session session= currSession.getThisSession();	
		try {
			SQLQuery query=session.createSQLQuery(queryString);
			query.executeUpdate();
			if (needEnd )
				currSession.endTransaction(true);
			
		}
		catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			if ( needEnd )
				currSession.endTransaction(false);
		}
		if (needClose)
			sessionManager.closeSession(currSession);
	}
	public void executeUpdate(String queryString, Map<String, Object> params) {
		boolean needClose=false;
		boolean needEnd=false;
		DBCoreSession currSession = DBCoreSessionManager.getCurrentSession();
		if (  currSession == null )
		{
			currSession=sessionManager.openSession(3, 100);
			currSession.beginTransaction(true);
			needClose=true;
			needEnd=true;
		}
		else 
			if (!currSession.isBeginTransaction() ) {
				currSession.beginTransaction(true);
				needEnd=true;
			}
		//execute Native Query
		Session session= currSession.getThisSession();	
		try {
			SQLQuery query=session.createSQLQuery(queryString);
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
			query.executeUpdate();
			if (needEnd )
				currSession.endTransaction(true);
			
		}
		catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			if ( needEnd )
				currSession.endTransaction(false);
		}
		if (needClose)
			sessionManager.closeSession(currSession);
	}
}
