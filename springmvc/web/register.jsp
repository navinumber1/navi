<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户注册</title>
</head>
<body>
<form action="getDemo2" method="post">
    <h1>用户注册</h1>
    <p>
        姓名: <input type="text" name="username">
    </p>
    <p>
        生日: <input type="text" name="birthday">(格式:yyyy/MM/dd/,yyyy年/MM月dd/日,yyyy-MM-dd)
    </p>
    <p>
        性别: <input type="radio" name="sex" value="男">男 <input type="radio" name="sex" value="女">女
    </p>
    <p>
        学历: <select name="education">
        <option selected="selected" value="本科">本科</option>
        <option value="大专">大专</option>
        <option value="高中">高中</option>
    </select>
    </p>
    <p>
        爱好:
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
