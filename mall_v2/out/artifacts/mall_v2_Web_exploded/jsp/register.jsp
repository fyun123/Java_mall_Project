<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>会员注册</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
    <style>

        .carousel-inner .item img {
            width: 100%;
            height: 300px;
        }

        .container .row div {
            /* position:relative;
            float:left; */
        }

        font {
            color: #3164af;
            font-size: 18px;
            font-weight: normal;
            padding: 0 10px;
        }
    </style>
</head>
<body>


<%@ include file="/jsp/header.jsp" %>


<div class="container" style="width:100%;background:url('${pageContext.request.contextPath}/img/regist_bg.jpg');">
    <div class="row">

        <div class="col-md-2"></div>


        <div class="col-md-8" style="background:#fff;padding:40px 80px;margin:30px;border:7px solid #ccc;">
            <span>会员注册</span>USER REGISTER
            <form class="form-horizontal" style="margin-top:5px;" method="post" action="${pageContext.request.contextPath}/userServlet?method=userRegist">
                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="username" placeholder="请输入用户名" name="username">
                        <input type="hidden" name="method" value="userRegist">
                    </div>
                    <span id="username_span"></span>
                </div>
                <div class="form-group">
                    <label for="inputPassword" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="inputPassword" placeholder="请输入密码"
                               name="password">
                    </div>
                </div>
                <div class="form-group">
                    <label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="confirmpwd" placeholder="请输入确认密码">
                    </div>
                    <span id="confirmpwd_span"></span>
                </div>
                <div class="form-group">
                    <label for="inputEmail" class="col-sm-2 control-label">Email</label>
                    <div class="col-sm-6">
                        <input type="email" class="form-control" id="inputEmail" placeholder="Email" name="email">
                    </div>
                </div>
                <div class="form-group">
                    <label for="usercaption" class="col-sm-2 control-label">姓名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="usercaption" placeholder="请输入姓名" name="name">
                    </div>
                </div>
                <div class="form-group opt">
                    <label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
                    <div class="col-sm-6">
                        <label class="radio-inline">
                            <input type="radio" name="sex" id="inlineRadio1" value="男" checked="checked"> 男
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="sex" id="inlineRadio2" value="女"> 女
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="dateTable" class="col-sm-2 control-label">出生日期</label>
                    <div class="col-sm-6">
                        <label>
                            <input type="date" class="form-control" name="birthday" id="dateTable">
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="myPhone" class="col-sm-2 control-label">手机</label>
                    <div class="col-sm-6">
                        <input id="myPhone" type="text" class="form-control" name="telephone">
                    </div>
                    <span id="phone_span"></span>
                </div>


                <div class="form-group">
                    <label for="checkCode" class="col-sm-2 control-label">验证码</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="checkCode" name="checkCode">

                    </div>
                    <div class="col-sm-2">
                        <img id="checkCodeImg" src="${pageContext.request.contextPath}/checkCodeServlet">
                    </div>

                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <input type="submit" width="100" value="注册" id="submit" name="submit" border="0"
                               style="background: url('${pageContext.request.contextPath}/img/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
                                       height:35px;width:100px;color:white;">
                        <span id="submit_span"></span>
                    </div>
                </div>
            </form>
        </div>

        <div class="col-md-2"></div>

    </div>
</div>

<%--页脚--%>
<%@ include file="/jsp/footer.jsp" %>

</body>
<%--
用户名校验
--%>
<script>
    $(function () {
        //页面加载完毕为id为username文本框绑定失去焦点事件
        $("#username").blur(function () {
            //获取到用户输入的信息
            var inputUsername = $(this).val();
            //发送Ajax
            $.post("userServlet?method=userExists", {username: inputUsername}, function (data) {
                //alert(data);
                var span = $("#username_span");
                if (data.userExsit) {
                    span.css("color", "red");
                    span.html(data.msg);
                } else {
                    span.css("color", "green");
                    span.html(data.msg);
                }
            });
        });
    });
</script>
<%--
密码确认
--%>
<script>
    $(function () {
        //页面加载完毕为id为username文本框绑定失去焦点事件
        $("#confirmpwd").blur(function () {
            //获取到用户输入的信息
            var confirmpwd = $(this).val();
            var inputPassword = $("#inputPassword").val();
            var span = $("#confirmpwd_span");
            if (confirmpwd === inputPassword && confirmpwd != null && inputPassword != null) {
                span.css("color", "green");
                span.html("密码输入通过");
            } else {
                span.css("color", "red");
                span.html("两次密码不一致,请重新输入");
            }
        });
    });
</script>
<script>
    $(function () {
        //页面加载完毕为id为username文本框绑定失去焦点事件
        $("#inputPassword").blur(function () {
            //获取到用户输入的信息
            var confirmpwd = $(this).val();
            var inputPassword = $("#confirmpwd").val();
            var span = $("#confirmpwd_span");
            if (confirmpwd === inputPassword && confirmpwd != null && inputPassword != null) {
                span.css("color", "green");
                span.html("密码输入通过");
            } else {
                span.css("color", "red");
                span.html("两次密码不一致,请重新输入");
            }
        });
    });
</script>
<%--
    验证手机号
--%>
<script>
    $(function () {
        //页面加载完毕为id为username文本框绑定失去焦点事件
        $("#myPhone").blur(function () {
            var myreg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;
            var span = $("#phone_span");
            if (!myreg.test($("#myPhone").val())) {
                span.css("color", "red");
                span.html("请输入正确手机号");
            } else {
                span.html("");
            }
        });
    });
</script>
<%--
    验证码校验
--%>
<script>
    window.onload = function () {
        //点击更新验证码
        document.getElementById("checkCodeImg").onclick = function () {
            this.src = "${pageContext.request.contextPath}/checkCodeServlet?time=" + new Date().getTime();
        };
        var span = $("#submit_span");
        span.css("color", "red");
        span.html("${requestScope.registCheckCodeError}");
    }
</script>
<script>
    function submitfuncyion() {
        url = "${pageContext.request.contextPath}/userServlet?method=userRegist?pass=true";
        window.location.href = url;
    }
</script>
</html>



