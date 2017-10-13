package com.newlecture.webapp.controller;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.newlecture.webapp.dao.NoticeDao;
import com.newlecture.webapp.entity.NoticeView;

// /customer/notice
// /customer/notice-detail?c=3
// /customer/notice/3
// www.cyworld.com/newlec

@Controller
@RequestMapping("/customer/*")
public class CustomerController {

	@Autowired
	private NoticeDao noticeDao;

	@RequestMapping("notice")
	public String notice(@RequestParam(value = "p", defaultValue = "1") Integer page,
			@RequestParam(value = "f", defaultValue = "title") String field,
			@RequestParam(value = "q", defaultValue = "") String query, Model model) {

		/*
		 * String output = String.format("p:%s, q:%s", page, query); output +=
		 * String.format("title : %s\n", list.get(0).getTitle());
		 */

		List<NoticeView> list = noticeDao.getList(page, field, query);

		model.addAttribute("list", list); // 모델에 리스트를 담아줘야한다.

		// return "customer/notice";
		return "customer.notice.list";
	}

	@RequestMapping("notice-ajax")
	@ResponseBody /* 뷰를 찾지 않고 함수를 출력 */
	public String noticeAjax(@RequestParam(value = "p", defaultValue = "1") Integer p,
			@RequestParam(value = "f", defaultValue = "title") String f,
			@RequestParam(value = "q", defaultValue = "") String q, Model model) {

		List<NoticeView> list = noticeDao.getList(p, f, q);

		String json = "";
		
		Gson gson = new Gson();
		json = gson.toJson(list);

/*		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append("{},");
		builder.append("{}");
		builder.append("]");

		json = builder.toString();*/

		return json;
	}

	@RequestMapping("notice/{id}")
	public String noticeDetail(@PathVariable("id") String id, Model model) {

		model.addAttribute("n", noticeDao.get(id));
		model.addAttribute("prev", noticeDao.getPrev(id));
		model.addAttribute("next", noticeDao.getNext(id));

		// return aaid+"번째 공지사항" + noticeView.getTitle();
		return "customer.notice.detail";
	}
}
