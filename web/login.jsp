<%-- 
    Document   : login
    Created on : May 18, 2017, 9:35:04 AM
    Author     : dell laptop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "sql" uri = "http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<sql:setDataSource var ="dataSource" 
                   driver = "com.mysql.jdbc.Driver"
                   url = "jdbc:mysql://localhost/mobilemo"
                   user="root"
                   password=""/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mobile Money</title>
    </head>
    <body>
        <sql:query var="getUser" dataSource="${dataSource}" sql = "Select * from users"></sql:query>
        <c:choose>
            <c:when test="${not empty getUser.rows}">
        <c:forEach var="check" items="${getUser.rows}">
            <c:out value="${check.userName}"/>
            <c:choose>
        <c:when test="${(param.username eq check.userName)and (param.password eq check.password)and (check.type eq 'admin') }">
            <c:out value="${check.userName}"/>
            <c:out value="welcome"/>
            <c:redirect url="kiosk.jsp"/>
        </c:when>
        
            <c:when test="${(param.username eq check.userName) and (param.password eq check.password)and (check.type eq 'user')}">
                
            </c:when>
            <c:otherwise>
                <%--<c:redirect url="index.jsp"/>--%>
            </c:otherwise></c:choose></c:forEach>
            </c:when>
        </c:choose>
    </body>
</html>
