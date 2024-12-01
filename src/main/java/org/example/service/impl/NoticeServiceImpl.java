package org.example.service.impl;

import org.example.entity.Notices;
import org.example.mapper.NoticeMapper;
import org.example.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    //添加公告
    @Override
    public void saveNotice(Notices notices) {
        noticeMapper.saveNotice(notices);
    }

    //删除公告
    @Override
    public void deleteNotice(Integer noticeId) {
        noticeMapper.deleteNotice(noticeId);
    }

    //更新公告
    @Override
    public void updateNotice(Notices notices) {
        noticeMapper.updateNotice(notices);
    }

    //查看公告
    @Override
    public List<Notices> queryNotice() {
        return noticeMapper.queryAllNotices();
    }

    @Override
    public Notices findNoticeByName(String name) {
        return noticeMapper.queryNoticeByName(name);
    }

    @Override
    public Notices findNoticeById(Integer noticeId) {
        return noticeMapper.findNoticeById(noticeId);
    }
}
