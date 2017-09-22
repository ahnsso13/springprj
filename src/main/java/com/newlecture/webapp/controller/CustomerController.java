package com.newlecture.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/customer/*")
public class CustomerController {
	
	@RequestMapping("notice")
	@ResponseBody
	public String notice(
			@RequestParam(value="p", defaultValue="1") Integer page, 
			@RequestParam(value="q", defaultValue="") String query) { 
		
		String output = String.format("p:%s, q:%s", page, query);
		
		return output;
	}
	
	@ResponseBody
	@RequestMapping("notice/{id}")
	public String noticeDetail(@PathVariable("id") String aaid){
		
		return aaid + "번째 공지사항";
	}
	
}

//공통부분 URL을 위에 먼저 써주기
//ResponseBody: 뷰를 가지 않고 여기서 찾아
//id : 여기에 값이 올 것이고 값이 오면 id 변수에 넣겠다.
//@PathVariable("id") 이 변수를 받도록