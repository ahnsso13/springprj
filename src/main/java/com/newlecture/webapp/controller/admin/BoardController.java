package com.newlecture.webapp.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.newlecture.webapp.dao.NoticeDao;
import com.newlecture.webapp.dao.NoticeFileDao;
import com.newlecture.webapp.entity.Notice;
import com.newlecture.webapp.entity.NoticeFile;
import com.newlecture.webapp.entity.NoticeView;

@Controller
@RequestMapping("/admin/board/*")
public class BoardController {
	
	   @Autowired
	   private NoticeDao noticeDao;
	   
	   @Autowired
	   private NoticeFileDao noticeFileDao;	   
	   
	   @RequestMapping("notice")   
	   public String notice(
	         @RequestParam(value="p", defaultValue="1") Integer page,
	         @RequestParam(value="f", defaultValue="title") String field,
	         @RequestParam(value="q", defaultValue="") String query, Model model) {
	   
		  List<NoticeView> list = noticeDao.getList(page, field, query);
	      
	      model.addAttribute("list", list); //모델에 리스트를 담아줘야한다.

	      return "admin.board.notice.list";
	   }
	   
	   @RequestMapping("notice/{id}")   
	   public String noticeDetail(
	         @PathVariable("id") String id, Model model) {
		   
		   model.addAttribute("n", noticeDao.get(id));
		   model.addAttribute("prev", noticeDao.getPrev(id));
		   model.addAttribute("next", noticeDao.getNext(id));

	      return "admin.board.notice.detail";
	   }
	   
	   //get으로 오는 경우 , forward 방식
	   @RequestMapping(value="notice/reg", method=RequestMethod.GET)   
	   public String noticeReg() {

	      return "admin.board.notice.reg";
	   }
	   
	   //post로 오는 경우, redirect 방식
	   @RequestMapping(value="notice/reg", method=RequestMethod.POST)   
	   public String noticeReg(Notice notice, String aa, MultipartFile file,
			   HttpServletRequest request) throws IOException {
		   
		   //날짜 얻는 방법 1 :날짜 자체만 얻을 경우
		   Date curDate = new Date();
		   //curDate.getYear(); --이제 이 방법은 쓰지 못함.
		   
		   //날짜 얻는 방법 2 :날짜를 문자로 바꿔서 사용하고 싶을 때
		   Calendar cal = Calendar.getInstance();
		   //Date curDate2 = cal.getTime(); --date를 얻고 싶을 때
		   int year = cal.get(Calendar.YEAR);
		   
		   //날짜 얻는 방법 3 :이런 형식으로 날짜를 뽑고 싶을 때
		   //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		   //SimpleDateFormat fmt = new SimpleDateFormat("yyyy"); //연도만 얻고 싶을 경우
		   //fmt.format(curDate);
		   
		   //하나의 작업으로 묶어줘야 한다. -트랜잭션(업무의 단위)
		   String nextId = noticeDao.getNextId();
		   
		   //title = new String(title.getBytes("ISO-8859-1"), "UTF-8");
		   //System.out.println(title);
		   
		   ServletContext ctx = request.getServletContext();
		   //String path = ctx.getRealPath("/resource/customer/notice" + year + "/" + nextId);
		   String path = ctx.getRealPath(String.format("/resource/customer/notice/%d/%s", year, nextId));
		   
		   System.out.println(path);
		   
		   //경로를 이용한 파일 객체 생성
		   File f = new File(path);
		   
		   //존재하지 않으면 폴더를 만들어줘
		   if(!f.exists()) {
			   if(!f.mkdirs())
				   System.out.println("디렉토리를 생성할 수가 없습니다.");			   	
		   }
		   
		   path += File.separator+file.getOriginalFilename();
		   File f2 = new File(path);
		   
		   InputStream fis = file.getInputStream();
		   OutputStream fos = new FileOutputStream(f2);
		   
		   byte[] buf = new byte[1024];
		   
		   int size = 0;
		   while((size = fis.read(buf)) > 0)
			   fos.write(buf, 0, size);
		   
		   fis.close();
		   fos.close();
  
		   
		   //file.getInputStream();
		   String fileName = file.getOriginalFilename();
		   System.out.println(fileName);
		   
		   String writerId = "newlec";
		   
		   System.out.println(notice.getTitle());
		  
		   notice.setWriterId(writerId);
		   int row = noticeDao.insert(notice);
		   
		   noticeFileDao.insert(new NoticeFile(null, fileName, nextId));
		   //int row = noticeDao.insert(title, content, writerId);
		   //noticeDao.insert(new Notice(title, content));
        		  
		   
	      return "redirect:../notice";	      
	   }
 
}

//한글이 깨질 경우 - 잘못읽어온 것, 2바이트씩 다시 정렬해야 한다.
//필터에서 아예 UTF-8로 바꿔서 담아줄 수 있도록 한다.
//파일을 위한 경로가 있는지 검사 > 경로:준비사항, 경로가 없으면 파일을 쓸 수 없다.
//

//file 속성으로 지정된 input 태그에 의해 서버 상에 실제로 업로드된 파일 이름을 스트링 타입으로 반환한다.
//이 메소드가 반환하는 파일명의 file 속성을 가진 input 태그에서 사용자가 지정한 파일 이름이 아니고
//사용자가 선택한 파일이 실제 서버 상의 폴더에 저장 되었을 때의 파일명을 반환한다.