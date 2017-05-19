package com.classpath;

import java.io.File;

public class WildcardEntry extends CompositeEntry {
	public WildcardEntry(String path) {
		super(path);
	}
	@Override
	protected void parsePathList(String path) {
		String baseDir = path.substring(0, path.length()-1);
		File dir = new File(baseDir);
		for(File file:dir.listFiles()){
			if(file.getPath().toLowerCase().endsWith(".jar")){//不允许递归子目录下的jar包
				this.entrys.add(newEntry(file.getPath()));
			}
		}
	}
}
