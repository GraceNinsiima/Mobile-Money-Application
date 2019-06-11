<%-- 
    Document   : insert
    Created on : May 14, 2017, 9:03:25 PM
    Author     : dell laptop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import = "java.sql.*"%>
<%@taglib prefix = "sql" uri = "http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
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
      
        <sql:query var = "kiosks" dataSource="${dataSource}" sql = "Select * from kiosk">
</sql:query>
     <c:choose>
        <c:when test="${empty kiosks.rows}">
            <sql:update var = "kioskn" dataSource="${dataSource}" 
            sql = "Insert into kiosk(kioskName, kioskAttendant,password, mtnSimCard, mtnFloat,mtnCash, airtelSimCard, airtelFloat,airtelCash)
            values(?,?,?,?,?,?,?,?,?)">
                <sql:param value="${param.kiosk}"/>
                <sql:param value="${param.username}"/>
                <sql:param value="${param.password}"/>
                <sql:param value="${param.mtnSimNo}"/>
                <sql:param value="${param.mtnFloat}"/>
                <sql:param value="${param.mtnCash}"/>
                <sql:param value="${param.airtelSimNo}"/>
                <sql:param value="${param.airtelFloat}"/>
                <sql:param value="${param.airtelCash}"/>
              </sql:update>
        </c:when>
         <c:otherwise>
             <c:forEach var ="kiosk" items="${kiosks.rows}">
                 <c:if var="checking" test="${(param.kiosk ne kiosk.kioskName) and (param.username ne kiosk.userName) and 
                      (param.mtnSimNo ne kiosk.mtnSimCard) and (param.airtelSimNo ne kiosk.airtelSimCard)}">
                 </c:if></c:forEach>
                
        <c:choose>
        <c:when test="${checking eq true}">
          <sql:update var = "kioska" dataSource="${dataSource}" 
            sql = "Insert into kiosk(kioskName, kioskAttendant,password, mtnSimCard, mtnFloat,mtnCash, airtelSimCard, airtelFloat,airtelCash)
            values(?,?,?,?,?,?,?,?,?)">
                <sql:param value="${param.kiosk}"/>
                <sql:param value="${param.username}"/>
                <sql:param value="${param.password}"/>
                <sql:param value="${param.mtnSimNo}"/>
                <sql:param value="${param.mtnFloat}"/>
                <sql:param value="${param.mtnCash}"/>
                <sql:param value="${param.airtelSimNo}"/>
                <sql:param value="${param.airtelFloat}"/>
                <sql:param value="${param.airtelCash}"/>
              </sql:update>
        </c:when>
            <c:otherwise>
                <c:redirect url = "kiosk.jsp"/>
            </c:otherwise>
        </c:choose>
         </c:otherwise>
     </c:choose>
        <%--Displaying the data--%>
        <sql:query var = "getKiosk" dataSource="${dataSource}" sql = "Select * from kiosk"></sql:query>
        <table>
            <tr><th>Kiosk Name</th><th>MTN SimCard</th><th>MTN Float</th><th>Airtel SimCard</th>
            <th>Airtel Float</th><th>Options</th></tr>
        <c:forEach var="show" items="${getKiosk.rows}">
            <tr>
                <td><c:out value="${show.kioskName}"/></td>
                <td><c:out value="${show.mtnSimCard}"/></td>
                <td><c:out value="${show.mtnFloat}"/></td>
                <td><c:out value="${show.airtelSImCard}"/></td>
                <td><c:out value="${show.airtelFloat}"/></td>
                <td><form action="" method="post">
                        <input type="submit" name="edit" value="Edit"/>
                        <input type="submit" name="remove" value="Remove"/>
                    </form></td>
            </tr>
        </c:forEach></table>
    </body>
</html>
