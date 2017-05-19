package com;

import java.util.Arrays;

import com.utils.StringUtil;


public class Main {
	public static void main(String[] args) {
		Cmd cmd = parseCmd(args); 
		if(cmd.getVersionFlag()){
			System.out.println("version 0.0.1");
		}else if(cmd.getHelpFlag()||StringUtil.isEmpty(cmd.getClassName())){
			printUsage();
		}else{
			startMiniJvm(cmd);
		}
	}
	
	//解析命令行参数
	private static Cmd parseCmd(String[] args){
		Cmd cmd = new Cmd();
		for(int i=0;i<args.length;i++){
			String arg = args[i];
			String nextArg = "";
			if(i+1<args.length) nextArg = args[i+1];
			if(Cmd.HELP.contains(arg)){
				cmd.setHelpFlag(true);
			}else if(Cmd.VERSION.contains(arg)){
				cmd.setVersionFlag(true);
			}else if(Cmd.CLASSPATH.contains(arg)){
				cmd.setCpOption(nextArg);
				i++;
			}else if(Cmd.X_JRE.contains(arg)){
				cmd.setXjreOption(nextArg);
				i++;
			}else{
				cmd.setClassName(arg);
				cmd.setArgs(Arrays.copyOfRange(args, i+1, args.length));
				break;
			}
		}
		return cmd;
	}
	//打印提示信息
	private static void printUsage(){
		System.out.println("Usage: minijvm.exe [-options] class [args...]");
		System.out.println("  "+Cmd.HELP.toString()+":print help message");
		System.out.println("  "+Cmd.VERSION.toString()+":print version");
		System.out.println("  "+Cmd.CLASSPATH.toString()+":classpath");
		System.out.println("  "+Cmd.X_JRE.toString()+":path to jre");
	}
	//启动MiniJvm
	private static void startMiniJvm(Cmd cmd){
		System.out.println("classpath:"+cmd.getCpOption()+" class:"+cmd.getClassName()+" args:"+Arrays.toString(cmd.getArgs()));
	}
}
