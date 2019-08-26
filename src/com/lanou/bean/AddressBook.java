package com.lanou.bean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import java.util.Set;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lanou.bean.Contact;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class AddressBook {
	
	private Map<String, List<Contact>> map = new TreeMap<>();
	private Pinyin4j py = new Pinyin4j();
	
	public AddressBook() {
		super();
	}

	public void addContact(Contact c) {
		String group = c.getGroup();
		List<Contact> list = map.get(group);
		if (list == null) { //说明这个分区现在还没有
			list = new ArrayList<Contact>();
			list.add(c);
			map.put(group, list);
		} else { //如果有这个分区，直接添加联系人
			list.add(c);
		}
	}
	
	public void deleteContact(String name) throws BadHanyuPinyinOutputFormatCombination {
		String group = py.toPinYinUppercaseInitials(name);
		List<Contact> list = map.get(group);
		if (list != null) {
			//如果有这个分区
			Iterator<Contact> it = list.iterator();
			while (it.hasNext()) {
				Contact c = it.next();
				if (c.getName().equals(name)) {
					it.remove();
				}
			}
			if (list.size() == 0) {//如果分区为空
				map.remove(group);
			}
		}
	}
	
	public void modify(String name, Contact c) throws BadHanyuPinyinOutputFormatCombination {
		deleteContact(name);
		addContact(c);
	}
	
	public List<Contact> findContactsByGroup(String group) {
		List<Contact> list = map.get(group);
		return list;
	}
	
	public List<Contact> findContactsByName(String name) {
		List<Contact> result = new ArrayList<>();
		Set<Entry<String, List<Contact>>> s = map.entrySet();
		Iterator<Entry<String, List<Contact>>> it = s.iterator();
		while (it.hasNext()) {
			Entry<String, List<Contact>> entry = it.next();
			List<Contact> list = entry.getValue();
			for (Contact c : list) {
				if (c.getName().contains(name)) {
					result.add(c);
				}
			}
		}
		return result.size() == 0 ? null : result;
		
//		Set<String> groups = map.keySet();
//		List<Contact> list = new ArrayList<>();
//		List<Contact> list2 = new ArrayList<>();
//		Iterator<String> it = groups.iterator();
//		while (it.hasNext()) {
//			String group = it.next();
//			list = map.get(group);
//			Iterator<Contact> it2 = list.iterator();
//			while (it2.hasNext()) {
//				Contact c = it2.next();
//				if (c.getName().equals(name)) {
//					list2.add(c);
//				}
//			}
//		}
//		return list2;
	}
	
	public List<Contact> findContactsByPhone(String phone) {
		Set<Entry<String, List<Contact>>> s = map.entrySet();
		List<Contact> result = new ArrayList<>();
		Iterator<Entry<String, List<Contact>>> it = s.iterator();
		while (it.hasNext()) {
			Entry<String, List<Contact>> entry = it.next();
			List<Contact> list = entry.getValue();
			for (Contact c : list) {
				if (c.getPhone().contains(phone)) {
					result.add(c);
				}
			}
		}
		return result;
		
//		Set<String> groups = map.keySet();
//		List<Contact> list = new ArrayList<>();
//		List<Contact> list2 = new ArrayList<>();
//		Iterator<String> it = groups.iterator();
//		while (it.hasNext()) {
//			String group = it.next();
//			list = map.get(group);
//			Iterator<Contact> it2 = list.iterator();
//			while (it2.hasNext()) {
//				Contact c = it2.next();
//				if (c.getPhone().equals(phone)) {
//					list2.add(c);
//				}
//			}
//		}
//		return list2;
	}
	
	//打印通讯录
	public void printAddressBook() {
		Set<String> groups = map.keySet();
		List<Contact> list = new ArrayList<>();
		Iterator<String> it = groups.iterator();
		while (it.hasNext()) {
			String group = it.next();
			System.out.println("|" + group + "|");
			System.out.println("--------------------------------");
			list = map.get(group);
			for (Contact c : list) {
				System.out.println(c);
			}
			System.out.println("================================");
			System.out.println();
		}
	}
	
	public void showCotacts(List<Contact> list) {
		if (list == null) {
			System.out.println("没有匹配的结果！");
			return;
		}
		System.out.println("-----------------------");
		for (Contact c : list) {
			System.out.println(c);
		}
	}
	
	public String changeToJsonString() {
//		Map<String, Object> map2 = new TreeMap<>();
//		map2.put("contacts", map);
//		JSONObject obj = new JSONObject(map2);
//		String str = obj.toJSONString();
		String str = JSONObject.toJSONString(map);
		return str;
	}
	
	public void initAddressBook(String string) {
		
		JSONObject obj = JSON.parseObject(string);
		Object str = null;
		Map<String, List<Contact>> address = new TreeMap<>();
		for (int i = 0; i < 26; i++) {
			String index = Character.toString((char) ('A' + i));
			str = obj.get(index);
			if (str == null) {
				continue;
			}
			List<Contact> list = JSON.parseArray(str.toString(), Contact.class);
			for (Contact  c: list) {
				System.out.println(c);
			}
			address.put(index, list);
		}
		map = address;
	}
	
	public void save() {
		BufferedOutputStream bos = null;
		try {
			FileOutputStream fos = new FileOutputStream("C:\\Users\\Nya\\Desktop\\AddressBook.txt");
			bos = new BufferedOutputStream(fos);
			String string = changeToJsonString();
			bos.write(string.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String load() {
		String str = "";
		BufferedInputStream bis = null;
		try {
			FileInputStream fis = new FileInputStream("C:\\Users\\Nya\\Desktop\\AddressBook.txt");
			bis = new BufferedInputStream(fis);
			byte[] arr = new byte[1024];
			int length = 0;
			while ((length = bis.read(arr)) != -1) {
				str = new String(arr, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return str;
	}

}
