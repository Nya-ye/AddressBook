package com.lanou.bean;

import com.lanou.bean.Pinyin4j;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Contact {
	static private Pinyin4j py = new Pinyin4j();
	private String name;
	private String group;
	private String phone;
	
	
	public Contact() {
		super();
	}
	public Contact(String name, String phone) throws BadHanyuPinyinOutputFormatCombination {
		if (phone.length() != 11) {
			System.out.println("手机号输入错误，请重新输入！");
		} else {
			this.name = name;
			this.phone = phone;
			this.group = py.toPinYinUppercaseInitials(this.name);
		}
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	@Override
	public String toString() {
		return name + "(" + phone + ")";
	}
	
	

}
