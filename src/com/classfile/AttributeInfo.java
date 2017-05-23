package com.classfile;

public abstract class AttributeInfo {
	public abstract void readInfo(ClassReader cr);
	public static AttributeInfo[] readAttributes(ClassReader cr,ConstantPool cp){
		int attributesCount = cr.readUnit16()&0xffff;
		AttributeInfo[] attributes = new AttributeInfo[attributesCount];
		for(int i=0;i<attributesCount;i++){
			attributes[i] = readAttribute(cr, cp);
		}
		return attributes;
	}
	protected static AttributeInfo readAttribute(ClassReader cr,ConstantPool cp){
		short attrNameIndex = cr.readUnit16();
		String attrName = cp.getUtf8(attrNameIndex);
		int attrLen = cr.readUnit32();
		AttributeInfo attrInfo = newAttributeInfo(attrName,attrLen,cp);
		attrInfo.readInfo(cr);
		return attrInfo;
	}
	private static AttributeInfo newAttributeInfo(String attrName, int attrLen, ConstantPool cp) {
		switch (attrName) {
			case "Code": return new CodeAttribute(cp);
			case "ConstantValue": return new ConstantValueAttribute();
			case "Deprecated": return new DeprecatedAttribute();
			case "Exceptions": return new ExceptionsAttribute();
			case "LineNumberTable": return new LineNumberTableAttribute();
			case "LocalVariableTable": return new LocalVariableTableAttribute();
			case "SourceFile": return new SourceFileAttribute(cp);
			case "Synthetic": return new SyntheticAttribute();
			default: return new UnparsedAttribute(attrName,attrLen);
		}
	}
}
class MarkerAttribute extends AttributeInfo{
	@Override
	public void readInfo(ClassReader cr) {}
}
class UnparsedAttribute extends AttributeInfo{
	private String name;
	private int length;
	private byte[] info;
	public UnparsedAttribute(String name,int length) {
		this.name = name;
		this.length = length;
	}
	@Override
	public void readInfo(ClassReader cr) {
		this.info = cr.readBytes(length);
	}
}
class DeprecatedAttribute extends MarkerAttribute{}
class SyntheticAttribute extends MarkerAttribute{}
class SourceFileAttribute extends AttributeInfo{
	private ConstantPool cp;
	private short sourceFileIndex;
	public SourceFileAttribute(ConstantPool cp) {
		this.cp = cp;
	}
	@Override
	public void readInfo(ClassReader cr) {
		this.sourceFileIndex = cr.readUnit16();
	}
	public String getFileName(){
		return this.cp.getUtf8(sourceFileIndex);
	}
}
class ConstantValueAttribute extends AttributeInfo{
	private short constantAttributeIndex;
	@Override
	public void readInfo(ClassReader cr) {
		this.constantAttributeIndex = cr.readUnit16();
	}
	public short getConstantAttributeIndex(){
		return this.constantAttributeIndex;
	}
}
class CodeAttribute extends AttributeInfo{
	private ConstantPool cp;
	private short maxStack;
	private short maxLocals;
	private byte[] code;
	private ExeceptionTableEntry[] exceptionTable;
	private AttributeInfo[] attributes;
	public CodeAttribute(ConstantPool cp) {
		this.cp = cp;
	}
	@Override
	public void readInfo(ClassReader cr) {
		this.maxStack = cr.readUnit16();
		this.maxLocals = cr.readUnit16();
		int codeLen = cr.readUnit32();
		this.code = cr.readBytes(codeLen);
		this.exceptionTable = readExceptionTable(cr);
		this.attributes = readAttributes(cr, cp);
	}
	private ExeceptionTableEntry[] readExceptionTable(ClassReader cr) {
		int exceptionTableLength = cr.readUnit16()&0xffff;
		ExeceptionTableEntry[] exceptionTable = new ExeceptionTableEntry[exceptionTableLength];
		for(int i=0;i<exceptionTableLength;i++){
			ExeceptionTableEntry exception = new ExeceptionTableEntry();
			exception.startPc = cr.readUnit16();
			exception.endPc = cr.readUnit16();
			exception.handlerPc = cr.readUnit16();
			exception.catchType = cr.readUnit16();
			exceptionTable[i] = exception;
		}
		return exceptionTable;
	}
	static class ExeceptionTableEntry{
		protected short startPc;
		protected short endPc;
		protected short handlerPc;
		protected short catchType;
	}
}
class ExceptionsAttribute extends AttributeInfo{
	private short[] exceptionIndexTable;
	@Override
	public void readInfo(ClassReader cr) {
		this.exceptionIndexTable = cr.readUnit16s();
	}
	public short[] getExceptionIndexTable() {
		return exceptionIndexTable;
	}
}
class LineNumberTableAttribute extends AttributeInfo{
	private LineNumberTableEntry[] lineNumberTable;
	@Override
	public void readInfo(ClassReader cr) {
		int lineNumberTableLength = cr.readUnit16()&0xffff;
		lineNumberTable = new LineNumberTableEntry[lineNumberTableLength];
		for(int i=0;i<lineNumberTableLength;i++){
			LineNumberTableEntry lineNumberTableEntry = new LineNumberTableEntry();
			lineNumberTableEntry.startPc = cr.readUnit16();
			lineNumberTableEntry.lineNumber = cr.readUnit16();
			lineNumberTable[i] = lineNumberTableEntry;
		}
	}
	static class LineNumberTableEntry{
		protected short startPc;
		protected short lineNumber;
	}
}
class LocalVariableTableAttribute extends AttributeInfo{
	private LocalVariableTableEntry[] localVariableTable;
	@Override
	public void readInfo(ClassReader cr) {
		int localVariableTableLength = cr.readUnit16()&0xffff;
		localVariableTable = new LocalVariableTableEntry[localVariableTableLength];
		for(int i=0;i<localVariableTableLength;i++){
			LocalVariableTableEntry localVariableTableEntry = new LocalVariableTableEntry();
			localVariableTableEntry.startPc = cr.readUnit16();
			localVariableTableEntry.lineNumber = cr.readUnit16();
			localVariableTable[i] = localVariableTableEntry;
		}
	}
	static class LocalVariableTableEntry{
		protected short startPc;
		protected short lineNumber;
	}
}
