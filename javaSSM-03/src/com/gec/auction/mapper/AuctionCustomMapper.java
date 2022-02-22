package com.gec.auction.mapper;

import com.gec.auction.pojo.Auction;
import com.gec.auction.pojo.AuctionCustom;
import com.gec.auction.pojo.AuctionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuctionCustomMapper {

    Auction findAuctionAndRecodeById(Integer auctionId);

    List<AuctionCustom> findAuctionByEnding();

    List<Auction> findAuctionByDoing();
}