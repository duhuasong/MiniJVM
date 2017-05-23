package com.classfile;

public class ConstantPool {
	private ConstantInfo[] cp;
	public ConstantPool readConstantPool(ClassReader cr){
		int cpCount = cr.readUnit16()&0xffff;
		cp = new ConstantInfo[cpCount];
		for(int i=1;i<cpCount;i++){
			cp[i] = cp[i].readConstantInfo(cr,this);
			if(cp[i] instanceof ConstantLongInfo || cp[i] instanceof ConstantDoubleInfo) i++;
		}
		return this;
	}
	public ConstantInfo getConstantInfo(short index){
		return cp[index];
	}
	public String getNameAndType(short index){
		ConstantNameAndTypeInfo ntInfo = (ConstantNameAndTypeInfo)getConstantInfo(index);
		return getUtf8(ntInfo.descriptionIndex)+" "+getUtf8(ntInfo.nameIndex);
	}
	public String getClassName(short index){
		ConstantClassInfo classInfo = (ConstantClassInfo)getConstantInfo(index);
		return getUtf8(classInfo.nameIndex);
	}
	public String getUtf8(short index){
		ConstantUtf8Info utf8Info = (ConstantUtf8Info)getConstantInfo(index);
		return utf8Info.toString();
	}
	public ConstantInfo[] getCp() {
		return cp;
	}
}
