package com.web.activiti.service.impl;

import com.web.activiti.mapper.BaoxiaobillMapper;
import com.web.activiti.pojo.Baoxiaobill;
import com.web.activiti.service.BaoxiaoService;
import com.web.activiti.utils.Constants;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BaoxiaoServiceImpl implements BaoxiaoService {
    @Autowired
    private HistoryService historyService;
    @Autowired
    private BaoxiaobillMapper baoxiaobillMapper;
    @Autowired
    private TaskService taskService;

    @Override
    public List<Baoxiaobill> findBaoxiaoBillQuery() {
        List<Baoxiaobill> list = baoxiaobillMapper.selectByExample(null);
        return list;
    }

    @Override
    public void deleteBaoxaio(Integer id) {
        baoxiaobillMapper.deleteByPrimaryKey(id);
    }

    //根据报销单ID查询历史批注
    @Override
    public List<Comment> findCommentByBaoxiaoBill(long id) {
        String bussiness_key = Constants.LEAVEBILL_KEY + "." + id;
        HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(bussiness_key).singleResult();
        List<Comment> commentList = taskService.getProcessInstanceComments(pi.getId());
        return commentList;
    }

    @Override
    public Baoxiaobill findBaoxiaoById(Integer id) {
        return baoxiaobillMapper.selectByPrimaryKey(id);
    }

}
