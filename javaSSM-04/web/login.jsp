<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>OA办公自动化系统</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!--图标-->
    <link rel="stylesheet" href="<c:url value="/css/font-awesome.min.css"/>">

    <!--布局框架-->
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/util.css"/>">

    <!--主要样式-->
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
</head>

<style>

    .checkbox-item{
        display: inline-block;
        margin:0 20px;
        width:88px;
        height:35px;
        text-align: center;
    }
    /*样式1*/
    .checkone{
        position:relative;
        width:80px;
        height:30px;
        background: #333;
        border-radius:15px;
        -moz-box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px rgba(255, 255, 255, 0.2);
        -webkit-box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px rgba(255, 255, 255, 0.2);
        box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px rgba(255, 255, 255, 0.2);
    }
    .checkone:after{
        content: "";
        position: absolute;
        left:14px;
        top:14px;
        height: 2px;
        width: 52px;
        background: #111;
        border-radius: 50%;
        -moz-box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px rgba(255, 255, 255, 0.2);
        -webkit-box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px rgba(255, 255, 255, 0.2);
        box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px rgba(255, 255, 255, 0.2);
    }
    .checkone input[type="checkbox"]{
        visibility:hidden;
    }
    .checkone label{
        position: absolute;
        width:20px;
        height:20px;
        background: #f1f1f1;
        border-radius:50%;
        left:5px;
        top:5px;
        cursor: pointer;
        z-index: 1;
        transition: all 0.3s ease;
    }
    .checkone label:after{
        content: "";
        position: absolute;
        width:10px;
        height:10px;
        background: #333;
        border-radius: 50%;
        left:5px;
        top:5px;
        -moz-box-shadow: inset 0px 1px 1px black, 0px 1px 0px rgba(255, 255, 255, 0.9);
        -webkit-box-shadow: inset 0px 1px 1px black, 0px 1px 0px rgba(255, 255, 255, 0.9);
        box-shadow: inset 0px 1px 1px black, 0px 1px 0px rgba(255, 255, 255, 0.9);
    }
    #checkone:checked + label{
        left:55px;
    }
    #checkone:checked + label:after{
        background: #00bf00;
    }
.remember{
  position: absolute;
    left: 98px;
    top: 5px;
}
</style>
<script>
    function randomcode_refresh() {
        var pic = document.getElementById("randomcode_img");
        var i = Math.random();
        pic.src = "validatecode.jsp?id=" + i;
    }

</script>
<body>

<div class="login">
    <div class="container-login100">
        <div class="wrap-login100">
            <div class="login100-pic js-tilt" data-tilt>
                <img src="<c:url value="/imgs/img-01.png"/>" alt="IMG">
            </div>

            <form class="login100-form validate-form" action="${pageContext.request.contextPath}/login" method="post">
				<span class="login100-form-title" style="color:red;font-size:30px">
					OA办公系统
				</span>

                <div class="wrap-input100 validate-input">
                    <input class="input100" type="text" name="username"  value="danny" placeholder="请输入账户">
                    <span class="focus-input100"></span>
                    <span class="symbol-input100">
						<i class="fa fa-envelope" aria-hidden="true"></i>
					</span>
                </div>

                <div class="wrap-input100 validate-input">
                    <input class="input100" type="password"  name="password" value="123" placeholder="请输入密码">
                    <span class="focus-input100"></span>
                    <span class="symbol-input100">
						<i class="fa fa-lock" aria-hidden="true"></i>
					</span>
                </div>


                <div class="wrap-input100 validate-input">
                    验证码:
                    <input id="randomcode"  type="text" required  class="input50" name="randomcode" size="8"/>
                    <img
                            id="randomcode_img" src="${pageContext.request.contextPath}/validatecode.jsp"
                            width="56" height="20"/> <a
                        href=javascript:randomcode_refresh()>刷新</a>
                </div>

                <div class="content">
                    <section class="checkbox-item">
                        <div class="checkone">
                            <input type="checkbox" id="checkone" name="rememberMe"/>
                            <label for="checkone"></label>
                            <div class="remember">Rememberme</div>
                        </div>

                    </section>
                </div>
                <span style="color: red">${errorMsg}</span>
                <div class="container-login100-form-btn">
                    <button class="login100-form-btn">
                        登  &nbsp;陆
                    </button>
                </div>

                <div class="text-center p-t-136">
                    <a class="txt2" href="#">
                        还没有账号？立即注册
                        <i class="fa fa-long-arrow-right m-l-5" aria-hidden="true"></i>
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>