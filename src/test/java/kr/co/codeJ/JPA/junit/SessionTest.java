package kr.co.codeJ.JPA.junit;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.co.codeJ.JPA.ComUtil.JPADevConfigClass;
import kr.co.codeJ.JPA.DBAccessor.DBSession;
import kr.co.codeJ.JPA.DBAccessor.DBSessionManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=JPADevConfigClass.class )
@TestPropertySource(locations="classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SessionTest  {
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	@Qualifier(value="DBManager")
	DBSessionManager myManager;
	
	@Test
	public void SessionTest_2() {
		
		DBSession currSession=myManager.openSession();
		assertEquals(myManager.getOpenSessionNo(),	1);
		myManager.closeSession(currSession);
	}
	
	@Test
	public void SessionTest_3() {
		
		DBSession currSession=myManager.openSession();
		myManager.closeSession(currSession);
		assertEquals(myManager.getOpenSessionNo(),0);
	}
	
	@Test
	public void SessionTest_4() {
		List<DBSession> currSessions=createMaxSessions();
		assertEquals(myManager.getOpenSessionNo(),myManager.getMaxPoolSize());
		closeAllSessions(currSessions);
	}
	
	@Test
	public void SessionTest_5() {
		List<DBSession> currSessions=createMaxSessions();
		DBSession addSession=myManager.openSession();
		assertNull(addSession);
		closeAllSessions(currSessions);
	}
	
	@Test
	public void SessionTest_6() {
		List<DBSession> currSessions=createMaxSessions();
		closeOneSession(currSessions.get(0));
		currSessions.remove(0);
		DBSession addSession=myManager.openSession(5, 10);
		assertNotNull(addSession);
		currSessions.add(addSession);
		
		closeAllSessions(currSessions);
	}
	
	@Test
	public void SessionTest_7() {
		List<DBSession> currSessions=createMaxSessions();
		closeAllSessions(currSessions);
		assertEquals(myManager.getOpenSessionNo(),0);
	}
	
	private List<DBSession> createMaxSessions() {
		List<DBSession> currSessions=new ArrayList<>();
		for(int i=0; i<myManager.getMaxPoolSize(); i++ ) {
			currSessions.add(myManager.openSession());
		}
		return currSessions;
	}
	
	@Async
	private void closeOneSession(DBSession oneSession) {
		try {
			Thread.currentThread().sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myManager.closeSession(oneSession);
	}
	private void closeAllSessions(List<DBSession> sessions) {
		for(DBSession currSession:sessions) {
			myManager.closeSession(currSession);
		}
	}
	
}
