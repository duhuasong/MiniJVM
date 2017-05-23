package com.classfile;

import java.util.Arrays;

import com.utils.BytesUtil;

public class ClassReader {
	private byte[] bytes;
	private int pos;
	private int count;
	public ClassReader(byte[] bytes){
		this.bytes = bytes;
		this.count = bytes.length;
		this.pos = 0;
	}
	public byte readUnit8(){
		checkSize(1);
		return bytes[pos++];
	}
	public short readUnit16(){
		checkSize(2);
		int curPos = pos;
		pos = pos + 2;
		return BytesUtil.bytesToShort(bytes, curPos);
	}
	public int readUnit32(){
		checkSize(4);
		int curPos = pos;
		pos = pos + 4;
		return BytesUtil.bytesToInt(bytes, curPos);
	}
	public long readUnit64(){
		checkSize(8);
		int curPos = pos;
		pos = pos + 8;
		return BytesUtil.bytesToLong(bytes, curPos);
	}
	public short[] readUnit16s(){
		checkSize(2);
		int count = readUnit16()&0xffff;//转成无符号数值
		checkSize(count<<1);
		short[] rShorts = new short[count];
		for(int i=0;i<count;i++){
			rShorts[i] = BytesUtil.bytesToShort(bytes, pos);
			pos = pos + 2;
		}
		return rShorts;
	}
	public byte[] readBytes(int length){
		checkSize(length);
		int curPos = pos;
		pos = pos + length;
		return Arrays.copyOfRange(bytes, curPos, pos);
	}
	private void checkSize(int length) { 
		if(length > count - pos)
			throw new IndexOutOfBoundsException("class文件已损坏！");
	}
}
