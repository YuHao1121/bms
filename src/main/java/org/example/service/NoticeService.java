package org.example.service;

import org.example.entity.Notices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoticeService {
    //添加公告
    void saveNotice(Notices notices);

    //删除公告
    void deleteNotice(Integer noticeId);

    //修改公告
    void updateNotice(Notices notices);

    //查看公告
    List<Notices> queryNotice() ;

    //根据标题查找公告
    Notices findNoticeByName(String name);

    Notices findNoticeById(Integer noticeId);
}
