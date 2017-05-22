package com.classfile;

public abstract class ConstantInfo {
	public static final byte CONSTANT_Class = 7;
	public static final byte CONSTANT_Fieldref = 9;
	public static final byte CONSTANT_Methodref = 10;
	public static final byte CONSTANT_InterfaceMethodref = 11;
	public static final byte CONSTANT_String = 8;
	public static final byte CONSTANT_Integer = 3;
	public static final byte CONSTANT_Float = 4;
	public static final byte CONSTANT_Long = 5;
	public static final byte CONSTANT_Double = 6;
	public static final byte CONSTANT_NameAndType = 12;
	public static final byte CONSTANT_Utf8 = 1;
	public static final byte CONSTANT_MethodHandle = 15;
	public static final byte CONSTANT_MethodType = 16;
	public static final byte CONSTANT_InvokeDynamic = 18;
	
	public abstract void readInfo(ClassReader cr);
	public static ConstantInfo readConstantInfo(ClassReader cr,ConstantPool cp){
		byte tag = cr.readUnit8();
		ConstantInfo ci = newConstantInfo(tag,cp);
		ci.readInfo(cr);
		return ci;
	}
	private static ConstantInfo newConstantInfo(byte tag,ConstantPool cp){
		return null;
	}
}
class ConstantLongInfo extends ConstantInfo{

	@Override
	public void readInfo(ClassReader cr) {
		// TODO Auto-generated method stub
		
	}
	
}
class ConstantDoubleInfo extends ConstantInfo{

	@Override
	public void readInfo(ClassReader cr) {
		// TODO Auto-generated method stub
		
	}
	
}
class ConstantClassInfo extends ConstantInfo{
	protected short nameIndex;

	@Override
	public void readInfo(ClassReader cr) {
		// TODO Auto-generated method stub
		
	}
}
class ConstantUtf8Info extends ConstantInfo{

	@Override
	public void readInfo(ClassReader cr) {
		// TODO Auto-generated method stub
		
	}
	
}
class ConstantNameAndTypeInfo extends ConstantInfo{
	protected short nameIndex;
	protected short descriptionIndex;
	@Override
	public void readInfo(ClassReader cr) {
		// TODO Auto-generated method stub
		
	}
}