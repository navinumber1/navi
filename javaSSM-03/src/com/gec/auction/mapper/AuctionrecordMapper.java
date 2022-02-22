package com.gec.auction.mapper;

import com.gec.auction.pojo.Auctionrecord;
import com.gec.auction.pojo.AuctionrecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuctionrecordMapper {
    long countByExample(AuctionrecordExample example);

    int deleteByExample(AuctionrecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auctionrecord record);

    int insertOrUpdate(Auctionrecord record);

    int insertOrUpdateSelective(Auctionrecord record);

    int insertSelective(Auctionrecord record);

    List<Auctionrecord> selectByExample(AuctionrecordExample example);

    Auctionrecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auctionrecord record, @Param("example") AuctionrecordExample example);

    int updateByExample(@Param("record") Auctionrecord record, @Param("example") AuctionrecordExample example);

    int updateByPrimaryKeySelective(Auctionrecord record);

    int updateByPrimaryKey(Auctionrecord record);

    int updateBatch(List<Auctionrecord> list);

    int updateBatchSelective(List<Auctionrecord> list);

    int batchInsert(@Param("list") List<Auctionrecord> list);

    int deleteByAuctionid(@Param("auctionid")Integer auctionid);


}