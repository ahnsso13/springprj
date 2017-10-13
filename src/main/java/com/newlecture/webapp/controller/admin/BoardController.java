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
	      
	      model.addAttribute("list", list); //�𵨿� ����Ʈ�� �������Ѵ�.

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
	   
	   //get���� ���� ��� , forward ���
	   @RequestMapping(value="notice/reg", method=RequestMethod.GET)   
	   public String noticeReg() {

	      return "admin.board.notice.reg";
	   }
	   
	   //post�� ���� ���, redirect ���
	   @RequestMapping(value="notice/reg", method=RequestMethod.POST)   
	   public String noticeReg(Notice notice, String aa, MultipartFile file,
			   HttpServletRequest request) throws IOException {
		   
		   //��¥ ��� ��� 1 :��¥ ��ü�� ���� ���
		   Date curDate = new Date();
		   //curDate.getYear(); --���� �� ����� ���� ����.
		   
		   //��¥ ��� ��� 2 :��¥�� ���ڷ� �ٲ㼭 ����ϰ� ���� ��
		   Calendar cal = Calendar.getInstance();
		   //Date curDate2 = cal.getTime(); --date�� ��� ���� ��
		   int year = cal.get(Calendar.YEAR);
		   
		   //��¥ ��� ��� 3 :�̷� �������� ��¥�� �̰� ���� ��
		   //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		   //SimpleDateFormat fmt = new SimpleDateFormat("yyyy"); //������ ��� ���� ���
		   //fmt.format(curDate);
		   
		   //�ϳ��� �۾����� ������� �Ѵ�. -Ʈ�����(������ ����)
		   String nextId = noticeDao.getNextId();
		   
		   //title = new String(title.getBytes("ISO-8859-1"), "UTF-8");
		   //System.out.println(title);
		   
		   ServletContext ctx = request.getServletContext();
		   //String path = ctx.getRealPath("/resource/customer/notice" + year + "/" + nextId);
		   String path = ctx.getRealPath(String.format("/resource/customer/notice/%d/%s", year, nextId));
		   
		   System.out.println(path);
		   
		   //��θ� �̿��� ���� ��ü ����
		   File f = new File(path);
		   
		   //�������� ������ ������ �������
		   if(!f.exists()) {
			   if(!f.mkdirs())
				   System.out.println("���丮�� ������ ���� �����ϴ�.");			   	
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

//�ѱ��� ���� ��� - �߸��о�� ��, 2����Ʈ�� �ٽ� �����ؾ� �Ѵ�.
//���Ϳ��� �ƿ� UTF-8�� �ٲ㼭 ����� �� �ֵ��� �Ѵ�.
//������ ���� ��ΰ� �ִ��� �˻� > ���:�غ����, ��ΰ� ������ ������ �� �� ����.
//

//file �Ӽ����� ������ input �±׿� ���� ���� �� ������ ���ε�� ���� �̸��� ��Ʈ�� Ÿ������ ��ȯ�Ѵ�.
//�� �޼ҵ尡 ��ȯ�ϴ� ���ϸ��� file �Ӽ��� ���� input �±׿��� ����ڰ� ������ ���� �̸��� �ƴϰ�
//����ڰ� ������ ������ ���� ���� ���� ������ ���� �Ǿ��� ���� ���ϸ��� ��ȯ�Ѵ�.