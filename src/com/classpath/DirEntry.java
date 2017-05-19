package com.classpath;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.utils.IOUtil;

public class DirEntry extends Entry {
	private String absDir = "";//绝对路径
	public DirEntry(String path) {
		this.absDir = new File(path).getAbsolutePath();
	}
	@Override
	byte[] readClass(String className){
		byte[] bytes = null;
		try {
			bytes = IOUtil.toByteArray(new FileInputStream(new File(absDir,className)));
		} catch (IOException e) {
//			throw new ClassNotFoundException("在目录"+absDir+"中未找到"+className,e);
			return null;
		}
		return bytes;
	}
	
	public String getAbsDir() {
		return absDir;
	}
	
	@Override
	public String toString() {
		return this.absDir;
	}

}
