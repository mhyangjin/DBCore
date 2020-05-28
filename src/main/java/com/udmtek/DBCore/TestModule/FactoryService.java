package com.udmtek.DBCore.TestModule;

import org.springframework.stereotype.Component;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;
import com.udmtek.DBCore.ComUtil.DBCoreTransactional;
import com.udmtek.DBCore.DBAccessor.DBCoreKey;
import com.udmtek.DBCore.DBAccessor.DBCoreService;
import com.udmtek.DBCore.DBAccessor.DBCoreSession;
import com.udmtek.model.Factory;
import com.udmtek.model.FactoryDAOImpl;
import com.udmtek.model.FactoryIdImpl;

@Component
public class FactoryService{

//	@DBCoreTransactional
	public void onlyReadService(String persistUnit) {
		// TODO Auto-generated method stub
		
	}

	@DBCoreTransactional
	public Factory saveService(DBCoreSession currSession,String argmemberCorpId, String argfactoryId ) {
		// TODO Auto-generated method stub

		DBCoreLogger.printInfo("memberCorpID:" + argmemberCorpId + " factoryId:" + argfactoryId);
		
		FactoryDAOImpl factoryImpl=currSession.getDAOImpl(FactoryDAOImpl.class);
		FactoryIdImpl fatoryKey=new FactoryIdImpl(argmemberCorpId,argfactoryId);
		Factory findFactory=factoryImpl.get((DBCoreKey)fatoryKey);
		findFactory.setBizNo("00000000");
		DBCoreLogger.printInfo("Factory Service - saveService");
		return findFactory;
	}
	
	
	
}
