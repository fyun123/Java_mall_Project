<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>会员登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

    <style>
        body {
            margin-top: 20px;
            margin: 0 auto;
        }

        .carousel-inner .item img {
            width: 100%;
            height: 300px;
        }

        .container .row div {
            /* position:relative;
            float:left; */
        }

        font {
            color: #666;
            font-size: 22px;
            font-weight: normal;
            padding-right: 17px;
        }
    </style>
</head>
<body>


<%@ include file="/jsp/header.jsp" %>


<div class="container"
     style="width:100%;height:460px;background:#FF2C4C url('${pageContext.request.contextPath}/img/loginbg.jpg') no-repeat;">
    <div class="row">
        <div class="col-md-7">
            <!--<img src="image/login.jpg" width="500" height="330" alt="会员登录" title="会员登录">-->
        </div>

        <div class="col-md-5">
            <div style="width:440px;border:1px solid #E7E7E7;padding:20px 0 20px 30px;border-radius:5px;margin-top:60px;background:#fff;">
                <span>会员登录</span>USER LOGIN
                <div>${msg}</div>
                <form class="form-horizontal" method="post"
                      action="${pageContext.request.contextPath}/userServlet?method=userLogin">

                    <div class="form-group">
                        <label for="username" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="username" placeholder="请输入用户名" name="username"
                                   value="${remUser}"/>
                            <input type="hidden" name="method" value="userLogin"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" id="inputPassword" placeholder="请输入密码"
                                   name="password"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="loginCheckCode" class="col-sm-2 control-label">验证码</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="loginCheckCode" name="loginCheckCode"
                                   placeholder="请输入验证码">
                        </div>
                        <div class="col-sm-3">
                            <img id="checkCodeImg" src="${pageContext.request.contextPath}/checkCodeServlet"/>
                        </div>

                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="autoLogin" value="yes"> 自动登录
                                </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <label>
                                    <input type="checkbox" name="remUser" value="yes"> 记住用户名
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <input type="submit" width="100" value="登录" name="submit" border="0"
                                   style="background: url('${pageContext.request.contextPath}/img/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
                                           height:35px;width:100px;color:white;">
                            <span id="submit_span"></span>
                        </div>
                        <span id="loginError"></span>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%--页脚--%>
<%@ include file="/jsp/footer.jsp" %>

</body>

<script>
    window.onload = function () {
        //点击更新验证码
        document.getElementById("checkCodeImg").onclick = function () {
            this.src = "${pageContext.request.contextPath}/checkCodeServlet?time=" + new Date().getTime();
        };
        var span = $("#submit_span");
        span.css("color", "red");
        span.html("${requestScope.registCheckCodeError}");
        var span1 = $("#loginError");
        span1.css("color", "red");
        span1.html("${requestScope.loginError}");
    }
</script>

</html>