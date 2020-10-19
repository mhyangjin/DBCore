package kr.co.codeJ.JPA.sampleCode;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import kr.co.codeJ.JPA.DBAccessor.DBReadTransactional;
import kr.co.codeJ.JPA.DBAccessor.DBTransactional;
import kr.co.codeJ.JPA.model.factory.Factory;
import kr.co.codeJ.JPA.model.factory.FactoryDAO;
import kr.co.codeJ.JPA.model.factory.FactoryDTO;


/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Component
public class FactoryService{
	private static Logger logger=LoggerFactory.getLogger(FactoryService.class);
	@Autowired
	FactoryDAO	factoryDAO;
	
	public FactoryService() {
	}
	
	//FactoryDAO 를 이용한 all select 기능
	@DBReadTransactional
	public List<FactoryDTO>  readFactory( ) {
		
		//--- << 조회 부분 시작 >> ---
		List<FactoryDTO> Factories = factoryDAO.getAll();
		// -- <<  조회 부분 끝  >> ---
		logger.info("findFactory {}", Factories.size());
		return Factories;
	}

	
	//FactoryDAO의 primary key를 이용한 1건 조회 기능
	@DBReadTransactional
	public FactoryDTO readFactoryWithKey(String argmemberCorpId,String argfactoryId ) {
		//--- << 조회 부분 시작 >> ---
		Factory.Key factoryKey=new Factory.Key(argmemberCorpId,argfactoryId );	//key entity 생성
		FactoryDTO findFactory=factoryDAO.get(factoryKey); //key entity를 이용한 조회
		// -- <<  조회 부분 끝  >> ---
		logger.info("findFactory {}",findFactory.toString());
		return findFactory;
	}

	//FactoryDAO의 update 기능 (1건)
	@DBTransactional
	public String updateFactoryWithKey (FactoryDTO myfactory) {
			//--- << update 부분 시작 >> ---
		factoryDAO.update( myfactory);					//이상없으면 저장
		// -- <<  update 부분 끝  >> ---
		String result="Update OK";
		return result;
		
	}
		
	//FactoryDAO의 delete 기능 (1건)
	@DBTransactional
	public String deleteFactoryWithKey (String argmemberCorpId,String argfactoryId) {
		//--- << delete 부분 시작 >> ---
		Factory.Key factoryKey=new Factory.Key(argmemberCorpId,argfactoryId );	//key entity 생성
		factoryDAO.delete( factoryKey);											//delete Call 
		// -- << delete 부분 끝  >> ---
		String result="Delete OK";
		return result;
	}
}
