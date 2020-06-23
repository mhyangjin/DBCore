
package com.udmtek.DBCore.DAOModel;

import com.udmtek.DBCoreTest.model.FactoryDAO;
import com.udmtek.DBCoreTest.model.FactoryInfo;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
public interface Entity extends Cloneable{

	public Entity getInfo(Class<?> type);
}
