package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.entity.Notices;

import java.util.List;

@Mapper
public interface NoticeMapper {

    @Insert("insert into notices(name,content,create_time)"+
            "values (#{name},#{content},now())")
    void saveNotice(Notices notices);

    @Delete("delete from notices where notice_id=#{noticeId}")
    void deleteNotice(Integer noticeId);

    @Update("update notices set name=#{name},content=#{content},create_time=now() where notice_id=#{noticeId}")
    void updateNotice(Notices notices);

    @Select("select * from notices")
    List<Notices> queryAllNotices();

    @Select("select * from notices where name=#{name}")
    Notices queryNoticeByName(String name);

    @Select("select * from notices where notice_id=#{noticeId}")
    Notices findNoticeById(Integer noticeId);
}
