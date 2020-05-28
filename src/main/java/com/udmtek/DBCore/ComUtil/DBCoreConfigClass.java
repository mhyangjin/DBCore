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

@Configuration
public class DBCoreConfigClass {
	@Bean
	public Map<String,DBCoreSessionManager> getMap() {
		return Collections.synchronizedMap(new HashMap<String,DBCoreSessionManager>());
	}
	@Bean(name="getList")
	@Scope(value="prototype")
	public Set<DBCoreSession> getList() {
		return Collections.synchronizedSet(new HashSet<DBCoreSession>());
	}
}
