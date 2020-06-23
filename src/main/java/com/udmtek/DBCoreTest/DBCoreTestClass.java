package com.udmtek.DBCoreTest;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.udmtek.DBCore.ComUtil.DBCoreLogger;
import com.udmtek.DBCore.DBAccessor.DBCoreAccessManager;
import com.udmtek.DBCore.DBAccessor.DBCoreSession;
import com.udmtek.DBCore.DBAccessor.DBCoreSessionManager;
import com.udmtek.DBCoreTest.model.FactoryDAO;
import com.udmtek.DBCoreTest.model.FactoryImpl;
import com.udmtek.DBCoreTest.model.FactoryInfo;
import com.udmtek.DBCoreTest.model.FactoryInfoImpl;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Component
@Scope(value = "prototype" )
public class DBCoreTestClass {
	@Autowired
	@Qualifier(value="DBManager")
	DBCoreSessionManager myManager;

	@Autowired
	ApplicationContext context;
	
	public DBCoreTestClass() {
	}
	
	//Session Pool test code. multi-thread로 동시 접속하여 생성함.
	@Async
	public void testDBCoreSesion() {
		DBCoreSession currSession=myManager.openSession(3,1000);
		if (currSession == null )
			return;
		boolean BeginOK=false;
		try  {
			currSession.beginTransaction(true);
			BeginOK=true;
			DBCoreLogger.printInfo("Thread Name[" + Thread.currentThread().getName() 
									+ "] Using :" + currSession.getTransactionID());
			
			try {
				//세션 임의 지연
				Thread.sleep( 1000 );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			currSession.endTransaction(false);
			DBCoreLogger.printInfo("Thread Name [" + Thread.currentThread().getName() 
								+ "] release :" + currSession.getTransactionID());
		}
		catch ( Exception e) {
			//Exception 처리
			if (BeginOK)
				currSession.endTransaction(false);
		}
		finally {
			myManager.closeSession(currSession);
		}
		
	}

	
	//FactoryDAO 를 이용한 all select 기능
	public List<FactoryInfo> readFactory() {
		
		
		DBCoreSession currSession=myManager.openSession(3,1000);
		if (currSession == null )
			return null;
		List<FactoryInfo> Factories = null;
		boolean BeginOK=false;
		
		try  {
			BeginOK=currSession.beginTransaction(true);
			//--- << 조회 부분 시작 >> ---
			FactoryInfoImpl factoryImpl=context.getBean(FactoryInfoImpl.class);
			Factories = factoryImpl.getAll();
			// -- <<  조회부분 끝  >> ---
			
			currSession.endTransaction(false);
		}
		catch ( Exception e) {
			e.printStackTrace();
			//Exception 처리
			if ( BeginOK)
				currSession.endTransaction(false);
		}
		finally {
			myManager.closeSession(currSession);
		}
		
		
		return Factories;

	}
	
	//FactoryDAO의 primary key를 이용한 1건 조회 기능
		public FactoryDAO readFactoryWithKey(String argmemberCorpId,String argfactoryId ) {
			DBCoreSession currSession=myManager.openSession(3,1000);
			if (currSession == null )
				return null;
			FactoryDAO findFactory=null;
			boolean BeginOK=false;
			try {
				BeginOK=currSession.beginTransaction(true);
				DBCoreLogger.printInfo("memberCorpID:" + argmemberCorpId + " factoryId:" + argfactoryId);
				//--- << 조회 부분 시작 >> ---
				FactoryImpl factoryImpl=context.getBean(FactoryImpl.class);
				FactoryDAO.Key factoryKey=new FactoryDAO.Key(argmemberCorpId,argfactoryId );		//key entity 생성
				findFactory=factoryImpl.get((Serializable)factoryKey); //key entity를 이용한 조회
				
				if ( findFactory == null)
					DBCoreLogger.printInfo(" not found!");
				else
					DBCoreLogger.printInfo(findFactory.toString());
				// -- <<  조회 부분 끝  >> ---

				
				currSession.endTransaction(false);
			}
			catch ( Exception e) {
				//Exception 처리
				e.printStackTrace();
				if ( BeginOK)
					currSession.endTransaction(false);
			}
			finally {
				myManager.closeSession(currSession);
			}
			return findFactory;
		}

	//FactoryDAO의 update 기능 (1건)
	public String updateFactoryWithKey (FactoryDAO myfactory) {
		DBCoreSession currSession=null;
		currSession = myManager.openSession(3,1000);
		if (currSession == null )
			return null;
		boolean BeginOK=false;
		try {
			BeginOK=currSession.beginTransaction(false);
	
			//--- << update 부분 시작 >> ---
			FactoryImpl factoryImpl=context.getBean(FactoryImpl.class);
			factoryImpl.save( myfactory);					//이상없으면 저장
			// -- <<  update 부분 끝  >> ---
			
			currSession.endTransaction(true);
		}
		catch ( Exception e) {
			if ( BeginOK )
				currSession.endTransaction(false); //Rollback			
		}
		finally {
			myManager.closeSession(currSession);
		}
		
		String result="Update OK";
		return result;
	
	}
	
	//FactoryDAO의 delete 기능 (1건)
	public String deleteFactoryWithKey (String argmemberCorpId,String argfactoryId) {
		DBCoreSession currSession=myManager.openSession(3,1000);
		if (currSession == null )
			return null;
		boolean BeginOK=false;
		try {
				
			BeginOK=currSession.beginTransaction(false);
			//--- << delete 부분 시작 >> ---
			FactoryImpl factoryImpl=context.getBean(FactoryImpl.class);
			FactoryDAO.Key factoryKey=new FactoryDAO.Key(argmemberCorpId,argfactoryId );	
			factoryImpl.delete( factoryKey);											//delete Call 
			// -- << delete 부분 끝  >> ---
			
			currSession.endTransaction(true);
		}
		catch ( Exception e) {
			if ( BeginOK )
				currSession.endTransaction(false); //Rollback			
		}
		finally {
			myManager.closeSession(currSession);
		}
		String result="Delete OK";
		return result;
		
	}
	
	//FactoryDAO의 insert 기능 (1건)
	public String insertFactory (FactoryDAO myfactory) {
		DBCoreSession currSession=null;
		String result="insert OK";
		
		currSession = myManager.openSession(3,1000);
		if (currSession == null )
			return null;
		
		boolean BeginOK=false;
		try {
				
			BeginOK=currSession.beginTransaction(false);
			//--- << insert 부분 시작 >> ---
			FactoryImpl factoryImpl=context.getBean(FactoryImpl.class);
			factoryImpl.insert( myfactory);
			// -- <<  insert 부분 끝  >> ---
			currSession.endTransaction(true);
		}
		catch ( Exception e) {
			if ( BeginOK )
				currSession.endTransaction(false); //Rollback	
			result="insert fail";
		}
		finally {
			myManager.closeSession(currSession);
		}
		
		return result;
	
	}
	

	//FactoryDAO의 native SQL. or JPQL 을 이횽한 select 기능
	public List<FactoryDAO> readFactoryFromSQL(String Query, String QueryType) {
		DBCoreSession currSession=myManager.openSession(3,1000);
		if (currSession == null )
			return null;
		List<FactoryDAO> Factories=null;
		boolean BeginOK=false;
		try {
			BeginOK=currSession.beginTransaction(true);
			if (QueryType.equals("SQL")) { 
				//SQL이면 native Query로 수행
				//--- << 조회 부분 시작 >> ---
				FactoryImpl factoryImpl=context.getBean(FactoryImpl.class);
				Factories=factoryImpl.getfromSQL(Query);
				// -- <<  조회부분 끝  >> ---
			}
			if (QueryType.equals("JPQL")) { 
				//JPQL이면 JPQL Query로 수행
				//--- << 조회 부분 시작 >> ---
				FactoryImpl factoryImpl=context.getBean(FactoryImpl.class);
				Factories=factoryImpl.getfromJPQL(Query);
				// -- <<  조회부분 끝  >> ---
			}
			
			currSession.endTransaction(false);
		}
		catch (Exception e  ) {
			//Exception 처리
			if ( BeginOK )
				currSession.endTransaction(false);
		}
		finally {
			myManager.closeSession(currSession);
		}
		return Factories;
	}
	// 

}
