<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>图书管理系统</title>
</head>
<body>
<script type="text/javascript">
    function deleteItems() {
        if (confirm("是否删除选中的商品?")) {
            document.getElementById("listForm").submit();
        }
    }
</script>
<h1>图书管理系统</h1>
<form action="qeryBook" method="post">
    <p>按图书类别查询 <select name="categoryId">
        <option <c:if test="${categoryId==''}">selected</c:if> value="">不限类型 </option>
        <c:forEach items="${booknameList}" var="book">
            <option <c:if test="${categoryId==book.id}">selected </c:if> value="${book.id}">${book.name}</option>
        </c:forEach>
    </select> &nbsp;<input type="submit" value="查 询"></p>
</form>

<input type="button" value="批量删除" onclick="deleteItems()"/>
<input type="button" value="添加图书" onclick="location='qeryname'"/>
<form id="listForm" action="${pageContext.request.contextPath}/deleteBook" method="post">
    <table width="100%" border=1>
        <tr>
            <td></td>
            <td>图书名称</td>
            <td> 出版时间</td>
            <td>出版社</td>
            <td>价格</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${bookList}" var="book">
            <tr>
                <td><input type="checkbox" name="ids" value="${book.id}"></td>
                <td>${book.name}</td>
                <td>
                    <fmt:formatDate value="${book.publishTime}" pattern="yyyy-MM-dd"/>
                </td>
                <td>
                        ${book.publishName}
                </td>
                <td>
                        ${book.price}
                </td>
                <td>
                    <a href="selectById?id=${book.id}">
                        <c:if test="${book.categoryId=='1'}">
                            经济
                        </c:if>
                        <c:if test="${book.categoryId=='2'}">
                            军事
                        </c:if>
                        <c:if test="${book.categoryId=='3'}">
                            工业
                        </c:if>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/qeryBook?pageNum=1&categoryId=${categoryId}">首页 </a>
    <a href="${pageContext.request.contextPath}/qeryBook?pageNum=${pageInfo.prePage}&categoryId=${categoryId}"> 上一页</a>
    <a href="${pageContext.request.contextPath}/qeryBook?pageNum=${pageInfo.nextPage}&categoryId=${categoryId}"> 下一页</a>
    <a href="${pageContext.request.contextPath}/qeryBook?pageNum=${pageInfo.lastPage}&categoryId=${categoryId}"> 末页</a>
共${pageInfo.pages}页
    (${pageInfo.total})条记录
    当前页数${pageInfo.pageNum}
</form>

</body>
</html>
