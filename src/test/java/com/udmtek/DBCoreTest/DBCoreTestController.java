package com.udmtek.DBCoreTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.codeJ.MVCTestGen.ControllerTestGenerator;
import com.udmtek.DBCore.ComUtil.DBCoreLogger;
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
	
	@RequestMapping(value="insertFactoryForm")
	public String insertFactoryForm() {
		return "insertFactoryForm";
	}
	
	@RequestMapping(value="readFromSQLForm")
	public String readFromSQLForm() {
		return "readFromSQLForm";
	}
	
	@ControllerTestGenerator(MethodName="readAllDataFromDAO")
	@GetMapping(value="readAllDataFromDAO")
	public ModelAndView readAllDataFromDAO( ) {
		ModelAndView mv=new ModelAndView("FactoryList");
		DBCoreLogger.printInfo("Factory read Test..DAOImpl");
		DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		mv.addObject("List", Mystart.readFactory());
		return mv;
	}
	
	@ControllerTestGenerator(MethodName="readAllDataFromAnnotation")
	@GetMapping(value="readAllDataFromAnnotation")
	public ModelAndView reaAlldData( ) {
		ModelAndView mv=new ModelAndView("FactoryList");
		DBCoreLogger.printInfo("Factory read Test..Annotation");
		FactoryService factoryService=context.getBean(FactoryService.class);
		mv.addObject("List", factoryService.readFactory());
		return mv;
	}
	
	@ControllerTestGenerator(MethodName="sessionTest")
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
	@ControllerTestGenerator(MethodName="readDataWithKeyFromDAO")
	@GetMapping(value="/readDataWithKeyFromDAO" )
	public ModelAndView readDataWithKeyFromDAO( @RequestParam("memberCorpid") String memberCorpid,
									@RequestParam("factoryid") String factoryid) {
		ModelAndView mv=new ModelAndView("readDataWithKeyForm");
		DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		FactoryDTO factory=(FactoryDTO) Mystart.readFactoryWithKey(memberCorpid,factoryid);
		mv.addObject("FactoryId",factory );
		return mv;
	}
	
@ControllerTestGenerator(MethodName="readDataWithKeyFromAnnotation")
@GetMapping(value="/readDataWithKeyFromAnnotation" )
public ModelAndView readDataWithKeyFromAnnotation( @RequestParam("memberCorpid") String memberCorpid,
								@RequestParam("factoryid") String factoryid) {
	ModelAndView mv=new ModelAndView("readDataWithKeyForm");
	FactoryService factoryService=context.getBean(FactoryService.class);
	mv.addObject("FactoryId", factoryService.readFactoryWithKey(memberCorpid,factoryid));
	return mv;
}

	@ControllerTestGenerator(MethodName="updateDataWithKeyFromDAO")
	@GetMapping(value="/updateDataWithKeyFromDAO")
	@ResponseBody
	public String updateDataWithKeyFromDAO( FactoryDTO myfactory) {
		String Result=null;
	DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		Result= Mystart.updateFactoryWithKey(myfactory);
		return Result;
	}
	
	@ControllerTestGenerator(MethodName="updateDataWithKeyFromAnnotaion")
	@GetMapping(value="/updateDataWithKeyFromAnnotaion")
	@ResponseBody
	public String updateDataWithKeyFromAnnotaion( FactoryDTO myfactory) {
		String Result=null;
		FactoryService factoryService=context.getBean(FactoryService.class);
		Result=factoryService.updateFactoryWithKey(myfactory);
		return Result;
	}
		
	@ControllerTestGenerator(MethodName="deleteDataWithKeyFromDAO")
	@GetMapping(value="/deleteDataWithKeyFromDAO" )
	@ResponseBody
	public String deleteDataWithKeyFromDAO( @RequestParam("memberCorpid") String memberCorpid,
									@RequestParam("factoryid") String factoryid ) {
		String Result=null;
		DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		Result= Mystart.deleteFactoryWithKey(memberCorpid,factoryid);
		return Result;
	}
	
	@ControllerTestGenerator(MethodName="deleteDataWithKeyFromAnnotaion")
	@GetMapping(value="/deleteDataWithKeyFromAnnotaion" )
	@ResponseBody
	public String deleteDataWithKeyFromAnnotaion( @RequestParam("memberCorpid") String memberCorpid,
									@RequestParam("factoryid") String factoryid ) {
		
		String Result=null;
		FactoryService factoryService=context.getBean(FactoryService.class);
		Result=factoryService.deleteFactoryWithKey(memberCorpid,factoryid);
		return Result;
	}
		
	@ControllerTestGenerator(MethodName="insertFactory")
	@GetMapping(value="/insertFactory")
	@ResponseBody
	public String insertFactory( FactoryDTO myfactory) {
		DBCoreLogger.printDBDebug("InsertFactory:" + myfactory.ToString());
		DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		return Mystart.insertFactory(myfactory);
	}
	
	
	@ControllerTestGenerator(MethodName="readDataFromSQL")
	@GetMapping(value="/readDataFromSQL" )
	public ModelAndView readDataFromSQL( @RequestParam("SQLQuery") String SQLQuery) {
		DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		ModelAndView mv=new ModelAndView("FactoryList");
		mv.addObject("List", Mystart.readFactoryFromSQL(SQLQuery,"SQL"));
		return mv;
	}
	
	@ControllerTestGenerator(MethodName="readDataFromJPQL")
	@GetMapping(value="/readDataFromJPQL" )
	public ModelAndView readDataFromJPQL( @RequestParam("SQLQuery") String SQLQuery ) {
		DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		ModelAndView mv=new ModelAndView("FactoryList");
		mv.addObject("List", Mystart.readFactoryFromSQL(SQLQuery,"JPQL"));
		return mv;
	}
}
