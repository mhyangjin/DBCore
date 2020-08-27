package com.udmtek.DBCoreTest;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.udmtek.DBCore.DBAccessor.DBCoreReadTransactional;
import com.udmtek.DBCore.DBAccessor.DBCoreTransactional;
import com.udmtek.DBCore.model.factory.Factory;
import com.udmtek.DBCore.model.factory.FactoryDAO;
import com.udmtek.DBCore.model.factory.FactoryDTO;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Component
public class FactoryService{
	private static Logger logger=LoggerFactory.getLogger(FactoryService.class);
	
	@Autowired
	ApplicationContext context;
	
	public FactoryService() {
	}
	
	//FactoryDAO 를 이용한 all select 기능
	@DBCoreReadTransactional
	public List<FactoryDTO>  readFactory( ) {
		
		//--- << 조회 부분 시작 >> ---
		FactoryDAO factoryDao=context.getBean(FactoryDAO.class);
		List<FactoryDTO> Factories = factoryDao.getAll();
		// -- <<  조회 부분 끝  >> ---
		logger.info("findFactory {}", Factories.size());
		return Factories;
	}

	
	//FactoryDAO의 primary key를 이용한 1건 조회 기능
	@DBCoreReadTransactional("default")
	public FactoryDTO readFactoryWithKey(String argmemberCorpId,String argfactoryId ) {
		//--- << 조회 부분 시작 >> ---
		FactoryDAO factoryDao=context.getBean(FactoryDAO.class);
		Factory.Key factoryKey=new Factory.Key(argmemberCorpId,argfactoryId );	//key entity 생성
		FactoryDTO findFactory=factoryDao.get(factoryKey); //key entity를 이용한 조회
		// -- <<  조회 부분 끝  >> ---
		logger.info("findFactory {}",findFactory.toString());
		return findFactory;
	}

	//FactoryDAO의 update 기능 (1건)
	@DBCoreTransactional( "default")
	public String updateFactoryWithKey (FactoryDTO myfactory) {
			//--- << update 부분 시작 >> ---
		FactoryDAO factoryDao=context.getBean(FactoryDAO.class);
		factoryDao.update( myfactory);					//이상없으면 저장
		// -- <<  update 부분 끝  >> ---
		String result="Update OK";
		return result;
		
	}
		
	//FactoryDAO의 delete 기능 (1건)
	@DBCoreTransactional("default")
	public String deleteFactoryWithKey (String argmemberCorpId,String argfactoryId) {
		//--- << delete 부분 시작 >> ---
		FactoryDAO factoryDao=context.getBean(FactoryDAO.class);
		Factory.Key factoryKey=new Factory.Key(argmemberCorpId,argfactoryId );	//key entity 생성
		factoryDao.delete( factoryKey);											//delete Call 
		// -- << delete 부분 끝  >> ---
		String result="Delete OK";
		return result;
	}
}
