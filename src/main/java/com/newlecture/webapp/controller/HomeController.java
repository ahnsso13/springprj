package com.newlecture.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@RequestMapping("/index.html")
	@ResponseBody
	public String index(){
		
		return "hello index";
	}
	
		
}

//��ȯ��: �並 ��Ÿ���� �ּҿ��� �Ѵ�.
//ResponseBody�� ���ָ� �並 ã�� ���� ���ϰ��� ��ȯ�ض�.(�̰� �ٵ� �ȴ�.)

