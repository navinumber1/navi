<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>图书入库</title>
</head>
<body>
<form action="addBooksumbit" method="post">
    <p>
        图书类型:
        <select name="categoryId">
            <c:forEach var="bookname" items="${booknameList}">
                <option value="${bookname.id}">
                        ${bookname.name}
                </option>
            </c:forEach>
        </select>
    </p>
    <p>图书名称: <input type="text" name="name"></p>
    <p>出版日期: <input type="text" name="publishTime">(格式:yyyy-MM-dd)</p>
    <p>价格: <input type="text" name="price"></p>
    <p>出版社 :<input type="text" name="publishName"></p>
    <input type="submit" value="保 存">
    <input type="reset" value="重置">
    <input type="button" value="返回" onclick="location='qeryBook'"/>
</form>
</body>
</html>
