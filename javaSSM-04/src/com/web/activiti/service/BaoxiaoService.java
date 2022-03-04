package com.web.activiti.service;

import com.web.activiti.pojo.Baoxiaobill;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import java.util.List;

public interface BaoxiaoService {

    List<Baoxiaobill> findBaoxiaoBillQuery();

    void deleteBaoxaio(Integer id);

    List<Comment> findCommentByBaoxiaoBill(long id);

    Baoxiaobill findBaoxiaoById(Integer id);

}
