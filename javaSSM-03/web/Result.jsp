<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>无标题文档</title>
    <link href="css/common.css" rel="stylesheet" type="text/css" />
    <link href="css/style.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="wrap">
    <!-- main begin-->
    <div class="sale">
        <h1 class="lf">拍卖结束的商品</h1>
        <div class="right rulse">当前用户是：<span class="blue strong">${sessionScope.user.username}</span></div>
        <div class="cl"></div>
    </div>
    <div class="items">
        <ul class="rows even strong">
            <li>名称</li>
            <li>开始时间</li>
            <li>结束时间</li>
            <li>起拍价</li>
            <li class="list-wd">成交价</li>
            <li class="borderno">买家</li>
        </ul>
        <c:forEach var="auctions" items="${auctionCustom}">
            <ul class="rows">
                <li>${auctions.auctionname}</li>
                <li>
                    <fmt:formatDate value="${auctions.auctionstarttime}" pattern="yyyy-MM-dd hh:mm:ss"/>
                </li>
                <li>
                    <fmt:formatDate value="${auctions.auctionendtime}" pattern="yyyy-MM-dd hh:mm:ss"/>
                </li>
                <li>${auctions.auctionstartprice}</li>
                <li class="list-wd">${auctions.auctionPrice}</li>
                <li class="borderno red">${auctions.userName}</li>
            </ul>
        </c:forEach>
    </div>

    <h1>拍卖中的商品</h1>
    <div class="items records">
        <ul class="rows even strong rowh">
            <li>名称</li>
            <li>开始时间</li>
            <li>结束时间</li>
            <li>起拍价</li>
            <li class="borderno record">出价记录</li>
            <div class="cl"></div>
        </ul>
        <c:forEach var="aution" items="${auction}">
            <ul class="rows">
                <li>${aution.auctionname}</li>
                <li>
                    <fmt:formatDate value="${aution.auctionstarttime}" pattern="yyyy-MM-dd hh:mm:ss"/>
                </li>
                <li>
                    <fmt:formatDate value="${aution.auctionendtime}" pattern="yyyy-MM-dd hh:mm:ss"/>
                </li>
                <li>${aution.auctionstartprice}</li>
                <li class="borderno blue record">
                   <c:forEach items="${aution.auctionrecordList}" var="aut">
                       <p>
                               ${aut.user.username} &nbsp;
                               ${aut.auctionprice}元
                       </p>
                   </c:forEach>
                </li>
                <div class="cl"></div>
            </ul>
        </c:forEach>
    </div>
    <!-- main end-->
    <div class="top10">
        <input type="button" value="返回列表"  onclick="location.href='${pageContext.request.contextPath}/queryAuction'"  class="spbg buttombg f14"/>
    </div>
</div>
</body>
</html>
