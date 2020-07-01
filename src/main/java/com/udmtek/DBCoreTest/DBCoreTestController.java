package com.udmtek.DBCoreTest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;
import com.udmtek.DBCore.model.factory.Factory;
import com.udmtek.DBCore.model.factory.FactoryDTO;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Controller
public class DBCoreTestController {
	@Autowired
	ApplicationContext context;

	
	@RequestMapping(value="/")
	public String testGuid() {

		DBCoreLogger.printInfo("testGuid");
		return "main";
		
	}

	@RequestMapping(value="SessionPoolTestForm")
	public String SessionPoolTestForm() {
		return "SessionPoolTestForm";
	}

	@GetMapping(value="reaAlldData")
	public ModelAndView reaAlldData(@RequestParam("sourceType") String sourceType ) {
			
		ModelAndView mv=new ModelAndView("FactoryList");
		mv.addObject("sourceType",sourceType);
		if ( sourceType.equals("DAOImpl")) {
			DBCoreLogger.printInfo("Factory read Test..DAOImpl");
			DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
			mv.addObject("List", Mystart.readFactory());
		}
		if ( sourceType.equals("Annotation")) {
			DBCoreLogger.printInfo("Factory read Test..Annotation");
			FactoryService factoryService=context.getBean(FactoryService.class);
			mv.addObject("List", factoryService.readFactory());
		}
		
		DBCoreLogger.printInfo("Factory read Test..");
		return mv;
	}
	
	@RequestMapping(value="insertFactoryForm")
	public String insertFactoryForm() {
		return "insertFactoryForm";
	}
	
	@RequestMapping(value="readFromSQLForm")
	public String readFromSQLForm() {
		return "readFromSQLForm";
	}
		
	@RequestMapping(value="sessionTest", method=RequestMethod.GET)
	@ResponseBody
	public String sessionTest(@RequestParam("SessionNo") int SessionNo) {
		DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		for(int i=0; i< SessionNo; i++)
			Mystart.testDBCoreSesion();
	
		String Result= "Create Sessions :" + SessionNo;
		return Result;
	}
	
	/**
	 * @param memberCorpid
	 * @param factoryid
	 * @param sourceType
	 * @return
	 */
	@GetMapping(value="/readDataWithKey" )
	public ModelAndView readDataWithKey( @RequestParam("memberCorpid") String memberCorpid,
									@RequestParam("factoryid") String factoryid,
									@RequestParam("sourceType") String sourceType) {
		
		ModelAndView mv=new ModelAndView("readDataWithKeyForm");
		mv.addObject("sourceType", sourceType);
		
		if ( sourceType.equals("DAOImpl")) {	
			DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
			FactoryDTO factory=(FactoryDTO) Mystart.readFactoryWithKey(memberCorpid,factoryid);
			mv.addObject("FactoryId",factory );
		}
		if ( sourceType.equals("Annotation")) {
			FactoryService factoryService=context.getBean(FactoryService.class);
			mv.addObject("FactoryId", factoryService.readFactoryWithKey(memberCorpid,factoryid));
		}
		
		return mv;
	}
	
	@GetMapping(value="/updateDataWithKey")
	@ResponseBody
	public String updateDataWithKey( FactoryDTO myfactory,
			@RequestParam("sourceType") String sourceType) {
		String Result=null;
		
		if ( sourceType.equals("DAOImpl")) {		
		DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		Result= Mystart.updateFactoryWithKey(myfactory);
		}
		
		if ( sourceType.equals("Annotation")) {
			FactoryService factoryService=context.getBean(FactoryService.class);
			Result=factoryService.updateFactoryWithKey(myfactory);
		}
		return Result;
	}
	
	@GetMapping(value="/deleteDataWithKey" )
	@ResponseBody
	public String deleteDataWithKey( @RequestParam("memberCorpid") String memberCorpid,
									@RequestParam("factoryid") String factoryid,
									@RequestParam("sourceType") String sourceType ) {
		
		String Result=null;
		if ( sourceType.equals("DAOImpl")) {	
			DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
			Result= Mystart.deleteFactoryWithKey(memberCorpid,factoryid);
}
		
		if ( sourceType.equals("Annotation")) {
			FactoryService factoryService=context.getBean(FactoryService.class);
			Result=factoryService.deleteFactoryWithKey(memberCorpid,factoryid);
		}
		return Result;
	}
	
	@GetMapping(value="/insertFactory")
	@ResponseBody
	public String insertFactory( FactoryDTO myfactory) {
		DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		return Mystart.insertFactory(myfactory);
	}
	
	@GetMapping(value="/readDataFromSQL" )
	public ModelAndView readDataFromSQL( @RequestParam("SQLQuery") String SQLQuery,
									@RequestParam("QueryType") String QueryType ) {
		DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		ModelAndView mv=new ModelAndView("FactoryList");
		mv.addObject("sourceType", "DAOImpl");
		mv.addObject("List", Mystart.readFactoryFromSQL(SQLQuery,QueryType));
		return mv;
	}
	
}
