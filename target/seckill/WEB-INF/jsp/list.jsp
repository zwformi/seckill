<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀列表</title>
     <%@include file="common/header.jsp"%>
</head>
<body>
    <div class="container">
        <div class="panel paned-default">
            <h2>秒杀列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thread>
                    <tr>
                        <th>名称</th>
                        <th>库存</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>创建时间</th>
                        <th>详情页</th>
                    </tr>
                </thread>
                <tbody>
                    <c:forEach var="row" items="${list}">
                        <tr>
                            <td>${row.name}</td>
                            <td>${row.number}</td>
                            <td><fmt:formatDate value="${row.startTime}" pattern="yyyy:MM:dd HH:mm:ss"></fmt:formatDate></td>
                            <td><fmt:formatDate value="${row.endTime}" pattern="yyyy:MM:dd HH:mm:ss"></fmt:formatDate></td>
                            <td><fmt:formatDate value="${row.createTime}" pattern="yyyy:MM:dd HH:mm:ss"></fmt:formatDate></td>
                            <td><a class="btn btn-info" href="/seckill/${row.secKillId}/detail" target="_blank">详细</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</body>
</html>