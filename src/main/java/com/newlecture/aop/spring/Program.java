package com.newlecture.aop.spring;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Program {

	public static void main(String[] args) {
		Calculator origin = new NewlecCalculator();

		//Proxy�� �����ؼ� ���� �־��� ������ ����.
		
		
		int data = cal.add(3, 4);
		
		System.out.println(data);

	}

}

//�ڹٸ� ������ ������ ���
//calculator�� ������ ��¥(proxy)�� ����.
//Proxy.newProxyInstance(loader, interfaces, h);  h: �ڵ鷯 - ��������
