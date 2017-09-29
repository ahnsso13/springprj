package com.newlecture.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.newlecture.webapp.entity.Notice;
import com.newlecture.webapp.entity.NoticeView;

public interface NoticeDao {
   List<NoticeView> getList(int page, String field, String query);
   int getCount();
   NoticeView get(String id);
   int update(String id, String title, String content);
   NoticeView getPrev(String id);
   NoticeView getNext(String id);
}
// @Param("page")를 써주면 param1이 아닌 page로 쓸 수 있다.