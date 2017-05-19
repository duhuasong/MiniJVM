package com.classpath;

import java.io.File;

public abstract class Entry {
	public static final String pathSeparator = File.pathSeparator;
	/**
	 * 读取class文件字节码
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 */
	abstract byte[] readClass(String className);
	/**
	 * 创建文件实例
	 * @param path
	 * @return
	 */
	public static Entry newEntry(String path){
		if(path.contains(pathSeparator)) return new CompositeEntry(path);
		if(path.endsWith("*")) return new WildcardEntry(path);
		if(path.toLowerCase().endsWith(".zip")||path.toLowerCase().endsWith(".jar")) return new ZipEntry(path);
		return new DirEntry(path);
	}
}
