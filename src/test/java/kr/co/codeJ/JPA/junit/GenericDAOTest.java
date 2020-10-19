package kr.co.codeJ.JPA.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import kr.co.codeJ.JPA.ComException.DBException;
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
public class GenericDAOTest {
	@Autowired
	private ApplicationContext context;
	
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
		String sqlquery="delete from factory where factoryid like 'TestFactory%'";
		factoryDAO.delete(sqlquery);
		closeSession(true);
	}
	
	@Test
	public void Generic01_getWithKey() {
		openSession(true);
		Factory.Key key= new Factory.Key("TestCorp","TestFactory");
		FactoryDTO result=factoryDAO.get(key);
		assertNotNull(result);
		closeSession(false);
	}
	
	@Test
	public void Generic03_getAll(){
		openSession(true);
		List<FactoryDTO> results=factoryDAO.getAll();
		assertTrue(results.size()>0?true:false);
		closeSession(false);
	}
	
	@Test
	public void Generic04_getfromJPQL(){
		String jpqlString = "select f from FactoryDAO f";
		openSession(true);
		List<FactoryDTO> results=factoryDAO.getfromJPQL(jpqlString);
		assertTrue(results.size()>0?true:false);
		closeSession(false);
	}
	
	@Test
	public void Generic05_getfromSQL(){
		String sqlString = "select * from factory";
		openSession(true);
		List<FactoryDTO> results=factoryDAO.getfromSQL(sqlString);
		assertTrue(results.size()>0?true:false);
		closeSession(false);
	}
	
	@Test
	public void Generic06_insert(){
		openSession(false);
		//--- << insert 부분 시작 >> ---
		FactoryDTO factory1=new FactoryDTO();
		factory1.setFactoryid("TestFactory1");
		factory1.setMemberCorpid("TestCorp1");
		factory1.setAddr1("Addr1");
		factory1.setAddr2("Addr2");
		factory1.setBizNo("02-222-3333");
		factoryDAO.insert(factory1);
		closeSession(true);
	}
	
	@Test
	public void Generic07_update(){
		openSession(false);
		Factory.Key key= new Factory.Key("TestCorp","TestFactory");
		FactoryDTO fatcory=factoryDAO.get(key);
		fatcory.setFctryNm("UpdateFactory");
		factoryDAO.update(fatcory);
		currSession.endTransaction(true);
		currSession.beginTransaction(false);
		FactoryDTO updatefatcory=factoryDAO.get(key);
		assertEquals(updatefatcory.getFctryNm(),"UpdateFactory");
		closeSession(true);
	}
	
	@Test
	public void Generic08_deleteWithObject(){
		openSession(false);
		Factory.Key key= new Factory.Key("TestCorp","TestFactory");
		FactoryDTO fatcory=factoryDAO.get(key);
		factoryDAO.delete(fatcory);
		currSession.endTransaction(true);
		currSession.beginTransaction(false);
		
		String sqlQuery =" select * from factory where factoryid='TestFactory'";
		List<FactoryDTO> deletefatcory=factoryDAO.getfromSQL(sqlQuery);
		assertTrue(deletefatcory.size()==0?true:false);
		closeSession(false);
	}
	
	@Test
	public void Generic09_deleteWithKey(){
		openSession(false);
		Factory.Key key= new Factory.Key("TestCorp","TestFactory");
		factoryDAO.delete(key);
		currSession.endTransaction(true);
		currSession.beginTransaction(false);
		String sqlQuery =" select * from factory where factoryid='TestFactory'";
		List<FactoryDTO> deletefatcory=factoryDAO.getfromSQL(sqlQuery);
		assertTrue(deletefatcory.size()==0?true:false);
		closeSession(false);
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
