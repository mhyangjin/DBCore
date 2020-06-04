package com.udmtek.DBCore.ComUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.udmtek.DBCore.DBAccessor.DBCoreSession;
import com.udmtek.DBCore.DBAccessor.DBCoreSessionManager;

/** This is Configuration.
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Configuration
public class DBCoreConfigClass {
	/**
	 * make bean object of Map<String,DBCoreSessionManager> 
	 * @return Map<String,DBCoreSessionManager>
	 */
	@Bean
	public Map<String,DBCoreSessionManager> getMap() {
		return Collections.synchronizedMap(new HashMap<String,DBCoreSessionManager>());
	}
	/**
	 * make bean object of Set<DBCoreSession>
	 * @return Set<DBCoreSession>
	 */
	@Bean(name="getList")
	@Scope(value="prototype")
	public Set<DBCoreSession> getList() {
		return Collections.synchronizedSet(new HashSet<DBCoreSession>());
	}
}
