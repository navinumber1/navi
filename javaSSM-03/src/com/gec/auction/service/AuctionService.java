package com.gec.auction.service;

import com.gec.auction.pojo.Auction;
import com.gec.auction.pojo.AuctionCustom;
import com.gec.auction.pojo.Auctionrecord;

import java.util.List;

public interface AuctionService {
    List<Auction> queryAuctionAll(Auction auction);

    void addAuction(Auction auction);

    void updateAuction(Auction auction);

    Auction selectByIdAuction(Integer auctionid);

    void deleteAuctionrecord(Integer id);

    Auction findAuctionAndRecodeById(Integer auctionId);

    void addAuctionrecord(Auctionrecord auctionrecord) throws Exception;

    List<AuctionCustom> findAuctionByEnding(AuctionCustom auctionCustom);

    List<Auction> findAuctionByDoing(Auction auction);
}
