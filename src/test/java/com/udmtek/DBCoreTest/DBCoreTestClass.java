package com.udmtek.DBCoreTest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.udmtek.DBCore.ComException.DBException;
import com.udmtek.DBCore.ComUtil.DBCoreLogger;
import com.udmtek.DBCore.DBAccessor.DBCoreCommService;
import com.udmtek.DBCore.DBAccessor.DBCoreSession;
import com.udmtek.DBCore.DBAccessor.DBCoreSessionManager;
import com.udmtek.DBCore.DBAccessor.SessionStateEnum;
import com.udmtek.DBCore.model.factory.Factory;
import com.udmtek.DBCore.model.factory.FactoryDAO;
import com.udmtek.DBCore.model.factory.FactoryDTO;
import com.udmtek.DBCore.model.factory.FactoryMapper;

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
	
	@Autowired
	DBCoreCommService DBCommService;
	

	public DBCoreTestClass() {
		DBCoreLogger.printDebug("..........DBCoreTestClass");

	}
	
	//Session Pool test code. multi-thread로 동시 접속하여 생성함.
	@Async
	public void testDBCoreSesion() {
		DBCoreSession currSession=myManager.openSession(3,1000);
		if (currSession == null )
			return;
		SessionStateEnum sessionState = SessionStateEnum.OPEN;
		try  {
			sessionState=currSession.beginTransaction(true);
			DBCoreLogger.printInfo("Thread Name[" + Thread.currentThread().getName() 
									+ "] Using :" + currSession.getTransactionID());
			
			try {
				//세션 임의 지연
				Thread.sleep( 1000 );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			DBCoreLogger.printInfo("Thread Name [" + Thread.currentThread().getName() 
								+ "] release :" + currSession.getTransactionID());
		}
		catch ( DBException e) {
			throw e;
		}
		finally {
			if ( sessionState == SessionStateEnum.BEGIN )
				sessionState = currSession.endTransaction(false);
			if ( sessionState == SessionStateEnum.OPEN)
				 myManager.closeSession(currSession);
		}
		
	}

	
	//FactoryDAO 를 이용한 all select 기능
	public List<FactoryDTO> readFactory() {
		
		FactoryMapper factoryMapper = context.getBean(FactoryMapper.class);
		
		DBCoreSession currSession=myManager.openSession(3,1000);
		if (currSession == null )
			return null;
		List<FactoryDTO> Factories = null;
		SessionStateEnum sessionState = SessionStateEnum.OPEN;
		try  {
			sessionState=currSession.beginTransaction(true);
			//--- << 조회 부분 시작 >> ---
			FactoryDAO factoryDao=context.getBean(FactoryDAO.class);
			Factories = factoryDao.getAll();
			// -- <<  조회부분 끝  >> ---
		}
		catch ( DBException e) {
			throw e;
		}
		finally {
			if ( sessionState == SessionStateEnum.BEGIN )
				sessionState = currSession.endTransaction(false);
			if ( sessionState == SessionStateEnum.OPEN)
				 myManager.closeSession(currSession);
		}
		
		
		return Factories;

	}
	
	//FactoryDAO의 primary key를 이용한 1건 조회 기능
	public FactoryDTO readFactoryWithKey(String argmemberCorpId,String argfactoryId ) {
		DBCoreSession currSession=myManager.openSession(3,1000);
		if (currSession == null )
			return null;
		FactoryDTO findFactory=null;
		SessionStateEnum sessionState = SessionStateEnum.OPEN;
		try {
			sessionState=currSession.beginTransaction(true);
			DBCoreLogger.printInfo("memberCorpID:" + argmemberCorpId + " factoryId:" + argfactoryId);
			//--- << 조회 부분 시작 >> ---
			FactoryDAO factoryDao=context.getBean(FactoryDAO.class);
			Factory.Key factoryKey=new Factory.Key(argmemberCorpId,argfactoryId );		//key entity 생성
			findFactory=factoryDao.get(factoryKey); //key entity를 이용한 조회
			
			if ( findFactory == null)
				DBCoreLogger.printInfo(" not found!");
			else
				DBCoreLogger.printInfo(findFactory.ToString());
			// -- <<  조회 부분 끝  >> ---
		}
		catch ( DBException e) {
			throw e;
		}
		finally {
			if ( sessionState == SessionStateEnum.BEGIN )
				sessionState = currSession.endTransaction(false);
			if ( sessionState == SessionStateEnum.OPEN)
				 myManager.closeSession(currSession);
		}
		return findFactory;
	}

	//FactoryDAO의 update 기능 (1건)
	public String updateFactoryWithKey (FactoryDTO myfactory) {
		DBCoreSession currSession=null;
		currSession = myManager.openSession(3,1000);
		if (currSession == null )
			return null;
		SessionStateEnum sessionState = SessionStateEnum.OPEN;
		try {
			sessionState=currSession.beginTransaction(false);
	
			//--- << update 부분 시작 >> ---
			FactoryDAO factoryDao=context.getBean(FactoryDAO.class);
			myfactory.setLasteventperson("mhjin");
			myfactory.setUseyn("teset");
			factoryDao.update( myfactory);					//이상없으면 저장
			// -- <<  update 부분 끝  >> ---
			sessionState=currSession.endTransaction(true);
		}
		catch ( DBException e) {
			throw e;
		}
		finally {
			if ( sessionState == SessionStateEnum.BEGIN )
				sessionState = currSession.endTransaction(false);
			if ( sessionState == SessionStateEnum.OPEN)
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
		SessionStateEnum sessionState = SessionStateEnum.OPEN;
		try {
			sessionState=currSession.beginTransaction(false);
			//--- << delete 부분 시작 >> ---
			FactoryDAO factoryDao=context.getBean(FactoryDAO.class);
			Factory.Key factoryKey=new Factory.Key(argmemberCorpId,argfactoryId );	
			factoryDao.delete( factoryKey);											//delete Call 
			// -- << delete 부분 끝  >> ---
			sessionState=currSession.endTransaction(true);
		}
		catch ( DBException e) {
			throw e;
		}
		finally {
			if ( sessionState == SessionStateEnum.BEGIN )
				sessionState = currSession.endTransaction(false);
			if ( sessionState == SessionStateEnum.OPEN)
				 myManager.closeSession(currSession);
		}
		String result="Delete OK";
		return result;
		
	}
	
	//FactoryDAO의 insert 기능 (1건)
	public String insertFactory (FactoryDTO myfactory) {
		DBCoreSession currSession=null;
		String result="insert OK";
		
		currSession = myManager.openSession(3,1000);
		if (currSession == null )
			return null;
		SessionStateEnum sessionState = SessionStateEnum.OPEN;
		try {
				
			sessionState=currSession.beginTransaction(false);
			//--- << insert 부분 시작 >> ---
			FactoryDAO factoryDao=context.getBean(FactoryDAO.class);
			factoryDao.insert( myfactory);
			// -- <<  insert 부분 끝  >> ---
			sessionState=currSession.endTransaction(true);
		}
		catch ( DBException e) {
			throw e;
		}
		finally {
			if ( sessionState == SessionStateEnum.BEGIN )
				sessionState = currSession.endTransaction(false);
			if ( sessionState == SessionStateEnum.OPEN)
				 myManager.closeSession(currSession);
		}
		
		return result;
	
	}
	

	//FactoryDAO의 native SQL. or JPQL 을 이횽한 select 기능
	public List<FactoryDTO> readFactoryFromSQL(String Query, String QueryType) {
		DBCoreSession currSession=myManager.openSession(3,1000);
		if (currSession == null )
			return null;
		List<FactoryDTO> Factories=null;
		SessionStateEnum sessionState = SessionStateEnum.OPEN;
		try {
			sessionState=currSession.beginTransaction(true);
			if (QueryType.equals("SQL")) { 
				//SQL이면 native Query로 수행
				//--- << 조회 부분 시작 >> ---
				FactoryDAO factoryDao=context.getBean(FactoryDAO.class);
				Factories=factoryDao.getfromSQL(Query);
				// -- <<  조회부분 끝  >> ---
			}
			if (QueryType.equals("JPQL")) { 
				//JPQL이면 JPQL Query로 수행
				//--- << 조회 부분 시작 >> ---
				FactoryDAO factoryDao=context.getBean(FactoryDAO.class);
				Factories=factoryDao.getfromJPQL(Query);
				// -- <<  조회부분 끝  >> ---
			}
		}
		catch ( DBException e) {
			throw e;
		}
		finally {
			if ( sessionState == SessionStateEnum.BEGIN )
				sessionState = currSession.endTransaction(false);
			if ( sessionState == SessionStateEnum.OPEN)
				 myManager.closeSession(currSession);
		}
		return Factories;
	}
	// 

}
