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
    <li>用户管理</li>
    <li class="active">用户列表</li>
</ol>
<!--路径导航-->

<div class="page-content">
    <form class="form-inline" >
        <div class="panel panel-default">
            <div class="panel-heading">用户列表&nbsp;&nbsp;&nbsp;
                <button type="button" class="btn btn-primary" title="新建" data-toggle="modal" id="addEmp"
                        data-target="#createUserModal">新建用户
                </button>
            </div>

            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th width="5%">ID</th>
                        <th width="10%">帐号</th>
                        <th width="20%">电子邮箱</th>
                        <th width="15%">角色分配</th>
                        <th width="15%">上级主管</th>
                        <th width="25%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${userList}">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.email}</td>
                            <td>
                                <label>
                                    <select class="form-control" onchange="assignRole(this.value,'${user.name}')">
                                        <c:forEach var="role" items="${allRoles}">
                                            <option value="${role.id}"
                                                    <c:if test="${role.name==user.rolename}">selected</c:if>>${role.name}</option>
                                        </c:forEach>
                                    </select>
                                </label>
                            </td>
                            <td id="manager">
                                    ${user.manager}
                            </td>
                            <td>
                                <a href="#" onclick="viewPermission('${user.name}')" class="btn btn-success btn-xs"
                                   data-toggle="modal" data-target="#editModal">
                                    <span class="glyphicon glyphicon-eye-open" id="look"></span> 查看权限</a>
                                <a type="button" class="btn btn-danger btn-xs"
                                   onclick="document.location.href='deleteUserAndRole?name=${user.name}'">
                                    <span class="glyphicon glyphicon-remove"></span> 删除
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </form>
</div>

<!--添加用户 编辑窗口 -->
<div class="modal fade" id="createUserModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <form id="permissionForm" action="${pageContext.request.contextPath}/saveUser" method="post">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">×
                    </button>
                    <h3 id="myModalLabel">编辑用户</h3>
                </div>
                <div class="modal-body">
                    <table class="table table-bordered table-striped" width="800px">
                        <tr>
                            <td>帐号</td>
                            <td><input class="form-control" name="name" placeholder="名称" onblur="checkName(this.value)"></td>
                        </tr>
                        <tr>
                            <td>初始密码</td>
                            <td><input class="form-control" type="password" name="password" placeholder="密码"></td>
                        </tr>
                        <tr>
                            <td>电子邮箱</td>
                            <td><input class="form-control" name="email" placeholder="链接">
                            </td>
                        </tr>
                        <tr>
                            <td>级别</td>
                            <td>
                                <select id="role" class="form-control" name="role"
                                        onchange="getNextManager(this.value)">
                                    <option value="0" selected></option>
                                    <option value="1">普通员工</option>
                                    <option value="2">一级主管</option>
                                    <option value="3">总经理</option>
                                    <option value="4">网管</option>
                                </select>
                            </td>
                        </tr>

                        <tr>
                            <td>上级主管</td>
                            <td>
                                <select id="selManager" class="form-control" name="managerId"></select>
                            </td>
                        </tr>

                    </table>
                </div>
                <div class="modal-footer">
                    <span id="checkName"></span>
                    <button type="button" class="btn btn-success" data-dismiss="modal"
                            aria-hidden="true" onclick="addEmployee()">
                        保存
                    </button>
                    <button  class="btn btn-default" data-dismiss="modal"
                            aria-hidden="true">关闭  </button>
                </div>
            </div>
        </div>
    </form>
</div>
<!--分页 -->
<div align="center">
    <ul class="pagination">
        <li <c:if test="${1==pageInfo.pageNum}">class="disabled"</c:if>>
            <a href="findUserList?pageNum=${pageInfo.firstPage}"><span>首页</span></a>
        </li>
        <c:if test="${1==pageInfo.pageNum}">
            <li class="disabled"><a href="#" aria-label="Previous"><span
                    aria-hidden="true">&laquo;</span></a></li>
        </c:if>

        <c:if test="${1!=pageInfo.pageNum}">
            <li><a href="${pageContext.request.contextPath}/findUserList?pageNum=${pageInfo.pageNum-1}"
                   aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
            </a></li>
        </c:if>

        <c:choose>
            <c:when test="${pageInfo.lastPage<=10}">
                <c:forEach begin="1" end="${pageInfo.lastPage}" var="i">
                    <c:if test="${i==pageInfo.pageNum }">
                        <li class="active"><a
                                href="findUserList?pageNum=${i}">${i }</a></li>
                    </c:if>
                    <c:if test="${i!=pageInfo.pageNum }">
                        <li><a href="findUserList?pageNum=${i }">${i }</a></li>
                    </c:if>
                </c:forEach>
            </c:when>
            <c:when test="${pageInfo.pageNum<6  and  pageInfo.lastPage>10}">
                <c:forEach begin="1" end="9" var="i">
                    <c:if test="${i==pageInfo.pageNum }">
                        <li class="active"><a
                                href="findUserList?pageNum=${i }">${i }</a></li>
                    </c:if>
                    <c:if test="${i!=pageInfo.pageNum }">
                        <li><a href="findUserList?pageNum=${i }">${i }</a></li>
                    </c:if>
                </c:forEach>
                <li><a>...</a></li>
                <li><a
                        href="findUserList?pageNum=${pageInfo.lastPage }">${pageInfo.lastPage }</a></li>
            </c:when>
            <c:when test="${(pageInfo.pageNum+5)>=pageInfo.lastPage and  pageInfo.lastPage>10}">
                <li><a href="findUserList?pageNum=1">1</a></li>
                <li><a>...</a></li>
                <c:forEach begin="${pageInfo.lastPage-9 }" end="${pageInfo.lastPage }" var="i">

                    <c:if test="${i==pageInfo.pageNum }">
                        <li class="active"><a
                                href="findUserList?pageNum=${i }">${i }</a></li>
                    </c:if>
                    <c:if test="${i!=pageInfo.pageNum }">
                        <li><a href="findUserList?pageNum=${i }">${i }</a></li>
                    </c:if>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <li><a href="findUserList?pageNum=1">1</a></li>
                <li><a>...</a></li>
                <c:forEach begin="${pageInfo.pageNum-4 }" end="${pageInfo.pageNum+4 }" var="i">

                    <c:if test="${i==pageInfo.pageNum }">
                        <li class="active"><a
                                href="findUserList?pageNum=${i }">${i }</a></li>
                    </c:if>
                    <c:if test="${i!=pageInfo.pageNum }">
                        <li><a href="findUserList?pageNum=${i }">${i }</a></li>
                    </c:if>
                </c:forEach>
                <li><a>...</a></li>
                <li><a
                        href="findUserList?pageNum=${pageInfo.lastPage }">${pageInfo.lastPage }</a></li>
            </c:otherwise>
        </c:choose>

        <c:if test="${pageInfo.lastPage==pageInfo.pageNum }">
            <li class="disabled"><a href="#" aria-label="Next"> <span
                    aria-hidden="true">&raquo;</span>
            </a></li>
        </c:if>
        <c:if test="${pageInfo.lastPage!=pageInfo.pageNum }">
            <li><a
                    href="findUserList?pageNum=${pageInfo.pageNum+1}"
                    aria-label="Next"> <span aria-hidden="true">&raquo;</span>
            </a></li>
        </c:if>
        <li <c:if test="${pageInfo.pageNum==pageInfo.lastPage}">class="disabled"</c:if>>
            <a href="findUserList?pageNum=${pageInfo.lastPage}"><span>末页</span></a>
        </li>

    </ul>
</div>


<!-- 查看用户角色权限窗口 -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3>权限列表</h3>
            </div>
            <div class="modal-body" id="roleList">
                <table class="table table-bordered" width="800px">
                    <thead>
                    <tr>
                        <th>角色
                        </th>
                        <th>权限</th>
                    </tr>
                    </thead>
                    <tbody id="roleListBody">
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>

<script type="text/javascript">
    // 查看当前员工的角色和权限列表
    function viewPermission(user_name) {
        $.ajax({
            url: 'viewPermissionByUser',
            type: 'post',
            data: {
                userName: user_name
            },
            dataType: 'json',
            success: function (sysRole) {
                //先清空原来的内容
                $("#roleListBody").empty();
                var role_td = "<td>" + sysRole.name + "</td>";
                var permission_td = "<td>";
                var permission_list = sysRole.permissionList;
                $.each(permission_list, function (i, perm) {
                    permission_td += perm.name + "【" + perm.type + "】<br/>"
                });
                permission_td += "</td>";
                var roleRow = $("<tr>" + role_td + permission_td + "</tr>");
                $("#roleListBody").append($(roleRow));
            },
            error: function (req, error) {
                alert(req.status + ':' + error);
            }
        });
    }

    //重新分配待办人   //重新分配权限和角色
    function assignRole(rid, uname) {
        var url = "assignRole";
        var params = {
            roleId: rid,
            userId: uname
        };
        $.getJSON(url, params, function (result) {
            alert(result.msg);
            role = rid;
        });
    }

    function getNextManager(levelNo) {
        var url = "findMaxManager";
        var params = {
            level: levelNo
        };
        $("#selManager").empty();
        $.getJSON(url, params, function (employeeList) {
            if (employeeList != null) {
                $.each(employeeList, function (i, manager) {
                    var pot = $("<option  value='" + manager.id + "'>" + manager.name + "</option>")
                    $("#selManager").append($(pot))
                });
            }
        });
    }

    //添加用户
    function addEmployee() {
        document.getElementById('permissionForm').submit()
    }

   //查询用户名是否可用
    function checkName(name) {
        $.ajax({
            url: 'checkName',
            type: 'post',
            data: {
                username: name
            },
            success: function (msg) {
                if (msg === 1) {
                    $("#checkName").html("该名字可以使用").css("color","green")
                } else {
                    $("#checkName").html("该名字已被使用").css("color","red");
                }
            }
        })
    }
</script>