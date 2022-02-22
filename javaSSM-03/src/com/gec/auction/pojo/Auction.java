package com.gec.auction.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auction implements Serializable {
    private Integer auctionid;

    private String auctionname; //商品名称

    private BigDecimal auctionstartprice;//起拍价格

    private BigDecimal auctionupset;//结束价格

    private Date auctionstarttime;

    private Date auctionendtime;

    private String auctionpic;

    private String auctionpictype;

    private String auctiondesc; //商品描述

    private Auctionrecord auctionrecord;

    private List<Auctionrecord> auctionrecordList;//多方映射

    private static final long serialVersionUID = 1L;

}