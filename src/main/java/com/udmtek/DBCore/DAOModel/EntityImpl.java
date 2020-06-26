package com.udmtek.DBCore.DAOModel;

import java.lang.reflect.Field;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;

public class EntityImpl implements Entity {

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public Entity getInfo(Class<?> type) {
		Object result=null;
		try {

			DBCoreLogger.printInfo("EntityImpl"+ this.getClass().getSimpleName());
			result = (Object) type.newInstance();
			DBCoreLogger.printInfo("EntityImpl"+ result.getClass().getSimpleName());
			
			Field[] FieldArray=this.getClass().getDeclaredFields();
			
			for ( int i=0; i < FieldArray.length; i ++) {
	
				Field thisField =FieldArray[i];
				String FieldName=thisField.getName();
				String getterName= "get" + FieldName.toUpperCase().charAt(0)+FieldName.substring(1);
				Class<?> resultType = this.getClass().getMethod(getterName).getReturnType();
				String setterName= "set" + FieldName.toUpperCase().charAt(0)+FieldName.substring(1);
				Object getresult=this.getClass().getMethod(getterName).invoke(this);
				result.getClass().getMethod(setterName,resultType ).invoke(result, getresult);
			}
		}
		catch ( Exception e) {
			e.printStackTrace();
		}

		return (Entity) result;
	}

}
