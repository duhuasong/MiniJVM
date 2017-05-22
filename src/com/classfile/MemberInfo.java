package com.classfile;

public class MemberInfo {
	private ConstantPool cp;
	private short accessFlags;
	private short nameIndex;
	private short descriptionIndex;
	private AttributeInfo[] attributes;
	public void setCp(ConstantPool cp) {
		this.cp = cp;
	}
	public void setAccessFlags(short accessFlags) {
		this.accessFlags = accessFlags;
	}
	public void setNameIndex(short nameIndex) {
		this.nameIndex = nameIndex;
	}
	public void setDescriptionIndex(short descriptionIndex) {
		this.descriptionIndex = descriptionIndex;
	}
	public void setAttributes(AttributeInfo[] attributes) {
		this.attributes = attributes;
	}
	public String getName() {
		return this.cp.getUtf8(this.nameIndex);
	}
	public String getDescription() {
		return this.cp.getUtf8(this.descriptionIndex);
	}
}
