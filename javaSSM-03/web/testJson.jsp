<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>json交互</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js">

</script>
<script type="text/javascript">
    function requestJson(){
        $.ajax({
            type:'post',
            url:'${pageContext.request.contextPath}/requestJson',
            contentType:'application/json;charset:utf-8',
            data:'{"username":"华为手机","userpassword":"3000"}',
            success:function (date){
                alert(date.username+","+date.userpassword);
            }
        })
    }
    function requestNoJson(){
            $.ajax({
                type:'post',
                url:'${pageContext.request.contextPath}/requestNoJson',
                data:'username=苹果手机&userpassword=2000',
                success:function (date){
                    alert(date.username+","+date.userpassword);
                }
            })
    }
</script>
<body>
<input type="button"  value="请求是json格式,输出json字符串" onclick="requestJson()">
<input type="button" value="请求是key/value" onclick="requestNoJson()">
</body>
</html>
