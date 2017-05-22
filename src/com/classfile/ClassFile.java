package com.classfile;

public class ClassFile {
	private int magic;
	private short minorVersion;
	private short majorVersion;
	private ConstantPool constantPool;
	private short accessFlags;
	private short thisClass;
	private short superClass;
	private short[] interfaces;
	private MemberInfo[] fields;
	private MemberInfo[] methods;
	private AttributeInfo[] attributes;
	
	public ClassFile parse(byte[] classData){
		ClassReader cr = new ClassReader(classData);
		ClassFile cf = new ClassFile();
		cf.read(cr);
		return cf;
	}
	private void read(ClassReader cr){
		readAndCheckMagic(cr);
		readAndCheckVersion(cr);
		this.constantPool = readConstantPool(cr);
		this.accessFlags = cr.readUnit16();
		this.thisClass = cr.readUnit16();
		this.superClass = cr.readUnit16();
		this.interfaces = cr.readUnit16s();
		this.fields = readMembers(cr);
		this.methods = readMembers(cr);
		this.attributes = readAttributes(cr);
	}
	protected void readAndCheckMagic(ClassReader cr){
		if((this.magic=cr.readUnit32())!=0xcafebabe)
			throw new ClassFormatError("magic error!");
	}
	protected void readAndCheckVersion(ClassReader cr){
		this.minorVersion = cr.readUnit16();
		this.majorVersion = cr.readUnit16();
		switch(majorVersion){
			case 45:return;
			case 46:
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:if(minorVersion==0) return;
			default:throw new UnsupportedClassVersionError(majorVersion+"."+minorVersion);
		}
	}
	protected ConstantPool readConstantPool(ClassReader cr){
		return null;
	}
	private MemberInfo[] readMembers(ClassReader cr){
		int count = cr.readUnit16()&0xff;//转成无符号数值
		MemberInfo[] members = new MemberInfo[count];
		for(int i=0;i<count;i++){
			members[i] = readMember(cr);
		}
		return members;
	}
	protected MemberInfo readMember(ClassReader cr){
		MemberInfo member = new MemberInfo();
		member.setCp(constantPool);
		member.setAccessFlags(cr.readUnit16());
		member.setNameIndex(cr.readUnit16());
		member.setDescriptionIndex(cr.readUnit16());
		member.setAttributes(readAttributes(cr));
		return member;
	}
	protected AttributeInfo[] readAttributes(ClassReader cr){
		return null;
	}
	public short getMinorVersion() {
		return minorVersion;
	}
	public short getMajorVersion() {
		return majorVersion;
	}
	public ConstantPool getConstantPool() {
		return constantPool;
	}
	public short getAccessFlag() {
		return accessFlags;
	}
	public MemberInfo[] getFields() {
		return fields;
	}
	public MemberInfo[] getMethods() {
		return methods;
	}
	public String getClassName(){
		return this.constantPool.getClassName(this.thisClass);
	}
	public String getSuperClassName(){
		if(this.superClass!=0){
			return this.constantPool.getClassName(this.superClass);
		}
		return "";//只有java.lang.Object没有超类
	}
	public String[] getInterfaceNames(){
		int length = this.interfaces.length;
		String[] interfaceNames = new String[length];
		for(int i=0;i<length;i++){
			interfaceNames[i] = this.constantPool.getClassName(this.interfaces[i]);
		}
		return interfaceNames;
	}
}
