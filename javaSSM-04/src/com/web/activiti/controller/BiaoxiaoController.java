package com.web.activiti.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.activiti.pojo.Baoxiaobill;
import com.web.activiti.service.BaoxiaoService;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BiaoxiaoController {

    @Autowired
    private BaoxiaoService baoxiaoService;

    /**
     * 查询我的报销单
     */
    public static final int PAGE_SIZE = 8;

    @RequestMapping("/myBaoxiaoBill")
    public ModelAndView myBaoxiaoBill(@RequestParam(defaultValue = "1") Integer pageNum) {
        ModelAndView mv = new ModelAndView();
        PageHelper.startPage(pageNum, PAGE_SIZE);
        List<Baoxiaobill> baoxiaoBillQuery = baoxiaoService.findBaoxiaoBillQuery();
        PageInfo<Baoxiaobill> info = new PageInfo<>(baoxiaoBillQuery);
        mv.addObject("baoxiaoList", baoxiaoBillQuery);
        mv.addObject("pageInfo", info);
        mv.setViewName("baoxiaobill");
        return mv;
    }

    /**
     * 删除报销单
     */
    @RequestMapping("/BiaoxiaoAction_delete")
    public String BiaoxiaoAction_delete(Integer id) {
        baoxiaoService.deleteBaoxaio(id);
        return "redirect:/myBaoxiaoBill";
    }

    /**
     * 查询审核记录:
     * 1.报销任务办理详情
     * 2.报销批注信息列表
     */
    @RequestMapping("viewHisComment")
    public ModelAndView viewHisComment(Integer id) {
        ModelAndView mv = new ModelAndView();
        List<Comment> commentByBaoxiaoBill = baoxiaoService.findCommentByBaoxiaoBill(id);
        Baoxiaobill baoxiaoById = baoxiaoService.findBaoxiaoById(id);
        mv.addObject("baoxiaoBill", baoxiaoById);
        mv.addObject("commentList", commentByBaoxiaoBill);
        mv.setViewName("workflow_commentlist");
        return mv;
    }


}
