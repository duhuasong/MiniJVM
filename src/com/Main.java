package com;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import com.classfile.ClassFile;
import com.classfile.MemberInfo;
import com.classpath.Classpath;
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
		Classpath cp = Classpath.parse(cmd.getXjreOption(), cmd.getCpOption());
		String className = cmd.getClassName().replace(".", "/");
		ClassFile cf = loadClass(className,cp);
		printClassInfo(cf);
//		System.out.println(Arrays.toString(classData));
	}

	private static void printClassInfo(ClassFile cf) {
		System.out.println("version:"+cf.getMajorVersion()+"."+cf.getMinorVersion());
		System.out.println("constants count:"+cf.getConstantPool().getCp().length);
		System.out.println("access flags:"+Modifier.toString(cf.getAccessFlag()));
		System.out.println("this class:"+cf.getClassName());
		System.out.println("super class:"+cf.getSuperClassName());
		System.out.println("interfaces:"+Arrays.toString(cf.getInterfaceNames()));
		System.out.println("fields count:"+cf.getFields().length);
		for(MemberInfo field:cf.getFields()){
			System.out.println(field.getDescription()+" "+field.getName());
		}
		System.out.println("methods count:"+cf.getMethods().length);
		for(MemberInfo method:cf.getMethods()){
			System.out.println(method.getDescription()+" "+method.getName());
		}
	}

	private static ClassFile loadClass(String className, Classpath cp) {
		byte[] classData = null;
		try {
			classData = cp.readClass(className);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		return ClassFile.parse(classData);
	}
}
