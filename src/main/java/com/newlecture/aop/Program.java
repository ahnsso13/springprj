package com.newlecture.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Program {

	public static void main(String[] args) {
		Calculator origin = new NewlecCalculator();
		
		//int data = cal.add(3, 4);
		
		//Proxy를 생성해서 실제 주업무 로직을 위임.
		Calculator cal = (Calculator) Proxy.newProxyInstance(
				NewlecCalculator.class.getClassLoader(),
				new Class[] {Calculator.class}, 
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						System.out.println("사전처리 보조업무");
						
						Object result = method.invoke(origin, args);
						
						return result;
					}
				});
		
		int data = cal.add(3, 4);
		
		System.out.println(data);

	}

}

//자바만 있으면 가능한 경우
//calculator를 받지만 가짜(proxy)를 만들어냄.
//Proxy.newProxyInstance(loader, interfaces, h);  h: 핸들러 - 보조업무
