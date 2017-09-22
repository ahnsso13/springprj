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

//반환값: 뷰를 나타내는 주소여야 한다.
//ResponseBody를 써주면 뷰를 찾지 말고 리턴값을 반환해라.(이게 바디가 된다.)

