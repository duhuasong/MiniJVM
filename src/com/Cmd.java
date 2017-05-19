package com;

import java.util.Arrays;
import java.util.List;

public class Cmd {
	public static final List<String> HELP = Arrays.asList("-help","-?","-h");//帮助命令，打印帮助信息
	public static final List<String> VERSION = Arrays.asList("-version","-v");//版本命令，打印版本信息
	public static final List<String> CLASSPATH = Arrays.asList("-classpath","-cp");//类路径设置命令
	public static final List<String> X_JRE = Arrays.asList("-Xjre");//启动类路径设置命令
	private Boolean helpFlag = false;
	private Boolean versionFlag = false;
	private String cpOption = null;
	private String XjreOption = null;
	private String className = null;
	private String[] args = null;
	
	public Boolean getHelpFlag() {
		return helpFlag;
	}
	public void setHelpFlag(Boolean helpFlag) {
		this.helpFlag = helpFlag;
	}
	public Boolean getVersionFlag() {
		return versionFlag;
	}
	public void setVersionFlag(Boolean versionFlag) {
		this.versionFlag = versionFlag;
	}
	public String getCpOption() {
		return cpOption;
	}
	public void setCpOption(String cpOption) {
		this.cpOption = cpOption;
	}
	public String getXjreOption() {
		return XjreOption;
	}
	public void setXjreOption(String xjreOption) {
		XjreOption = xjreOption;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String[] getArgs() {
		return args;
	}
	public void setArgs(String[] args) {
		this.args = args;
	}
	
}
