package com.classpath;

import java.util.ArrayList;
import java.util.List;

public class CompositeEntry extends Entry {
	protected List<Entry> entrys = new ArrayList<>();
	public CompositeEntry(String pathList) {
		parsePathList(pathList);
	}
	@Override
	byte[] readClass(String className) throws ClassNotFoundException {
		byte[] bytes = null;
		for(Entry entry:entrys){
			try {
				if((bytes = entry.readClass(className))!=null){
					return bytes;
				}
			} catch (ClassNotFoundException e) {}
		}
		throw new ClassNotFoundException("在路径"+toString()+"中未找到"+className);
	}
	public List<Entry> getEntrys() {
		return entrys;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(Entry entry:entrys){
			str.append(entry);
			str.append(pathSeparator);
		}
		return str.deleteCharAt(str.length()-1).toString();
	}
	
	protected void parsePathList(String pathList){
		for(String path : pathList.split(pathSeparator)){
			entrys.add(newEntry(path));
		}
	}
}
