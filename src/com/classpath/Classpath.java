package com.classpath;

import java.io.File;

import com.utils.StringUtil;

public class Classpath {
	private Entry bootClasspath;
	private Entry extClasspath;
	private Entry userClasspath;
	
	public Classpath parse(String jreOption,String cpOption){
		Classpath cp = new Classpath();
		cp.parseBootAndExtClasspath(jreOption);
		cp.parseUserClasspath(cpOption);
		return cp;
	}
	
	private void parseBootAndExtClasspath(String jreOption){
		String jreDir = getJreDir(jreOption);
		String jreLibPath = jreDir+File.separator+"lib"+File.separator+"*";//jre/lib/*
		this.bootClasspath = Entry.newEntry(jreLibPath);
		String jreExtPath = jreDir+File.separator+"lib"+File.separator+"ext"+File.separator+"*";//jre/lib/ext/*
		this.extClasspath = Entry.newEntry(jreExtPath);
	}
	private void parseUserClasspath(String cpOption){
		if(StringUtil.isEmpty(cpOption))
			cpOption = ".";
		this.userClasspath = Entry.newEntry(cpOption);
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
		bytes = this.bootClasspath.readClass(className);
		bytes = bytes!=null ?bytes : this.extClasspath.readClass(className);
		bytes = bytes!=null ?bytes : this.userClasspath.readClass(className);
		if(bytes!=null) return bytes;
		throw new ClassNotFoundException("未在路径"+this.bootClasspath.toString()+","
		+this.extClasspath.toString()+","+this.userClasspath.toString()+"中找到"+className);
	}
	public Entry getBootClasspath() {
		return bootClasspath;
	}
	public void setBootClasspath(Entry bootClasspath) {
		this.bootClasspath = bootClasspath;
	}
	public Entry getExtClasspath() {
		return extClasspath;
	}
	public void setExtClasspath(Entry extClasspath) {
		this.extClasspath = extClasspath;
	}
	public Entry getUserClasspath() {
		return userClasspath;
	}
	public void setUserClasspath(Entry userClasspath) {
		this.userClasspath = userClasspath;
	}
	@Override
	public String toString() {
		return this.userClasspath.toString();
	}
}
