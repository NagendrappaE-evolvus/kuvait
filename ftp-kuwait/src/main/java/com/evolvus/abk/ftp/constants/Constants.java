package com.evolvus.abk.ftp.constants;

public interface Constants {

	Integer ONE = 1;
	Integer CCY_CRV_MAX_CELL = 13;
	Integer ZERO = 0;

	String FIELD_TYPE_TEXT = "TEXT";
	
	String FIELD_TYPE_NUMBER = "NUMBER";
	
	String FIELD_TYPE_DATE = "DATE";
	String FIELD_TYPE_DATE_C = "DATE_C";
	
	String FIELD_TYPE_MULTISELECT = "MULTISELECT";
	
	String FIELD_TYPE_PRECHIPS = "PRECHIPS";
	
	String FIELD_TYPE_CHIPS = "CHIPS";
	
	String FIELD_TYPE_SELECT = "SELECT";
	
	String EMPTY = "";
	
	String STATUS_OK="OK";
	String STATUS_NO_DATA="NODATA";
	String LIST_TEMP = "TEMP";
	String LIST_MAIN = "MAIN";
	String STATUS_FAIL="FAIL";
	
	String OP_EQUAL="=";
	
	String LDAP_SN_KEY="sn";
	String LDAP_GIVEN_NAME_KEY="givenName";
	String LDAP_MAIL_KEY="mail";
	
	String TXN_SAVE = "SAVE";
	String TXN_CREATE = "CREATE";
	String TXN_UPDATE = "UPDATE";
	String TXN_DELETE = "DELETE";
	String TXN_FETCH = "FETCH";
	String TXN_LOGIN = "LOGIN";
	String TXN_LOGOUT = "LOGOUT";
	String TXN_FILE_UPLOAD = "FILE_UPLOAD";
	
	Integer CRUD_CREATE = 1;
	Integer CRUD_UPDATE = 2;
	Integer CRUD_DELETE = 3;
	
	String NA_SHORT = "NA";
	
	String NA_LONG = "Not Available";	
}
