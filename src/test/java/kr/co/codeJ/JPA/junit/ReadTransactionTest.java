package kr.co.codeJ.JPA.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import kr.co.codeJ.JPA.ComException.DBException;
import kr.co.codeJ.JPA.ComUtil.JPADevConfigClass;
import kr.co.codeJ.JPA.DBAccessor.DBLockMode;
import kr.co.codeJ.JPA.DBAccessor.DBSession;
import kr.co.codeJ.JPA.DBAccessor.DBSessionManager;
import kr.co.codeJ.JPA.DBAccessor.SessionStateEnum;
import kr.co.codeJ.JPA.model.factory.Factory;
import kr.co.codeJ.JPA.model.factory.FactoryDAO;
import kr.co.codeJ.JPA.model.factory.FactoryDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {JPADevConfigClass.class,Factory.class,FactoryDAO.class })
@TestPropertySource(locations="classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReadTransactionTest  {
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	@Qualifier(value="DBManager")
	DBSessionManager myManager;
	
	@Autowired
	FactoryDAO	factoryDAO;
	
	private boolean testRes=false;
	
	@Before
	public void addFactory() {
		DBSession currSession=null;
		currSession = myManager.openSession();
		if (currSession == null )
			return;
		SessionStateEnum sessionState = SessionStateEnum.OPEN;
		try {
				
			sessionState=currSession.beginTransaction(false);
			//--- << insert 부분 시작 >> ---

			FactoryDTO factory=new FactoryDTO();
			factory.setFactoryid("TestFactory");
			factory.setMemberCorpid("TestCorp");
			factory.setAddr1("Addr1");
			factory.setAddr2("Addr2");
			factory.setBizNo("02-222-3333");
			factoryDAO.insert(factory);
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
		
		
	}
	
	@After
	public void deleteFactory() {
		DBSession currSession=null;
		currSession = myManager.openSession();
		if (currSession == null )
			return;
		SessionStateEnum sessionState = SessionStateEnum.OPEN;
		try {
				
			sessionState=currSession.beginTransaction(false);
			//--- << insert 부분 시작 >> ---
			Factory.Key factoryKey=new Factory.Key("TestCorp","TestFactory" );	//key entity 생성
			factoryDAO.delete(factoryKey);
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
		
	}
	
	@Test
	public void ReadTest_1() {
		
		DBSession currSession=myManager.openSession();
		assertNotNull(currSession);
		List<FactoryDTO> Factories = null;
		SessionStateEnum sessionState = SessionStateEnum.OPEN;
		try  {
			sessionState=currSession.beginTransaction(true);
			//--- << 조회 부분 시작 >> ---
			Factory.Key factoryKey=new Factory.Key("TestCorp","TestFactory" );	//key entity 생성
			FactoryDTO findFactory=factoryDAO.get(factoryKey); //key entity를 이용한 조회
			assertNotNull(findFactory);
			assertEquals(findFactory.getFactoryid(), "TestFactory");
			assertEquals(findFactory.getMemberCorpid(),"TestCorp");
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
		
	}
	
	@Test
	public void ReadTest_2() {
		
		DBSession currSession=myManager.openSession();
		assertNotNull(currSession);
		List<FactoryDTO> Factories = null;
		SessionStateEnum sessionState = SessionStateEnum.OPEN;
		try  {
			sessionState=currSession.beginTransaction(true);
			//--- << 조회 부분 시작 >> ---
			Factory.Key factoryKey=new Factory.Key("TestCorp","TestFactory" );	//key entity 생성
			FactoryDTO findFactory=factoryDAO.get(factoryKey,DBLockMode.READLOCK); //key entity를 이용한 조회
			assertNotNull(findFactory);
			readTest();
			
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertTrue(testRes);
			testRes=false;
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
		
	}
	
	@Test
	public void ReadTest_3() {
		
		DBSession currSession=myManager.openSession();
		assertNotNull(currSession);
		List<FactoryDTO> Factories = null;
		SessionStateEnum sessionState = SessionStateEnum.OPEN;
		try  {
			sessionState=currSession.beginTransaction(true);
			//--- << 조회 부분 시작 >> ---
			Factory.Key factoryKey=new Factory.Key("TestCorp","TestFactory" );	//key entity 생성
			FactoryDTO findFactory=factoryDAO.get(factoryKey,DBLockMode.READLOCK); //key entity를 이용한 조회
			assertNotNull(findFactory);
			writeTest();
			
			try {
				Thread.currentThread().sleep(1000);
				System.out.println("Read:"+findFactory.getAddr1());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertFalse(testRes);
			testRes=false;
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
		
	}
	
	
	@Async
	private void readTest() {

		DBSession currSession=myManager.openSession();
		assertNotNull(currSession);
		List<FactoryDTO> Factories = null;
		SessionStateEnum sessionState = SessionStateEnum.OPEN;
		try  {
			sessionState=currSession.beginTransaction(true);
			//--- << 조회 부분 시작 >> ---
			Factory.Key factoryKey=new Factory.Key("TestCorp","TestFactory" );	//key entity 생성
			FactoryDTO findFactory=factoryDAO.get(factoryKey); //key entity를 이용한 조회
			if (findFactory != null )
				testRes=true;
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
	}
	
	@Async
	private void writeTest() {

		DBSession currSession=myManager.openSession();
		assertNotNull(currSession);
		List<FactoryDTO> Factories = null;
		SessionStateEnum sessionState = SessionStateEnum.OPEN;
		try  {
			sessionState=currSession.beginTransaction(false);
			//--- << 조회 부분 시작 >> ---
			Factory.Key factoryKey=new Factory.Key("TestCorp","TestFactory" );	//key entity 생성
			FactoryDTO findFactory=factoryDAO.get(factoryKey,DBLockMode.WRITELOCK); //key entity를 이용한 조회
			findFactory.setAddr1("readAddress");
			factoryDAO.update(findFactory);
			System.out.println("write:"+findFactory.getAddr1());
			sessionState=currSession.endTransaction(true);
			System.out.println("============================");
			
			testRes=true;
			// -- <<  조회부분 끝  >> ---
		}
		catch ( DBException e) {
			
			 e.printStackTrace();
			//throw e;
		}
		finally {
			if ( sessionState == SessionStateEnum.BEGIN )
				sessionState = currSession.endTransaction(false);
			if ( sessionState == SessionStateEnum.OPEN)
				 myManager.closeSession(currSession);
		}
	}
	
	
	
}
