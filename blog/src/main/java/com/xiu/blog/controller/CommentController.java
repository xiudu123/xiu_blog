package com.xiu.blog.controller;

import com.xiu.blog.pojo.Comment;
import com.xiu.blog.pojo.User;
import com.xiu.blog.service.BlogService;
import com.xiu.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author: 锈渎
 * @date: 2023/6/30 22:50
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;


    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable int blogId, Model model) {
        List<Comment> commentList = commentService.listCommentByBLogId(blogId);
        Map<Integer, List<Comment> > childrenComment = eachChildrenComment(commentList);
        Map<Integer, String> idToName = new HashMap<>();
        for(Comment comment : commentList)  idToName.put(comment.getId(), comment.getNickname());
        if(childrenComment.containsKey(-1)) Collections.reverse(childrenComment.get(-1));
        model.addAttribute("comments", childrenComment.get(-1));
        model.addAttribute("replyComments", childrenComment);
        model.addAttribute("idToName", idToName);
        return "blog :: commentList";
    }

    @PostMapping("/comments/submit")
    public String post(Comment comment, HttpSession session){
        comment.setCreateTime(new Date());
        comment.setAvatar(avatar);
        User user = (User) session.getAttribute("user");
        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            comment.setNickname(user.getNickname());
        }else {
            comment.setAvatar(avatar);
        }
        commentService.insertComment(comment);
        return "redirect:/comments/" + comment.getBlogId();
    }

    // map<parentId, comments>
    private Map<Integer, List<Comment>> eachChildrenComment(List<Comment> comments){
        Map<Integer, List<Comment> > childrenComment = new HashMap<>();
        for(Comment comment : comments){
            if(!childrenComment.containsKey(comment.getTopCommentId()))  childrenComment.put(comment.getTopCommentId(), new ArrayList<>());
            childrenComment.get(comment.getTopCommentId()).add(comment);
        }
        return childrenComment;
    }


}
