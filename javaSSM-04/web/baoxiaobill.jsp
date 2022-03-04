<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>流程管理</title>

    <!-- Bootstrap -->
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="css/content.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="js/jquery.min.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>

<!--路径导航-->
<ol class="breadcrumb breadcrumb_nav">
    <li>首页</li>
    <li>报销管理</li>
    <li class="active">我的报销单</li>
</ol>
<!--路径导航-->

<div class="page-content">
    <form class="form-inline">
        <div class="panel panel-default">
            <div class="panel-heading">报销单列表</div>

            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th width="5%">ID</th>
                        <th width="10%">报销金额</th>
                        <th width="15%">标题</th>
                        <th width="20%">备注</th>
                        <th width="15%">时间</th>
                        <th width="10%">状态</th>
                        <th width="25%">操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="bill" items="${baoxiaoList}">
                        <tr>
                            <td>${bill.id}</td>
                            <td>${bill.money}</td>
                            <td>${bill.title}</td>
                            <td>${bill.remark}</td>
                            <td>
                                <fmt:formatDate value="${bill.creatdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td>
                                <c:if test="${bill.state==1}">
                                    审核中
                                </c:if>
                                <c:if test="${bill.state==2}">
                                    审核完成
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${bill.state==1}">
                                    <a href="${pageContext.request.contextPath }/viewHisComment?id=${bill.id}"
                                       class="btn btn-success btn-xs"><span
                                            class="glyphicon glyphicon-eye-open"></span> 查看审核记录</a>
                                    <a href="${pageContext.request.contextPath}/viewCurrentImageByBill?billId=${bill.id}"
                                       target="_blank"
                                       class="btn btn-success btn-xs"><span
                                       class="glyphicon glyphicon-eye-open"></span> 查看当前流程图</a>
                                </c:if>
                                <c:if test="${bill.state==2}">
                                    <a type="button" class="btn btn-danger btn-xs" onclick="del2(${bill.id})">
                                        <span class="glyphicon glyphicon-remove"></span> 删除
                                    </a>
                                    <a href="${pageContext.request.contextPath }/viewHisComment?id=${bill.id}"
                                       class="btn btn-success btn-xs"><span
                                            class="glyphicon glyphicon-eye-open"></span> 查看审核记录</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </form>
</div>

<!-- Button trigger modal -->

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">提示</h4>
            </div>
            <div class="modal-body">
                确定要删除报销单吗？
                <input id="hiddenId" type="hidden">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="del3()">确定</button>
            </div>
        </div>
    </div>
</div>


<%--分页--%>
<div align="center">
    <ul class="pagination">
        <li <c:if test="${1==pageInfo.pageNum}">class="disabled"</c:if>>
            <a href="myBaoxiaoBill?pageNum=${pageInfo.firstPage}"><span>首页</span></a>
        </li>
        <c:if test="${1==pageInfo.pageNum}">
            <li class="disabled"><a href="#" aria-label="Previous"><span
                    aria-hidden="true">&laquo;</span></a></li>
        </c:if>

        <c:if test="${1!=pageInfo.pageNum}">
            <li><a href="${pageContext.request.contextPath}/myBaoxiaoBill?pageNum=${pageInfo.pageNum-1}"
                   aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
            </a></li>
        </c:if>

        <c:choose>
            <c:when test="${pageInfo.lastPage<=10}">
                <c:forEach begin="1" end="${pageInfo.lastPage}" var="i">
                    <c:if test="${i==pageInfo.pageNum }">
                        <li class="active"><a
                                href="myBaoxiaoBill?pageNum=${i}">${i }</a></li>
                    </c:if>
                    <c:if test="${i!=pageInfo.pageNum }">
                        <li><a href="myBaoxiaoBill?pageNum=${i }">${i }</a></li>
                    </c:if>
                </c:forEach>
            </c:when>
            <c:when test="${pageInfo.pageNum<6  and  pageInfo.lastPage>10}">
                <c:forEach begin="1" end="9" var="i">
                    <c:if test="${i==pageInfo.pageNum }">
                        <li class="active"><a
                                href="myBaoxiaoBill?pageNum=${i }">${i }</a></li>
                    </c:if>
                    <c:if test="${i!=pageInfo.pageNum }">
                        <li><a href="myBaoxiaoBill?pageNum=${i }">${i }</a></li>
                    </c:if>
                </c:forEach>
                <li><a>...</a></li>
                <li><a
                        href="myBaoxiaoBill?pageNum=${pageInfo.lastPage }">${pageInfo.lastPage }</a></li>
            </c:when>
            <c:when test="${(pageInfo.pageNum+5)>=pageInfo.lastPage and  pageInfo.lastPage>10}">
                <li><a href="myBaoxiaoBill?pageNum=1">1</a></li>
                <li><a>...</a></li>
                <c:forEach begin="${pageInfo.lastPage-9 }" end="${pageInfo.lastPage }" var="i">

                    <c:if test="${i==pageInfo.pageNum }">
                        <li class="active"><a
                                href="myBaoxiaoBill?pageNum=${i }">${i }</a></li>
                    </c:if>
                    <c:if test="${i!=pageInfo.pageNum }">
                        <li><a href="myBaoxiaoBill?pageNum=${i }">${i }</a></li>
                    </c:if>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <li><a href="myBaoxiaoBill?pageNum=1">1</a></li>
                <li><a>...</a></li>
                <c:forEach begin="${pageInfo.pageNum-4 }" end="${pageInfo.pageNum+4 }" var="i">

                    <c:if test="${i==pageInfo.pageNum }">
                        <li class="active"><a
                                href="myBaoxiaoBill?pageNum=${i }">${i }</a></li>
                    </c:if>
                    <c:if test="${i!=pageInfo.pageNum }">
                        <li><a href="myBaoxiaoBill?pageNum=${i }">${i }</a></li>
                    </c:if>
                </c:forEach>
                <li><a>...</a></li>
                <li><a
                        href="myBaoxiaoBill?pageNum=${pageInfo.lastPage }">${pageInfo.lastPage }</a></li>
            </c:otherwise>
        </c:choose>

        <c:if test="${pageInfo.lastPage==pageInfo.pageNum }">
            <li class="disabled"><a href="#" aria-label="Next"> <span
                    aria-hidden="true">&raquo;</span>
            </a></li>
        </c:if>
        <c:if test="${pageInfo.lastPage!=pageInfo.pageNum }">
            <li><a
                    href="myBaoxiaoBill?pageNum=${pageInfo.pageNum+1}"
                    aria-label="Next"> <span aria-hidden="true">&raquo;</span>
            </a></li>
        </c:if>
        <li <c:if test="${pageInfo.pageNum==pageInfo.lastPage}">class="disabled"</c:if>>
            <a href="myBaoxiaoBill?pageNum=${pageInfo.lastPage}"><span>末页</span></a>
        </li>

    </ul>
</div>


</body>
<script type="text/javascript">
    function delBaoxiaoBill(billId) {
        var flag = window.confirm("确定要删除报销单吗？");
        if (flag) {
            $.ajax({
                type: "POST",
                dataType: "text",
                url: 'BiaoxiaoAction_delete?id=' + billId,
                success: function (data) {
                    if (data > 0) {
                        alert("删除成功");
                    }
                    closePanel();
                },
                error: function () {
                    alert("删除失败！");
                }
            });
        }
    }

    var val = $("#delMsg").val();
    if (val.length > 0) {
        alert(val);
    }
    function del2(billId) {
        $("#hiddenId").val(billId);
        $("#myModal").modal();
    }

    function del3() {
        var billId = $("#hiddenId").val();
        location = "BiaoxiaoAction_delete?id=" + billId;
    }
</script>
</html>