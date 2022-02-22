package com.gec.auction.Junit;


import com.gec.auction.mapper.AuctionCustomMapper;
import com.gec.auction.pojo.Auction;
import com.gec.auction.pojo.AuctionCustom;
import com.gec.auction.pojo.Auctionrecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:spring/applicationContext-*.xml"}) //用spring文件构建环境
public class TestAuctionMapper {
    @Autowired
    private AuctionCustomMapper auctionCustomMapper;

    @Test
    public void testQuery(){
        Auction auction = auctionCustomMapper.findAuctionAndRecodeById(20);
        System.out.println(auction);
        List<Auctionrecord> auctionrecordList = auction.getAuctionrecordList();
        for (Auctionrecord auctionrecord : auctionrecordList) {
            System.out.println(auctionrecord);
        }
    }

    @Test
    public void test1(){
        List<AuctionCustom> auctionByEnding = auctionCustomMapper.findAuctionByEnding();
        for (AuctionCustom auctionCustom : auctionByEnding) {
            System.out.println(auctionCustom.getAuctionname()+auctionCustom.getAuctionstarttime()+auctionCustom.getAuctionendtime()
            +auctionCustom.getAuctionstartprice()+auctionCustom.getAuctionPrice()+auctionCustom.getUserName());

        }
    }

    @Test
    public void test2(){
        List<Auction> auction = auctionCustomMapper.findAuctionByDoing();
        for (Auction auction1 : auction) {
            System.out.println(auction1.getAuctionname()+","+auction1.getAuctionstartprice()+","+auction1.getAuctionid());
            List<Auctionrecord> auctionrecordList = auction1.getAuctionrecordList();
            for (Auctionrecord auctionrecord : auctionrecordList) {
                System.out.println(auctionrecord.getAuctionprice()+",  "+auctionrecord.getUser().getUsername()+","+auctionrecord.getAuctionid());
            }
        }
    }
}
