package com.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Primitive{
	Z("boolean"),B("byte"),C("char"),I("int"),J("long"),D("double"),V("void"),S("short"),F("float");
	private String type;
	
	private Primitive(String type){
		this.setType(type);
	}

	public static String getRealType(String type){
		String returnType = "";
		for(char c:type.toCharArray()){
			if('['!=c) break;
			returnType += "[]";
		}
		type = type.substring(returnType.length()>>>1);
		if(type.length()==1){
			return valueOf(type).getType()+returnType;
		}else{
			return type.substring(1,type.length()-1).replace('/', '.')+returnType;
		}
	}
	public static Map<String,String> getRealMethodDescription(String desc){
		Map<String,String> realDescription = new HashMap<>();
		String[] params = desc.split("[()]");
		String inType = params[1];
		String returnType = params[2];
		String[] inTypes = inType.split("(?<=\\[{0,10}[ZBCIJDSF]|\\[{0,10}L[^;]{1,65535};)(?=\\[{0,10}[ZBCIJDSF]|\\[{0,10}L[^;]{1,65535};)");
		List<String> inParams = new ArrayList<String>();
		for(String tmpType:inTypes){
			if(!"".equals(tmpType))
				inParams.add(getRealType(tmpType));
		}
		returnType = getRealType(returnType);
		realDescription.put("in", inParams.toString().substring(1, inParams.toString().length()-1));
		realDescription.put("out", returnType);
		return realDescription;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
