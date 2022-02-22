package com.gec.auction.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionCustom extends Auction {
    private Double auctionPrice;
    private String userName;
}
