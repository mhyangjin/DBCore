package com.udmtek.DBCore.TestModule;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;
import com.udmtek.DBCore.DBAccessor.DBCoreAccessManager;
import com.udmtek.DBCore.DBAccessor.DBCoreKey;
import com.udmtek.DBCore.DBAccessor.DBCoreService;
import com.udmtek.DBCore.DBAccessor.DBCoreSession;
import com.udmtek.DBCore.DBAccessor.DBCoreSessionManager;
import com.udmtek.model.Factory;
import com.udmtek.model.FactoryDAOImpl;
import com.udmtek.model.FactoryIdImpl;

@Component
public class DBCoreTestClass {
	DBCoreSessionManager myManager;
	@Autowired
	DBCoreAccessManager DBAccessor;
	@Autowired
	ApplicationContext context;
	
	public boolean ready() {
		myManager=DBAccessor.makeSessionManager("DBCoreJpa");
		myManager.printValues();
		return true;
	}
	
	@Async
	public void testDBCoreSesion() {
		DBCoreSession mysession=myManager.openSession(3,1000);
		if (mysession == null )
			return;
		mysession.beginTransaction(true);
		DBCoreLogger.printInfo("Thread Name[" + Thread.currentThread().getName() + "] Using :" + mysession.getTransactionID());
		try {
			Thread.sleep( 1000 );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mysession.endTransaction(false);
		DBCoreLogger.printInfo("Thread ID [" + Thread.currentThread().getName() + "] release :" + mysession.getTransactionID());
		myManager.closeSession(mysession);
		
	}
	
	public List<Factory> readFactory() {
		DBCoreSession mysession=myManager.openSession(3,1000);
		if (mysession == null )
			return null;
		
		mysession.beginTransaction(true);
		FactoryDAOImpl factoryImpl=mysession.getDAOImpl(FactoryDAOImpl.class);
		List<Factory> Factories = factoryImpl.getAll();
		
		mysession.endTransaction(false);
		myManager.closeSession(mysession);
		DBCoreLogger.printInfo("Factory read:" + Factories.size());
		return Factories;
	}
	
	public String readFactoryWithKey (String argmemberCorpId,String argfactoryId) {
		String result="";
		
		DBCoreSession mysession=myManager.openSession(3,1000);
		if (mysession == null )
			return null;
		mysession.beginTransaction(true);

		DBCoreLogger.printInfo("memberCorpID:" + argmemberCorpId + " factoryId:" + argfactoryId);
		
		FactoryDAOImpl factoryImpl=mysession.getDAOImpl(FactoryDAOImpl.class);
		FactoryIdImpl fatoryKey=new FactoryIdImpl(argmemberCorpId,argfactoryId);
		Factory findFactory=factoryImpl.get((DBCoreKey)fatoryKey);
		mysession.endTransaction(false);
		myManager.closeSession(mysession);
		
		if ( findFactory==null)
			result="Not found";
		else
			result="memberCorpID:" + findFactory.getMemberCorpid() 
			 		+ " factoryId:" + findFactory.getFactoryid()
			 		+ " factorytype:" + findFactory.getFactorytype()
			 		+ " siteid:" + findFactory.getSiteid();
			
		return result;
		
	}

	public String updateFactoryWithKey (String argmemberCorpId,String argfactoryId) {
		String result="";
		 DBCoreSession currSession=null;
		 currSession = myManager.openSession(3,1000);
			 
		FactoryService test=context.getBean(FactoryService.class);
		Factory findFactory= test.saveService(currSession,argmemberCorpId,argfactoryId);
		/*
		if (mysession == null )
			return null;
		mysession.beginTransaction(true);

		DBCoreLogger.printInfo("memberCorpID:" + argmemberCorpId + " factoryId:" + argfactoryId);
		
		FactoryDAOImpl factoryImpl=mysession.getDAOImpl(FactoryDAOImpl.class);
		FactoryIdImpl fatoryKey=new FactoryIdImpl(argmemberCorpId,argfactoryId);
		Factory findFactory=factoryImpl.get((DBCoreKey)fatoryKey);
		findFactory.setBizNo("111122222");
		mysession.endTransaction(true);
		myManager.closeSession(mysession);
		
		if ( findFactory==null)
			result="Not found";
		else
		{
			
			
		}
		*/
		myManager.closeSession(currSession);
		
		result="memberCorpID:" + findFactory.getMemberCorpid() 
 		+ " factoryId:" + findFactory.getFactoryid()
 		+ " factorytype:" + findFactory.getFactorytype()
 		+ " BizNo:" + findFactory.getBizNo()
 		+ " siteid:" + findFactory.getSiteid();
		return result;
		
	}
	public String deleteFactoryWithKey (String argmemberCorpId,String argfactoryId) {
		String result="";
		
		DBCoreSession mysession=myManager.openSession(3,1000);
		if (mysession == null )
			return null;
		DBCoreLogger.printInfo("memberCorpID:" + argmemberCorpId + " factoryId:" + argfactoryId);
		
		FactoryDAOImpl factoryImpl=mysession.getDAOImpl(FactoryDAOImpl.class);
		FactoryIdImpl fatoryKey=new FactoryIdImpl(argmemberCorpId,argfactoryId);
		
		Factory findFactory=factoryImpl.get((DBCoreKey)fatoryKey);
		
		if ( findFactory==null)
			result="Not found";
		else
			result="memberCorpID:" + findFactory.getMemberCorpid() 
			 		+ " factoryId:" + findFactory.getFactoryid()
			 		+ " factorytype:" + findFactory.getFactorytype()
			 		+ " siteid:" + findFactory.getSiteid();
			
		return result;
		
	}
}
