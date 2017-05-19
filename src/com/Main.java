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
		boolean flag = false;//标识当前参数应该是命令还是参数值，false表示命令
		for(int i=0;i<args.length;i++){
			String arg = args[i];
			if(!flag && Cmd.HELP.contains(arg)){
				cmd.setHelpFlag(true);
			}else if(!flag && Cmd.VERSION.contains(arg)){
				cmd.setVersionFlag(true);
			}else if(!flag && Cmd.CLASSPATH.contains(arg)){
				flag = true;
			}else if(flag){
				cmd.setCpOption(arg);
				flag = false;
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
		System.out.println("Usage: minijvm.exe [-options] class [args...]\n");
	}
	//启动MiniJvm
	private static void startMiniJvm(Cmd cmd){
		System.out.println("classpath:"+cmd.getCpOption()+" class:"+cmd.getClassName()+" args:"+Arrays.toString(cmd.getArgs()));
	}
}
