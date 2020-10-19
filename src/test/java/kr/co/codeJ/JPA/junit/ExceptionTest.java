package kr.co.codeJ.JPA.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.TestCase;
import kr.co.codeJ.JPA.ComException.DBDuplicateException;
import kr.co.codeJ.JPA.ComException.DBException;
import kr.co.codeJ.JPA.ComException.DBNotFoundException;
import kr.co.codeJ.JPA.ComUtil.JPADevConfigClass;
import kr.co.codeJ.JPA.DBAccessor.DBSession;
import kr.co.codeJ.JPA.DBAccessor.DBSessionManager;
import kr.co.codeJ.JPA.DBAccessor.SessionStateEnum;
import kr.co.codeJ.JPA.model.factory.Factory;
import kr.co.codeJ.JPA.model.factory.FactoryDAO;
import kr.co.codeJ.JPA.model.factory.FactoryDTO;
/** 
* @author julu1 <julu1 @ naver.com >
* @version 1.0.0
* GenericDAO의  모든 API를 테스트 함.
*/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {JPADevConfigClass.class,Factory.class,FactoryDAO.class })
@TestPropertySource(locations="classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExceptionTest extends TestCase {
	
	@Autowired
	@Qualifier(value="DBManager")
	DBSessionManager myManager;
	
	@Autowired
	FactoryDAO	factoryDAO;
	
	DBSession currSession=null;
	SessionStateEnum sessionState=SessionStateEnum.INIT;
	
	@Before
	public void addFactory() {
		openSession(false);
		//--- << insert 부분 시작 >> ---
		FactoryDTO factory=new FactoryDTO();
		factory.setFactoryid("TestFactory");
		factory.setMemberCorpid("TestCorp");
		factory.setAddr1("Addr1");
		factory.setAddr2("Addr2");
		factory.setBizNo("02-222-3333");
		factoryDAO.insert(factory);
		closeSession(true);
	}
	
	@After
	public void deleteFactory() {
		openSession(false);
		
		Factory.Key factoryKey=new Factory.Key("TestCorp","TestFactory" );	//key entity 생성
		factoryDAO.delete(factoryKey);
		closeSession(true);
	}
	
	@Test(expected =DBDuplicateException.class) 
	public void Exception01_DBDuplcateException() {
		openSession(false);
		//--- << insert 부분 시작 >> ---
		FactoryDTO factory=new FactoryDTO();
		factory.setFactoryid("TestFactory");
		factory.setMemberCorpid("TestCorp");
		factory.setAddr1("Addr1");
		factory.setAddr2("Addr2");
		factory.setBizNo("02-222-3333");
		factoryDAO.insert(factory);
		closeSession(true);
	}
	
	@Test(expected = DBNotFoundException.class) 
	public void Exception02_DBNotFoundExceptionWhenGet() {
		openSession(true);
		Factory.Key key= new Factory.Key("TestCorp2","TestFactory2");
		FactoryDTO result=factoryDAO.get(key);
		closeSession(false);
	}
	
	@Test(expected = DBNotFoundException.class) 
	public void Exception02_DBNotFoundExceptionWhenUpdate() {
		openSession(false);
		Factory.Key key= new Factory.Key("TestCorp","TestFactory");
		FactoryDTO factory=factoryDAO.get(key);
		factory.setFctryNm("UpdateFactory");
		factory.setFactoryid("UpdateFactoryID");
		factoryDAO.update(factory);
		closeSession(true);
	}
	

	@Test(expected = DBNotFoundException.class) 
	public void Exception02_DBNotFoundExceptionWhenDelete() {
		openSession(false);
		Factory.Key key= new Factory.Key("TestCorp","TestFactory");
		FactoryDTO factory=factoryDAO.get(key);
		factory.setFctryNm("UpdateFactory");
		factory.setFactoryid("UpdateFactoryID");
		factoryDAO.delete(factory);
		closeSession(true);
	}
	
	
	public void openSession(boolean ReadOnly) {
		currSession = myManager.openSession();
		if (currSession == null )
			return;
		sessionState = SessionStateEnum.OPEN;
		try {
			sessionState=currSession.beginTransaction(ReadOnly);
		}
		catch ( DBException e) {
			throw e;
		}
	}

	public void closeSession(boolean Commit) {
		try {
			sessionState=currSession.endTransaction(Commit);
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
}
