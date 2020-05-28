package com.udmtek.DBCore.TestModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;
import com.udmtek.DBCore.DBAccessor.DBCoreAccessManager;

@Controller
public class DBCoreTestController {
	@Autowired
	ApplicationContext context;

	@RequestMapping(value="/")
	public String testGuid() {
		return "main";
	}
		
	
	@GetMapping(value="/sessionTest")
	@ResponseBody
	public String sessionTest() {
			
		DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		
		DBCoreLogger.printInfo("[Step 1] make DBCoreSessionManger, DBCoreSessions..");
		Mystart.ready();
		
		DBCoreLogger.printInfo("[Step 2] get and release DBCoreSession in multi-thread..");
		
		Mystart.testDBCoreSesion();
			
				
		  Mystart.testDBCoreSesion(); //Mystart.testDBCoreSesion();
		  Mystart.testDBCoreSesion(); //Mystart.testDBCoreSesion();
		  Mystart.testDBCoreSesion(); //Mystart.testDBCoreSesion();
		  return "Test done";
	}
	
	@GetMapping(value="/reaAlldData")
	public ModelAndView reaAlldData() {
			
		DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		Mystart.ready();
		ModelAndView mv=new ModelAndView("Factory");
		mv.addObject("List", Mystart.readFactory());
		DBCoreLogger.printInfo("Factory read Test..");
		return mv;
	}
	
	@GetMapping(value="/readDataWithKey" )
	@ResponseBody
	public String readDataWithKey( @RequestParam("memberCorpid") String memberCorpid,
									@RequestParam("factoryid") String factoryid ) {
			
		DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		Mystart.ready();
		return Mystart.readFactoryWithKey(memberCorpid,factoryid);
	}

	@GetMapping(value="/updateDataWithKey" )
	@ResponseBody
	public String updateDataWithKey( @RequestParam("memberCorpid") String memberCorpid,
									@RequestParam("factoryid") String factoryid ) {
			
		DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		Mystart.ready();
		return Mystart.updateFactoryWithKey(memberCorpid,factoryid);
	}
	
	@GetMapping(value="/deleteDataWithKey" )
	@ResponseBody
	public String deleteDataWithKey( @RequestParam("memberCorpid") String memberCorpid,
									@RequestParam("factoryid") String factoryid ) {
			
		DBCoreTestClass	Mystart=context.getBean(DBCoreTestClass.class);
		Mystart.ready();
		return Mystart.deleteFactoryWithKey(memberCorpid,factoryid);
	}
}
