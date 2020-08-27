package com.udmtek.DBCore.ComException;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.udmtek.DBCore.DBAccessor.DBCoreCommService;

/** This is for err message.
 *  Read from data base but it is not advisable to process messages on the server.
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
public class NationErrorMessages {
	private static Logger logger=LoggerFactory.getLogger(NationErrorMessages.class);
	private EnumMap<NationCode, ErrorMessages> nationMessages;
	/**
	 * constructor NationErrorMessages
	 */
	public NationErrorMessages() {
		logger.trace("NationErrorMessages. constructor call");
		nationMessages= new EnumMap<>(NationCode.class);
	}
	
	/**
	 * find from language set using nation code and error code, return error message
	 * @param nationCode
	 * @param errorCode
	 * @return String
	 */
	public String getMessages(NationCode nationCode, ErrorCode errorCode) {
		ErrorMessages errorMessges = nationMessages.get(nationCode);
		return errorMessges.getMessage(errorCode.getCode());
	}
	
	
	/**
	 * load from predefined DefaultMessages 
	 * @return this
	 */
	//DB로부터 읽지 않고 DefaultMessages에 설정된 message로 message Map을 만든다.
	public NationErrorMessages defaultMessages() {
		//default Setting
		ErrorMessages cnMessage = new ErrorMessages();
		putMessages(cnMessage,NationCode.CN );
		nationMessages.put(NationCode.CN, cnMessage);
		
		//default Setting
		ErrorMessages krMessage = new ErrorMessages();
		putMessages(krMessage,NationCode.KR);
		nationMessages.put(NationCode.KR, krMessage);
		
		//default Setting
		ErrorMessages enMessage = new ErrorMessages();
		putMessages(enMessage,NationCode.EN);
		nationMessages.put(NationCode.EN, enMessage);
		
		return this;
	}
		
	private NationErrorMessages putMessages(ErrorMessages errorMessage, NationCode nationCode ) {
		DefaultMessages[] defaultMesages=DefaultMessages.values();
		for(DefaultMessages defaultMesssage:defaultMesages) {
			errorMessage.put(defaultMesssage.getCode(), defaultMesssage.getMessage(nationCode));
		}
		return this;
	}
	
	/**
	 * Default Message set.
	 * @author julu1
	 */
	private enum DefaultMessages {
		DB_ACCESS_ERROR("D001",	"DataBase에 접근할 수 없습니다","DataBase access is denied",""),
		INVALID_ARGUMENT("D002","잘못된 인수입니다","Invalid argument",""),
		INVALID_LENGTH("D003"," 자리수를 초과하였습니다"," cant not exceed the number of strings",""),
		INVALID_NULLABLE("D004","Null값을 허용하지 않습니다","Null value are not allowed",""),
		DB_TYPE_ERROR("D005","Data type이 맞지 않습니다.","Invalid data type",""),
		;
		private String code;
		private String kr_Message;
		private String EN_Message;
		private String CN_Message;
		
		DefaultMessages(String code, String kr_Message, String EN_Message, String CN_Message) {
			this.code = code;
			this.kr_Message = kr_Message;
			this.EN_Message = EN_Message;
			this.CN_Message = CN_Message;
		}
		
		public String getCode() { return code;}
		
		public String getMessage(NationCode nationCode) {
			switch (nationCode) {
			case CN:
				return CN_Message;
			case EN:
				return EN_Message;
			case KR:
				return kr_Message;
			}
			//derfault는 kr 
			return kr_Message;
		}
	}
}
