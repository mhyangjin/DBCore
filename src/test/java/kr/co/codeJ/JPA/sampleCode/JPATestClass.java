package kr.co.codeJ.JPA.sampleCode;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import kr.co.codeJ.JPA.ComException.DBException;
import kr.co.codeJ.JPA.DBAccessor.DBCommService;
import kr.co.codeJ.JPA.DBAccessor.DBSession;
import kr.co.codeJ.JPA.DBAccessor.DBSessionManager;
import kr.co.codeJ.JPA.DBAccessor.SessionStateEnum;
import kr.co.codeJ.JPA.model.factory.Factory;
import kr.co.codeJ.JPA.model.factory.FactoryDAO;
import kr.co.codeJ.JPA.model.factory.FactoryDTO;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Component
@Scope(value = "prototype" )
public class JPATestClass {
	private static Logger logger=LoggerFactory.getLogger(JPATestClass.class);
//	
//	@Autowired
//	@Qualifier(value="DBManager")
//	DBSessionManager myManager;
//
//	@Autowired
//	ApplicationContext context;
//	
//	@Autowired
//	DBCommService commService;
//	
//	@Autowired
//	FactoryDAO factoryDAO;
//
//	public JPATestClass() {
//		logger.debug("..........DBCoreTestClass");
//
//	}
//	
//	//Session Pool test code. multi-thread로 동시 접속하여 생성함.
//	@Async
//	public void testDBCoreSesion() {
//		DBSession currSession=myManager.openSession(3,1000);
//		if (currSession == null )
//			return;
//		SessionStateEnum sessionState = SessionStateEnum.OPEN;
//		try  {
//			sessionState=currSession.beginTransaction(true);
//			logger.info("Thread Name[{}] Using :{}",Thread.currentThread().getName() ,currSession.getTransactionID());
//			
//			try {
//				//세션 임의 지연
//				Thread.sleep( 1000 );
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			logger.info("Thread Name[{}] release :{}",Thread.currentThread().getName() ,currSession.getTransactionID());
//		}
//		catch ( DBException e) {
//			throw e;
//		}
//		finally {
//			if ( sessionState == SessionStateEnum.BEGIN )
//				sessionState = currSession.endTransaction(false);
//			if ( sessionState == SessionStateEnum.OPEN)
//				 myManager.closeSession(currSession);
//		}
//		
//	}
////	//FactoryImpl 를 이용한 all select 기능
////	public List<FactorySub> readFactory() {
////		FactoryMapper factoryMapper = context.getBean(FactoryMapper.class);
////		
////		DBCoreSession currSession=myManager.openSession(3,1000);
////		if (currSession == null )
////			return null;
////		List<FactorySub> Factories = null;
////		SessionStateEnum sessionState = SessionStateEnum.OPEN;
////		try  {
////			sessionState=currSession.beginTransaction(true);
////			//--- << 조회 부분 시작 >> ---
////			FactoryImpl FactoryImpl=context.getBean(FactoryImpl.class);
////			Factories = FactoryImpl.getSubAll();
////			// -- <<  조회부분 끝  >> ---
////		}
////		catch ( DBException e) {
////			throw e;
////		}
////		finally {
////			if ( sessionState == SessionStateEnum.BEGIN )
////				sessionState = currSession.endTransaction(false);
////			if ( sessionState == SessionStateEnum.OPEN)
////				 myManager.closeSession(currSession);
////		}
////		return Factories;
////	}
////	
//	//FactoryDAO 를 이용한 all select 기능
//	public List<FactoryDTO> readFactory() {
//		DBSession currSession=myManager.openSession(3,1000);
//		if (currSession == null )
//			return null;
//		List<FactoryDTO> Factories = null;
//		SessionStateEnum sessionState = SessionStateEnum.OPEN;
//		try  {
//			sessionState=currSession.beginTransaction(true);
//			//--- << 조회 부분 시작 >> ---
//			Factories = factoryDAO.getAll();
//			// -- <<  조회부분 끝  >> ---
//		}
//		catch ( DBException e) {
//			throw e;
//		}
//		finally {
//			if ( sessionState == SessionStateEnum.BEGIN )
//				sessionState = currSession.endTransaction(false);
//			if ( sessionState == SessionStateEnum.OPEN)
//				 myManager.closeSession(currSession);
//		}
//		return Factories;
//	}
//	
//	//FactoryImpl의 primary key를 이용한 1건 조회 기능
//	public FactoryDTO readFactoryWithKey(String argmemberCorpId,String argfactoryId ) {
//		DBSession currSession=myManager.openSession(3,1000);
//		if (currSession == null )
//			return null;
//		FactoryDTO findFactory=null;
//		SessionStateEnum sessionState = SessionStateEnum.OPEN;
//		try {
//			sessionState=currSession.beginTransaction(true);
//			logger.info("memberCorpID:{}  factoryId:{}",argmemberCorpId,argfactoryId);
//			//--- << 조회 부분 시작 >> ---
//			Factory.Key factoryKey=new Factory.Key(argmemberCorpId,argfactoryId );		//key entity 생성
//			findFactory=factoryDAO.get(factoryKey); //key entity를 이용한 조회
//			
//			if ( findFactory == null)
//				logger.info(" not found!");
//			else
//				logger.info(findFactory.ToString());
//			// -- <<  조회 부분 끝  >> ---
//		}
//		catch ( DBException e) {
//			throw e;
//		}
//		finally {
//			if ( sessionState == SessionStateEnum.BEGIN )
//				sessionState = currSession.endTransaction(false);
//			if ( sessionState == SessionStateEnum.OPEN)
//				 myManager.closeSession(currSession);
//		}
//		return findFactory;
//	}
//
//	//FactoryImpl의 update 기능 (1건)
//	public String updateFactoryWithKey (FactoryDTO myfactory) {
//		DBSession currSession=null;
//		currSession = myManager.openSession(3,1000);
//		if (currSession == null )
//			return null;
//		SessionStateEnum sessionState = SessionStateEnum.OPEN;
//		try {
//			sessionState=currSession.beginTransaction(false);
//	
//			//--- << update 부분 시작 >> ---
//			factoryDAO.update( myfactory);					//이상없으면 저장
//			// -- <<  update 부분 끝  >> ---
//			sessionState=currSession.endTransaction(true);
//		}
//		catch ( DBException e) {
//			throw e;
//		}
//		finally {
//			if ( sessionState == SessionStateEnum.BEGIN )
//				sessionState = currSession.endTransaction(false);
//			if ( sessionState == SessionStateEnum.OPEN)
//				 myManager.closeSession(currSession);
//		}
//		String result="Update OK";
//		return result;
//	
//	}
//	
//	//FactoryImpl의 delete 기능 (1건)
//	public String deleteFactoryWithKey (String argmemberCorpId,String argfactoryId) {
//		DBSession currSession=myManager.openSession(3,1000);
//		if (currSession == null )
//			return null;
//		SessionStateEnum sessionState = SessionStateEnum.OPEN;
//		try {
//			sessionState=currSession.beginTransaction(false);
//			//--- << delete 부분 시작 >> ---
//			Factory.Key factoryKey=new Factory.Key(argmemberCorpId,argfactoryId );	
//			factoryDAO.delete( factoryKey);											//delete Call 
//			// -- << delete 부분 끝  >> ---
//			sessionState=currSession.endTransaction(true);
//		}
//		catch ( DBException e) {
//			throw e;
//		}
//		finally {
//			if ( sessionState == SessionStateEnum.BEGIN )
//				sessionState = currSession.endTransaction(false);
//			if ( sessionState == SessionStateEnum.OPEN)
//				 myManager.closeSession(currSession);
//		}
//		String result="Delete OK";
//		return result;
//		
//	}
//	
//	//FactoryImpl의 insert 기능 (1건)
//	public String insertFactory (FactoryDTO myfactory) {
//		DBSession currSession=null;
//		String result="insert OK";
//		
//		currSession = myManager.openSession(3,1000);
//		if (currSession == null )
//			return null;
//		SessionStateEnum sessionState = SessionStateEnum.OPEN;
//		try {
//				
//			sessionState=currSession.beginTransaction(false);
//			//--- << insert 부분 시작 >> ---
//			factoryDAO.insert( myfactory);
//			// -- <<  insert 부분 끝  >> ---
//			sessionState=currSession.endTransaction(true);
//		}
//		catch ( DBException e) {
//			throw e;
//		}
//		finally {
//			if ( sessionState == SessionStateEnum.BEGIN )
//				sessionState = currSession.endTransaction(false);
//			if ( sessionState == SessionStateEnum.OPEN)
//				 myManager.closeSession(currSession);
//		}
//		
//		return result;
//	
//	}
//	
//
//	//FactoryImpl의 native SQL. or JPQL 을 이횽한 select 기능
//	public List<FactoryDTO> readFactoryFromSQL(String Query, String QueryType) {
//		DBSession currSession=myManager.openSession(3,1000);
//		if (currSession == null )
//			return null;
//		List<FactoryDTO> Factories=null;
//		SessionStateEnum sessionState = SessionStateEnum.OPEN;
//		try {
//			sessionState=currSession.beginTransaction(true);
//			if (QueryType.equals("SQL")) { 
//				//SQL이면 native Query로 수행
//				//--- << 조회 부분 시작 >> ---
//				Factories=factoryDAO.getfromSQL(Query);
//				// -- <<  조회부분 끝  >> ---
//			}
//			if (QueryType.equals("JPQL")) { 
//				//JPQL이면 JPQL Query로 수행
//				//--- << 조회 부분 시작 >> ---
//				Factories=factoryDAO.getfromJPQL(Query);
//				// -- <<  조회부분 끝  >> ---
//			}
//		}
//		catch ( DBException e) {
//			throw e;
//		}
//		finally {
//			if ( sessionState == SessionStateEnum.BEGIN )
//				sessionState = currSession.endTransaction(false);
//			if ( sessionState == SessionStateEnum.OPEN)
//				 myManager.closeSession(currSession);
//		}
//		return Factories;
//	}
	// 

}
