<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>自定义转换日期</title>
</head>
<body>
<form action="getTime.action" method="post">
    <p>
        <input type="text" name="birthday"> (yyyy-MM-dd hh:mm:ss)
    </p>
    <p>
        <input type="checkbox" name="hobbies" value="reading">阅读
        <input type="checkbox" name="hobbies" value="dancing">跳舞
        <input type="checkbox" name="hobbies" value="singing">唱歌
        <input type="checkbox" name="hobbies" value="football">足球
    </p>
    <p>
        <input type="submit" value="提 交">
    </p>
</form>

</body>
</html>
