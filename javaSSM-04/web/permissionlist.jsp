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
    <li>系统管理</li>
    <li class="active">角色管理</li>
</ol>
<!--路径导航-->

<div class="page-content">
    <div class="panel panel-default">
        <div class="panel-heading">角色列表&nbsp;&nbsp;&nbsp;</div>
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th width="60%">角色名称</th>
                    <th width="20%">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="role" items="${allRoles}">
                    <tr id="${role.id}">
                        <td>${role.name}</td>
                        <td>
                            <a href="#" onclick="viewPermissions('${role.id}')" class="btn btn-info btn-xs"><span
                                    class="glyphicon glyphicon-edit"></span> 编辑</a>
                            <a href="#" onclick="deleteRoles('${role.id}')" class="btn btn-danger btn-xs"><span
                                    class="glyphicon glyphicon-remove"></span> 删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- 编辑窗口 -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" onclick="closePanel()">×</button>
                <h3 id="myModalLabel">权限列表</h3>
            </div>
            <form id="permissionForm" action="updateRoleAndPermission" method="post">
                <input type="hidden" id="roleId" name="roleId"/>
                <div class="modal-body" id="roleList">
                    <table class="table table-bordered" width="800px">
                        <thead>
                        <tr>
                            <th width="30%">角色
                            </td>
                            <th width="70%">权限</th>
                        </tr>
                        </thead>
                        <tbody id="roleListBody">
                        <c:forEach var="menu" items="${allMenuAndPermissions}">
                            <tr>
                                <td>
                                    <input id="chk${menu.id}" type="checkbox" name="permissionIds" value="${menu.id}"/>
                                        ${menu.name}
                                </td>
                                <td>
                                    <c:forEach var="permission" items="${menu.children}">
                                        <input id="chk${permission.id}" type="checkbox" name="permissionIds"
                                               value="${permission.id}"/>
                                        ${permission.name}[${permission.type}]
                                        <br/>
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" data-dismiss="modal"
                            aria-hidden="true" onclick="document.getElementById('permissionForm').submit()">保存
                    </button>
                    <button class="btn btn-default" onclick="document.location.href='findRoles'">关闭</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%--分页--%>
<div align="center">
    <ul class="pagination">
        <li <c:if test="${1==pageInfo.pageNum}">class="disabled"</c:if>>
            <a href="findRoles?pageNum=${pageInfo.firstPage}"><span>首页</span></a>
        </li>
        <c:if test="${1==pageInfo.pageNum}">
            <li class="disabled"><a href="#" aria-label="Previous"><span
                    aria-hidden="true">&laquo;</span></a></li>
        </c:if>

        <c:if test="${1!=pageInfo.pageNum}">
            <li><a href="${pageContext.request.contextPath}/findRoles?pageNum=${pageInfo.pageNum-1}"
                   aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
            </a></li>
        </c:if>

        <c:choose>
            <c:when test="${pageInfo.lastPage<=10}">
                <c:forEach begin="1" end="${pageInfo.lastPage}" var="i">
                    <c:if test="${i==pageInfo.pageNum }">
                        <li class="active"><a
                                href="findRoles?pageNum=${i}">${i }</a></li>
                    </c:if>
                    <c:if test="${i!=pageInfo.pageNum }">
                        <li><a href="findRoles?pageNum=${i }">${i }</a></li>
                    </c:if>
                </c:forEach>
            </c:when>
            <c:when test="${pageInfo.pageNum<6  and  pageInfo.lastPage>10}">
                <c:forEach begin="1" end="9" var="i">
                    <c:if test="${i==pageInfo.pageNum }">
                        <li class="active"><a
                                href="findRoles?pageNum=${i }">${i }</a></li>
                    </c:if>
                    <c:if test="${i!=pageInfo.pageNum }">
                        <li><a href="findRoles?pageNum=${i }">${i }</a></li>
                    </c:if>
                </c:forEach>
                <li><a>...</a></li>
                <li><a
                        href="findRoles?pageNum=${pageInfo.lastPage }">${pageInfo.lastPage }</a></li>
            </c:when>
            <c:when test="${(pageInfo.pageNum+5)>=pageInfo.lastPage and  pageInfo.lastPage>10}">
                <li><a href="findRoles?pageNum=1">1</a></li>
                <li><a>...</a></li>
                <c:forEach begin="${pageInfo.lastPage-9 }" end="${pageInfo.lastPage }" var="i">

                    <c:if test="${i==pageInfo.pageNum }">
                        <li class="active"><a
                                href="findRoles?pageNum=${i }">${i }</a></li>
                    </c:if>
                    <c:if test="${i!=pageInfo.pageNum }">
                        <li><a href="findRoles?pageNum=${i }">${i }</a></li>
                    </c:if>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <li><a href="findRoles?pageNum=1">1</a></li>
                <li><a>...</a></li>
                <c:forEach begin="${pageInfo.pageNum-4 }" end="${pageInfo.pageNum+4 }" var="i">

                    <c:if test="${i==pageInfo.pageNum }">
                        <li class="active"><a
                                href="findRoles?pageNum=${i }">${i }</a></li>
                    </c:if>
                    <c:if test="${i!=pageInfo.pageNum }">
                        <li><a href="findRoles?pageNum=${i }">${i }</a></li>
                    </c:if>
                </c:forEach>
                <li><a>...</a></li>
                <li><a
                        href="findRoles?pageNum=${pageInfo.lastPage }">${pageInfo.lastPage }</a></li>
            </c:otherwise>
        </c:choose>

        <c:if test="${pageInfo.lastPage==pageInfo.pageNum }">
            <li class="disabled"><a href="#" aria-label="Next"> <span
                    aria-hidden="true">&raquo;</span>
            </a></li>
        </c:if>
        <c:if test="${pageInfo.lastPage!=pageInfo.pageNum }">
            <li><a
                    href="findRoles?pageNum=${pageInfo.pageNum+1}"
                    aria-label="Next"> <span aria-hidden="true">&raquo;</span>
            </a></li>
        </c:if>
        <li <c:if test="${pageInfo.pageNum==pageInfo.lastPage}">class="disabled"</c:if>>
            <a href="findRoles?pageNum=${pageInfo.lastPage}"><span>末页</span></a>
        </li>

    </ul>
</div>
</body>
</html>
<script type="text/javascript">
    var role_id;

    function viewPermissions(role_id) {
        $('#roleId').val(role_id);
        $.ajax({
            url: 'loadMyPermissions',
            type: 'post',
            data: {
                roleId: role_id
            },
            dataType: 'json',
            success: function (permissionList) {
                //$("input[type=checkbox]").attr('checked',false);
                $.each(permissionList, function (i, perm) {
                    $('#chk' + perm.id).attr('checked', true);
                });
                $("#editModal").modal("show");
            },
            error: function (req, error) {
                alert(req.status + ':' + error);
            }
        });
    }

    function deleteRoles(id) {
        if (confirm('确定要删除角色吗?')) {
            window.location.href="deleteRoles?id="+id;
        }
    }

</script>