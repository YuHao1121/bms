package org.example.controller;

import org.example.entity.Notices;
import org.example.entity.Result;
import org.example.service.NoticeService;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notices")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    //新增公告
    @PostMapping("/save")
    public Result saveNotice(@RequestBody Notices notices) {

        // 验证标题和内容不能为空
        if (notices.getName() == null || notices.getName().trim().isEmpty()) {
            return Result.error("公告标题不能为空");
        }
        if (notices.getContent() == null || notices.getContent().trim().isEmpty()) {
            return Result.error("公告内容不能为空");
        }

        // 获取当前登录管理员 ID（假设在 ThreadLocal 中存储了管理员信息）
//        Integer adminId = ThreadLocalUtil.get("id");
//        if (adminId == null) {
//            return Result.error("用户未登录或无权限发布公告");
//        }
        Notices notice = noticeService.findNoticeByName(notices.getName());
        if(notice==null){
            noticeService.saveNotice(notices);
            Notices newNotice = noticeService.findNoticeByName(notices.getName());
            return  Result.success(newNotice);
        }
        return Result.error("该公告已发布过了");


    }

    //删除公告
    @DeleteMapping("/delete")
    public Result deleteNotice(Integer noticeId) {
        noticeService.deleteNotice(noticeId);
        return  Result.success();
    }

    //修改公告
    @PutMapping("/update")
    public Result updateNotice(@RequestBody Notices notices) {
            noticeService.updateNotice(notices);
            return  Result.success();
    }

    //查看所有公告
    @GetMapping("/list")
    public Result getNotices() {
        List<Notices> notices = noticeService.queryNotice();
        if(notices != null && !notices.isEmpty()) {
            return Result.success(notices);
        }
        return  Result.error("暂时没有系统公告发布");
    }

    //根据noticeId查看公告
    @GetMapping("/list/{notice_id}")
    public Result getNoticeById(@PathVariable("notice_id") Integer noticeId) {
        Notices notice = noticeService.findNoticeById(noticeId);
        if(notice!=null){
            return Result.success(notice);
        }
        return Result.error("没有该公告");
    }
}
