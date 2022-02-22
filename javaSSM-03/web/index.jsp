<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>无标题文档</title>
    <link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/WebCalendar.js" type="text/javascript"></script>
</head>

<body>
<div class="wrap">
    <!-- main begin-->
    <div class="sale">
        <h1 class="lf">在线拍卖系统</h1>
        <div class="logout right"><a href="${pageContext.request.contextPath}/doLogout" title="注销">注销</a></div>
        <div class="right rulse">当前用户是：<span class="blue strong">${sessionScope.user.username}</span></div>
    </div>
    <div class="forms">
        <form id="form_query" action="${pageContext.request.contextPath}/queryAuction" method="post">
            <input type="hidden" name="pageNum" id="pageNum" value="1">
            <label for="name">名称</label>
            <input name="auctionname" value="${condition.auctionname}" type="text" class="nwinput" id="name"/>
            <label for="names">描述</label>
            <input name="auctiondesc" value="${condition.auctiondesc}" type="text" id="names" class="nwinput"/>

            <label for="time">开始时间</label>
            <input name="auctionstarttime"
                   value="<fmt:formatDate value="${condition.auctionstarttime}" pattern="yyyy-MM-dd hh:mm:ss"/>"
                   type="text" id="time" class="nwinput" onclick="selectDate(this,'yyyy-MM-dd hh:mm:ss')"/>

            <label for="end-time">结束时间</label>
            <input name="auctionendtime"
                   value="<fmt:formatDate value="${condition.auctionendtime}" pattern="yyyy-MM-dd hh:mm:ss"/>"
                   type="text" id="end-time" class="nwinput" onclick="selectDate(this,'yyyy-MM-dd hh:mm:ss')"/>

            <label for="price">起拍价</label>
            <input name="auctionstartprice" value="${condition.auctionstartprice}" type="text" id="price"
                   class="nwinput"/>
            <input name="" type="submit" value="查 询" class="spbg buttombg f14  sale-buttom"/>
        </form>
        <c:if test="${sessionScope.user.userisadmin==1}">
            <input type="button" value="发布" onclick="location.href='addAuction.jsp'" class="spbg buttombg f14  sale-buttom buttomb"/>
        </c:if>
        <c:if test="${sessionScope.user.userisadmin==0}">
            <input type="button" value="竞拍结果" onclick="location.href='${pageContext.request.contextPath}/toResultAuction'" class="spbg buttombg f14  sale-buttom buttomb"/>
        </c:if>
    </div>

    <div class="items">
        <ul class="rows even strong">
            <li>名称</li>
            <li class="list-wd">描述</li>
            <li>开始时间</li>
            <li>结束时间</li>
            <li>起拍价</li>
            <li class="borderno">操作</li>
        </ul>

        <c:forEach var="auction" items="${auctionList}" varStatus="stauts">
            <input type="hidden" name="id" >
             <ul
                    <c:if test="${stauts.index%2==0}">class="rows"</c:if>
                    <c:if test="${stauts.index%2!=0}">class="rows even"</c:if>
            >
                <li>${auction.auctionname}</li>
                <li class="list-wd">${auction.auctiondesc}</li>
                <li>
                    <fmt:formatDate value="${auction.auctionstarttime}" pattern="yyyy-MM-dd hh:mm:ss"/>
                </li>
                <li>
                    <fmt:formatDate value="${auction.auctionendtime}" pattern="yyyy-MM-dd hh:mm:ss"/>
                </li>
                <li>
                        ${auction.auctionstartprice}
                </li>
                <li class="borderno red">
                    <c:if test="${sessionScope.user.userisadmin==1}">
                        <a href="${pageContext.request.contextPath}/updateAuction/${auction.auctionid}" onclick="dele();">修改</a>&nbsp; |&nbsp;
                        <a href="${pageContext.request.contextPath}/deleteAuction/${auction.auctionid}"  onclick="abc();">删除</a>
                    </c:if>
                    <c:if test="${sessionScope.user.userisadmin==0}">
                        <a href="${pageContext.request.contextPath}/toauctionDetail/${auction.auctionid}">竞拍</a>
                    </c:if>
                </li>
            </ul>
        </c:forEach>
        <div class="page">

            【当前第 ${pageInfo.pageNum} 页,总共 ${pageInfo.pages} 页,总记录数${pageInfo.total}条】
            <a href="javascript:jumpPage(1)">首页</a>
            <a href="javascript:jumpPage(${pageInfo.prePage})">上一页</a>
            <a href="javascript:jumpPage(${pageInfo.nextPage})">下一页</a>
            <a href="javascript:jumpPage(${pageInfo.lastPage})">尾页</a>
        </div>
    </div>
    <script type="text/javascript">

        function jumpPage(page_num) {
            //先修改访问的页数
            document.getElementById("pageNum").value = page_num;
            //表单外手动提交表单
            document.getElementById("form_query").submit();
        }

        function abc() {

            if (confirm("你真的确认要删除吗？请确认")) {

                return true;
            } else {
                return false;
            }

        };

        function dele() {
            if (confirm("你真的确认要修改吗？请确认")) {
                return true;
            } else {
                return false;
            }
        }
    </script>
    <!-- main end-->
</div>
</body>
</html>
