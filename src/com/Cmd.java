package com;

import java.util.Arrays;
import java.util.List;

public class Cmd {
	public static final List<String> HELP = Arrays.asList("-help","-?","-h");
	public static final List<String> VERSION = Arrays.asList("-version","-v");
	public static final List<String> CLASSPATH = Arrays.asList("-classpath","-cp");
	private Boolean helpFlag = false;
	private Boolean versionFlag = false;
	private String cpOption = null;
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
