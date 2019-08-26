package com.lanou.test;

import java.util.List;
import java.util.Scanner;

import com.lanou.bean.AddressBook;
import com.lanou.bean.Contact;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Test {

	public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		AddressBook ab = new AddressBook();
		ab.initAddressBook(ab.load());
		
		ab.printAddressBook();
		
		boolean isExit = false;
		while (isExit == false) {
			showMenu();
			int choose = getChoose();
			switch (choose) {
			case 1:{
				System.out.print("请输入联系人的姓名：");
				String name = scanner.nextLine();
				System.out.print("请输入联系人的手机号：");
				String phone = scanner.nextLine();
				Contact c = new Contact(name, phone);
				System.out.println("------------------------------------------");
				ab.addContact(c);
				System.out.println("添加成功！");
				System.out.println();
				break;
			}
			case 2:{
				System.out.println("------------------------------------------");
				ab.printAddressBook();
				System.out.println();
				break;
			}
			case 3:{
				System.out.print("请输入您要查找的分组：");
				String group = scanner.nextLine();
				List<Contact> list = ab.findContactsByGroup(group);
				ab.showCotacts(list);
				System.out.println();
				break;
			}
			case 4:{
				System.out.print("请输入您要查询的姓名关键字：");
				String name = scanner.nextLine();
				List<Contact> list = ab.findContactsByName(name);
				ab.showCotacts(list);
				System.out.println();
				break;
			}
			case 5:{
				System.out.print("请输入您要查询的电话关键字：");
				String phone = scanner.nextLine();
				List<Contact> list = ab.findContactsByPhone(phone);
				ab.showCotacts(list);
				break;
			}
			case 6:{
				System.out.print("请输入您要修改的联系人：");
				String name = scanner.nextLine();
				System.out.print("请输入新的姓名：");
				String newName = scanner.nextLine();
				System.out.print("请输入新的电话:");
				String newPhone = scanner.nextLine();
				Contact c = new Contact(newName, newPhone);
				System.out.println("------------------------------------------");
				ab.modify(name, c);
				System.out.println("修改成功！");
				System.out.println();
				break;
			}
			case 7:{
				System.out.print("请输入要删除的联系人的姓名：");
				String name = scanner.nextLine();
				System.out.println("------------------------------------------");
				ab.deleteContact(name);
				System.out.println("删除成功！");
				System.out.println();
				break;
			}
			case 8:{
				System.out.println("您确定要退出吗？(Y/N)");
				String str = scanner.nextLine();
				if (str.equalsIgnoreCase("Y")) {
					isExit = true;
				}
				break;
			}
			case 9:{
				String str = ab.changeToJsonString();
				System.out.println(str);
				ab.save();
				break;
			}
			case 10:{
				String string = ab.changeToJsonString();
				ab.initAddressBook(string);
				break;
			}
			}
			
		}

	}
	
	
	public static int getChoose() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.print("请输入您的选择：");
		int choose = scanner.nextInt();
		while (true) {
			if (choose < 1 && choose > 10) {
				System.out.println("您的输入有误，请重新输入：");
				choose = scanner.nextInt();
			} else {
				break;
			}
		}
		return choose;
	}
	
	public static void showMenu() {
		System.out.println("=======通讯录=======");
		System.out.println("1.添加联系人");
		System.out.println("2.查看全部联系人");
		System.out.println("3.根据分组查看联系人");
		System.out.println("4.根据姓名关键字查看联系人");
		System.out.println("5.根据电话关键字查看联系人");
		System.out.println("6.修改联系人");
		System.out.println("7.删除联系人");
		System.out.println("8.退出");
		System.out.println("9.将联系人转换为JSON字符串");
		System.out.println("10.将包含联系人信息的JSON字符串转换为对象Map");
	}

}
