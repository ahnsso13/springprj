package com.newlecture.webapp.dao.mybatis;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.newlecture.webapp.dao.NoticeDao;
import com.newlecture.webapp.dao.NoticeFileDao;
import com.newlecture.webapp.entity.NoticeFile;
import com.newlecture.webapp.entity.NoticeView;

public class MyBatisNoticeFileDao implements NoticeFileDao {
   
   @Autowired
   private SqlSessionTemplate sqlSession;

   @Override
   public List<NoticeFile> getListByNoticeId(String noticeId) {
      
      NoticeFileDao noticeFileDao = sqlSession.getMapper(NoticeFileDao.class);
      List<NoticeFile> list = noticeFileDao.getListByNoticeId(noticeId);
      
      return list;
   }

   @Override
   public int insert(NoticeFile noticefile) {
      
      NoticeFileDao noticeFileDao = sqlSession.getMapper(NoticeFileDao.class);
      int result = noticeFileDao.insert(noticefile);
      
      return result;
   }

   @Override
   public int update(NoticeFile noticefile) {
      NoticeFileDao noticeFileDao = sqlSession.getMapper(NoticeFileDao.class);
      int result = noticeFileDao.update(noticefile);
      
      return result;
   }

   @Override
   public int delete(String id) {
      NoticeFileDao noticeFileDao = sqlSession.getMapper(NoticeFileDao.class);
      int result = noticeFileDao.delete(id);
      
      return result;
   }

   

}