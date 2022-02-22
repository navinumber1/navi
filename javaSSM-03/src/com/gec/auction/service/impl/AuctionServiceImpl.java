package com.gec.auction.service.impl;

import com.gec.auction.mapper.AuctionCustomMapper;
import com.gec.auction.mapper.AuctionMapper;
import com.gec.auction.mapper.AuctionrecordMapper;
import com.gec.auction.pojo.*;
import com.gec.auction.service.AuctionService;
import com.gec.auction.utils.AuctionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Max;
import java.util.Date;
import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {
    @Autowired
    private AuctionMapper auctionMapper;
    @Autowired
    private AuctionrecordMapper auctionrecordMapper;
    @Autowired
    private AuctionCustomMapper auctionCustomMapper;

    @Override
    public List<Auction> queryAuctionAll(Auction auction) {
        AuctionExample example = new AuctionExample();
        AuctionExample.Criteria criteria = example.createCriteria();
        if (auction != null) {
            //商品名称,描述都是模糊查询
            if (auction.getAuctionname() != null && !"".equals(auction.getAuctionname())) {
                criteria.andAuctionnameLike("%" + auction.getAuctionname() + "%");
            }
            if (auction.getAuctiondesc() != null && !"".equals(auction.getAuctiondesc())) {
                criteria.andAuctiondescLike("%" + auction.getAuctiondesc() + "%");
            }
            //起始时间 >
            if (auction.getAuctionstarttime() != null) {
                criteria.andAuctionstarttimeGreaterThan(auction.getAuctionstarttime());
            }
            //结束时间<
            if (auction.getAuctionendtime() != null) {
                criteria.andAuctionendtimeLessThan(auction.getAuctionendtime());
            }
            //起拍价
            if (auction.getAuctionstartprice() != null) {
                criteria.andAuctionstartpriceGreaterThan(auction.getAuctionstartprice());
            }
            //起拍时间降序排序
            example.setOrderByClause("auctionstarttime desc");
        }
        return auctionMapper.selectByExample(example);
    }

    @Override
    public void addAuction(Auction auction) {
        auctionMapper.insertSelective(auction);
    }

    @Override
    public void updateAuction(Auction auction) {
        auctionMapper.updateByPrimaryKeySelective(auction);
    }

    @Override
    public Auction selectByIdAuction(Integer auctionid) {
        return auctionMapper.selectByPrimaryKey(auctionid);
    }

    //事务注解 事务传播途径
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteAuctionrecord(Integer id) {
        //先删除竞拍记录表的数据
        AuctionrecordExample example = new AuctionrecordExample();
        AuctionrecordExample.Criteria criteria = example.createCriteria();
        criteria.andAuctionidEqualTo(id);
        auctionrecordMapper.deleteByExample(example);
        //再删除商品记录
        auctionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Auction findAuctionAndRecodeById(Integer auctionId) {
        return auctionCustomMapper.findAuctionAndRecodeById(auctionId);
    }

    /**
     * 竞拍价格的业务规则
     * 1.判断竞拍时间,不能过期
     * 2.如果是首次竞价,价格不能低于起拍价
     * 3.如果是后续的总价,价格必须高于当前的最高价
     */
    @Override
    public void addAuctionrecord(Auctionrecord auctionrecord) throws Exception {
        //先查询出商品信息
        Auction auction = auctionCustomMapper.findAuctionAndRecodeById(auctionrecord.getAuctionid());
        //判断竞拍时间
        if (!auction.getAuctionendtime().after(new Date())) {  //过期时间
            throw new AuctionException("该商品拍卖时间已经结束");
        }
        //判断是否有竞拍记录
        if (auction.getAuctionrecordList() != null && auction.getAuctionrecordList().size() > 0) {
            Auctionrecord maxRecord = auction.getAuctionrecordList().get(0);
            if (auctionrecord.getAuctionprice().compareTo(maxRecord.getAuctionprice()) < 1) {
                throw new AuctionException("竞拍价格必须大于当前最大竞价");
            }

        } else {  //首次竞拍
            if (auctionrecord.getAuctionprice().compareTo(auction.getAuctionstartprice()) < 1) {
                throw new AuctionException("竞拍价格必须大于起拍价");
            }
        }
        auctionrecordMapper.insert(auctionrecord);
    }

    @Override
    public List<AuctionCustom> findAuctionByEnding(AuctionCustom auctionCustom) {
        return auctionCustomMapper.findAuctionByEnding();
    }

    @Override
    public List<Auction> findAuctionByDoing(Auction auction) {
        return auctionCustomMapper.findAuctionByDoing();
    }
}
