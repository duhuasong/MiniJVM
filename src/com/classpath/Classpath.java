package com.classpath;

import java.io.File;

import com.utils.StringUtil;

public class Classpath {
	private static Entry bootClasspath;
	private static Entry extClasspath;
	private static Entry userClasspath;
	
	public static Classpath parse(String jreOption,String cpOption){
		Classpath cp = new Classpath();
		cp.parseBootAndExtClasspath(jreOption);
		cp.parseUserClasspath(cpOption);
		return cp;
	}
	
	private void parseBootAndExtClasspath(String jreOption){
		String jreDir = getJreDir(jreOption);
		String jreLibPath = jreDir+File.separator+"lib"+File.separator+"*";//jre/lib/*
		bootClasspath = Entry.newEntry(jreLibPath);
		String jreExtPath = jreDir+File.separator+"lib"+File.separator+"ext"+File.separator+"*";//jre/lib/ext/*
		extClasspath = Entry.newEntry(jreExtPath);
	}
	private void parseUserClasspath(String cpOption){
		if(StringUtil.isEmpty(cpOption))
			cpOption = ".";
		userClasspath = Entry.newEntry(cpOption);
	}
	private String getJreDir(String cpOption){
		if(!StringUtil.isEmpty(cpOption)&&new File(cpOption).exists())
			return cpOption;
		if(new File("./jre").exists())
			return "./jre";
		String javaHome = null;
		if((javaHome = System.getenv("JAVA_HOME"))!=null)
			return javaHome+File.separator+"jre";
		throw new RuntimeException("找不到jre目录");
	}
	
	public byte[] readClass(String className) throws ClassNotFoundException{
		className = className+".class";
		byte[] bytes = null;
		bytes = bootClasspath.readClass(className);
		bytes = bytes!=null ?bytes : extClasspath.readClass(className);
		bytes = bytes!=null ?bytes : userClasspath.readClass(className);
		if(bytes!=null) return bytes;
		throw new ClassNotFoundException("未在路径"+bootClasspath.toString()+","
		+extClasspath.toString()+","+userClasspath.toString()+"中找到"+className);
	}
	@Override
	public String toString() {
		return userClasspath.toString();
	}
}
