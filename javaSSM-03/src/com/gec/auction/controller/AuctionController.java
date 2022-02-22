package com.gec.auction.controller;

import com.gec.auction.pojo.*;
import com.gec.auction.service.AuctionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class AuctionController {
    @Autowired
    private AuctionService auctionService;
    public static final int PAGE_SIZE = 10;

    @RequestMapping("/queryAuction")
    public ModelAndView quertAuctionAll(@RequestParam(defaultValue = "1") Integer pageNum, @ModelAttribute("condition") Auction auction) {
        ModelAndView mv = new ModelAndView();
        PageHelper.startPage(pageNum, PAGE_SIZE);
        List<Auction> auctionList = auctionService.queryAuctionAll(auction);
        PageInfo<Auction> pageInfo = new PageInfo<>(auctionList);
        mv.addObject("pageInfo", pageInfo);
        mv.addObject("auctionList", auctionList);
        mv.setViewName("index");
        //postHandle
        return mv;  //afterCompletion
    }

    @RequestMapping("/publishAuctions")
    public String publishAuctions(Auction auction, MultipartFile pic, HttpSession session) {
        //1.文件上传,把文件保存在tomcat文件中
        try {
            if (pic.getSize() > 0) {
                //把pic二进制数据保存在tomcat目录下,获取upload下的绝对路径 OriginalFilename()//源文件名
                String path = session.getServletContext().getRealPath("upLoad");
                System.out.println(path);
                File targetFile = new File(path, pic.getOriginalFilename());
                pic.transferTo(targetFile);//核心代码

                //设置图片参数
                auction.setAuctionpic(pic.getOriginalFilename());
                auction.setAuctionpictype(pic.getContentType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        auctionService.addAuction(auction);
        return "redirect:/queryAuction";
    }

    @RequestMapping("/updateAuction/{id}")
    public ModelAndView selectByIdAcution(@PathVariable int id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("auction", auctionService.selectByIdAuction(id));
        mv.setViewName("updateAuction");
        return mv;
    }

    @RequestMapping("/submitUpdateAuction")
    public String submitUpdateAuction(Auction auction, MultipartFile pic, HttpSession session) {
        //1.文件上传,把文件保存在tomcat文件中
        try {
            if (pic.getSize() > 0) {
                //把pic二进制数据保存在tomcat目录下,获取upload下的绝对路径 OriginalFilename()//源文件名
                String path = session.getServletContext().getRealPath("upLoad");

                File oldFile = new File(path, auction.getAuctionpic());
                if (oldFile.exists()) {
                    oldFile.delete();
                }
                File targetFile = new File(path, pic.getOriginalFilename());
                pic.transferTo(targetFile);//核心代码

                //设置图片参数
                auction.setAuctionpic(pic.getOriginalFilename());
                auction.setAuctionpictype(pic.getContentType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        auctionService.updateAuction(auction);
        return "redirect:/queryAuction";
    }

    @RequestMapping("/deleteAuction/{auctionid}")
    public String deleteAuction(@PathVariable Integer auctionid) {
        auctionService.deleteAuctionrecord(auctionid);
        return "redirect:/queryAuction";
    }

    @RequestMapping("/toauctionDetail/{id}")
    public ModelAndView toauctionDetail(@PathVariable int id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("auctionDetail", auctionService.findAuctionAndRecodeById(id));
        mv.setViewName("auctionDetail");
        return mv;
    }

    @RequestMapping("/saveAuctionRecord")
    public String saveAuctionRecord(Auctionrecord auctionrecord, HttpSession session) throws Exception {
        //设置当前竞拍时间
        auctionrecord.setAuctiontime(new Date());
        //设置竞拍人
        User user = (User) session.getAttribute("user");//通过session那对象userid
        auctionrecord.setUserid(user.getUserid());
        auctionService.addAuctionrecord(auctionrecord);
        return "redirect:/toauctionDetail/" + auctionrecord.getAuctionid();
    }

    @RequestMapping("/toResultAuction")
    public ModelAndView toResultAuction(Auction auction, AuctionCustom auctionCustom) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("auctionCustom", auctionService.findAuctionByEnding(auctionCustom));
        mv.addObject("auction", auctionService.findAuctionByDoing(auction));
        mv.setViewName("Result");
        return mv;
    }

}
