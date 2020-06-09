package com.udmtek.DBCore.TestModule;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;
import com.udmtek.DBCore.DBAccessor.DBCoreReadTransactional;
import com.udmtek.DBCore.DBAccessor.DBCoreTransactional;
import com.udmtek.DBCore.model.FactoryIdDAO;
import com.udmtek.DBCore.model.FactoryImpl;
import com.udmtek.DBCore.model.FactoryInfo;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Component
public class FactoryService{
	@Autowired
	ApplicationContext context;
	
	//FactoryDAO 를 이용한 all select 기능
	@DBCoreReadTransactional(PersistUnit = "myLocalDB")
	public List<FactoryInfo>  readFactory( ) {
		//--- << 조회 부분 시작 >> ---
		FactoryImpl factoryImpl=context.getBean(FactoryImpl.class);
		List<FactoryInfo> Factories = factoryImpl.getAll();
		// -- <<  조회 부분 끝  >> ---
		DBCoreLogger.printInfo("findFactory" + Factories.size());
		return Factories;
	}

	
	//FactoryDAO의 primary key를 이용한 1건 조회 기능
	@DBCoreReadTransactional(PersistUnit = "myLocalDB")
	public FactoryInfo readFactoryWithKey(String argmemberCorpId,String argfactoryId ) {
		//--- << 조회 부분 시작 >> ---
		FactoryImpl factoryImpl=context.getBean(FactoryImpl.class);
		FactoryIdDAO factoryKey=new FactoryIdDAO(argmemberCorpId,argfactoryId );		//key entity 생성
		FactoryInfo findFactory=(FactoryInfo)factoryImpl.get((Serializable)factoryKey); //key entity를 이용한 조회
		// -- <<  조회 부분 끝  >> ---
		DBCoreLogger.printInfo("findFactory" + findFactory.getFactoryid());
		return findFactory;
	}

	//FactoryDAO의 update 기능 (1건)
	@DBCoreTransactional(PersistUnit = "myLocalDB")
	public String updateFactoryWithKey (FactoryInfo myfactory) {
			//--- << update 부분 시작 >> ---
		FactoryImpl factoryImpl=context.getBean(FactoryImpl.class);
		factoryImpl.save( myfactory);					//이상없으면 저장
		// -- <<  update 부분 끝  >> ---
		String result="Update OK";
		return result;
		
	}
		
	//FactoryDAO의 delete 기능 (1건)
	@DBCoreTransactional(PersistUnit = "myLocalDB")
	public String deleteFactoryWithKey (String argmemberCorpId,String argfactoryId) {
		//--- << delete 부분 시작 >> ---
		FactoryImpl factoryImpl=context.getBean(FactoryImpl.class);
		FactoryIdDAO factoryKey=new FactoryIdDAO(argmemberCorpId,argfactoryId );			//key entity 생성
		factoryImpl.delete( factoryKey);											//delete Call 
		// -- << delete 부분 끝  >> ---
		String result="Delete OK";
		return result;
	}
}
