package com.classpath;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.utils.IOUtil;

public class ZipEntry extends Entry {
	private String absPath = "";
	public ZipEntry(String path) {
		this.absPath = new File(path).getAbsolutePath();
	}
	@Override
	byte[] readClass(String className){
		ZipFile jarFile = null;
		ZipInputStream jarIn = null;
		try {
			jarFile = new ZipFile(absPath);
			jarIn = new ZipInputStream(new FileInputStream(absPath));
			java.util.zip.ZipEntry entry = null;
			while((entry = jarIn.getNextEntry())!=null){
				if(className.equals(entry.getName())){
					return IOUtil.toByteArray(jarFile.getInputStream(entry));
				}
			}
		}catch(IOException e){
			return null;
		}finally {
			try {
				jarIn.close();
				jarFile.close();
			} catch (IOException e2) {}
		}
		return null;
	}
	public String getAbsPath() {
		return absPath;
	}

	@Override
	public String toString() {
		return this.absPath;
	}
}
