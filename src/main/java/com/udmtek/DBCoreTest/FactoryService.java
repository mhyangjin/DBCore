package com.udmtek.DBCoreTest;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;
import com.udmtek.DBCore.DBAccessor.DBCoreReadTransactional;
import com.udmtek.DBCore.DBAccessor.DBCoreTransactional;
import com.udmtek.DBCoreTest.model.FactoryDAO;
import com.udmtek.DBCoreTest.model.FactoryImpl;
import com.udmtek.DBCoreTest.model.PersonDAO;
import com.udmtek.DBCoreTest.model.PersonInfo;
import com.udmtek.DBCoreTest.model.PersonInfoImpl;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Component
public class FactoryService{
	@Autowired
	ApplicationContext context;
	
	//FactoryDAO 를 이용한 all select 기능
	@DBCoreReadTransactional
	public List<FactoryDAO>  readFactory( ) {

		String memberCorpid="DAEIL";
		String factoryidDft = "DAEIL";
		String personid="ADMIN";
		PersonInfoImpl personImpl= context.getBean(PersonInfoImpl.class);
		PersonDAO.Key Key=new PersonDAO.Key(memberCorpid,factoryidDft,personid );	//key entity 생성
		PersonInfo findPerson=personImpl.get(Key); //key entity를 이용한 조회
		DBCoreLogger.printInfo(findPerson.toString());
		
		//--- << 조회 부분 시작 >> ---
		FactoryImpl factoryImpl=context.getBean(FactoryImpl.class);
		List<FactoryDAO> Factories = factoryImpl.getAll();
		// -- <<  조회 부분 끝  >> ---
		DBCoreLogger.printInfo("findFactory" + Factories.size());
		return Factories;
	}

	
	//FactoryDAO의 primary key를 이용한 1건 조회 기능
	@DBCoreReadTransactional("default")
	public FactoryDAO readFactoryWithKey(String argmemberCorpId,String argfactoryId ) {
		//--- << 조회 부분 시작 >> ---
		FactoryImpl factoryImpl=context.getBean(FactoryImpl.class);
		FactoryDAO.Key factoryKey=new FactoryDAO.Key(argmemberCorpId,argfactoryId );	//key entity 생성
		FactoryDAO findFactory=factoryImpl.get((Serializable)factoryKey); //key entity를 이용한 조회
		// -- <<  조회 부분 끝  >> ---
		DBCoreLogger.printInfo("findFactory" + findFactory.toString());
		return findFactory;
	}

	//FactoryDAO의 update 기능 (1건)
	@DBCoreTransactional( "default")
	public String updateFactoryWithKey (FactoryDAO myfactory) {
			//--- << update 부분 시작 >> ---
		FactoryImpl factoryImpl=context.getBean(FactoryImpl.class);
		factoryImpl.save( myfactory);					//이상없으면 저장
		// -- <<  update 부분 끝  >> ---
		String result="Update OK";
		return result;
		
	}
		
	//FactoryDAO의 delete 기능 (1건)
	@DBCoreTransactional("default")
	public String deleteFactoryWithKey (String argmemberCorpId,String argfactoryId) {
		//--- << delete 부분 시작 >> ---
		FactoryImpl factoryImpl=context.getBean(FactoryImpl.class);
		FactoryDAO.Key factoryKey=new FactoryDAO.Key(argmemberCorpId,argfactoryId );	//key entity 생성
		factoryImpl.delete( factoryKey);											//delete Call 
		// -- << delete 부분 끝  >> ---
		String result="Delete OK";
		return result;
	}
}
