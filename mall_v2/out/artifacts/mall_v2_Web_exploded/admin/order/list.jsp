<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<HTML>
<HEAD>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css"/>
    <script type="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>

</HEAD>
<body>
<br>
<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/admin/order/list.jsp" method="post">
    <table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
        <TBODY>
        <tr>
            <td class="ta_01" align="center" bgColor="#afd1f3">
                <strong>订单列表</strong>
            </TD>
        </tr>

        <tr>
            <td class="ta_01" align="center" bgColor="#f5fafe">
                <table cellspacing="0" cellpadding="1" rules="all"
                       bordercolor="gray" border="1" id="DataGrid1"
                       style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
                    <tr
                            style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

                        <td align="center" width="5%">
                            序号
                        </td>
                        <td align="center" width="25%">
                            订单编号
                        </td>
                        <td align="center" width="5%">
                            订单金额
                        </td>
                        <td align="center" width="5%">
                            收货人
                        </td>
                        <td align="center" width="5%">
                            订单状态
                        </td>
                        <td align="center" width="55%">
                            订单详情
                        </td>
                    </tr>
                    <c:if test="${empty page.list }">
                        <tr>
                            <td colspan="6">暂无数据</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty page.list }">
                        <c:forEach items="${page.list}" var="o" varStatus="status">
                            <tr onmouseover="this.style.backgroundColor = 'white'"
                                onmouseout="this.style.backgroundColor = '#F5FAFE';">
                                <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                    width="5%">
                                        ${status.count}
                                </td>
                                <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                    width="25%">
                                        ${o.oid}
                                </td>
                                <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                    width="5%">
                                        ${o.total}
                                </td>
                                <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                    width="5%">
                                        ${o.name}
                                </td>
                                <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                    width="5%">
                                    <!-- 1=未付款、2=发货、3=已发货、4=订单完成 -->
                                    <c:if test="${o.state==1}">未付款</c:if>
                                    <c:if test="${o.state==2}">
                                        <a href="${pageContext.request.contextPath}/adminOrderServlet?method=updateOrderByOid&oid=${o.oid}">发货</a>
                                    </c:if>
                                    <c:if test="${o.state==3}">已发货,未签收</c:if>
                                    <c:if test="${o.state==4}">结束</c:if>
                                </td>
                                <td align="center" style="HEIGHT: 22px">
                                    <input type="button" value="订单详情" class="btn" id="${o.oid}"/>
                                    <table width="100%" border="1" class="myTb">
                                        <!--
												   <tr><th>商品</th><th>名称</th><th>数量</th><th>小计</th></tr>
												   <tr><td><img src="${pageContext.request.contextPath}/products/1/c_0001.jpg" width="50px"/></td><td>名称</td><td>数量</td><td>小计</td></tr>
												   <tr><td><img src="${pageContext.request.contextPath}/products/1/c_0001.jpg" width="50px"/></td><td>名称</td><td>数量</td><td>小计</td></tr>
												-->
                                    </table>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </table>
            </td>
        </tr>
        <tr align="center">
            <td colspan="7">
            </td>
        </tr>
        </TBODY>
    </table>
    <%@include file="/jsp/pageFile.jsp" %>
</form>
</body>
<script>
    $(function () {
        //页面加载完毕之后获取样式为.btn标签,为其绑定点击事件
        $(".btn").click(function () {
            //获取按钮文字
            var val = $(this).val();  //this.value
            //获取按钮上id的值
            var id = this.id;
            //alert(id);

            //获取按钮,约定:如果我们用的是JQUERYAPI获取到的对象,加前缀$.
            var $btn = $(this);

            if ("订单详情" == val) {
                //发送Ajax请求
                $.post("${pageContext.request.contextPath}/adminOrderServlet", {
                    "method": "findOrderByOidWithAjax",
                    "oid": id
                }, function (dt) {
                    //alert(dt);
                    //var $tb=$(this).next();
                    //alert(this);  //此处无用,JS打印对象:都是Object
                    //console.log(this); //火狐控制台下打印当前对象
                    //获取到当前按钮的下一个兄弟节点
                    var $tb = $btn.next("table");
                    //清空table元素
                    $tb.html("");
                    //alert($tb.attr("class")); //测试是否正确获取到table标签对象
                    $tb.append("<tr><th>商品</th><th>名称</th><th>数量</th><th>小计</th></tr>");
                    //JQUERY的方式遍历数组元素
                    $.each(dt, function (i, o) {
                        var tr = "<tr><td><img src='${pageContext.request.contextPath}/" + o.product.pimage + "' width='50px'/></td><td>" + o.product.pname + "</td><td>" + o.quantity + "</td><td>" + o.total + "</td></tr>";
                        $tb.append(tr);
                    });
                    //更改按钮文字
                    $btn.val("关闭");

                });

            }
            if ("关闭" == val) {
                var $tb = $btn.next("table");
                $tb.html("");
                $btn.val("订单详情");
            }
        });
    });
</script>
</HTML>