<%-- 
    Document   : index
    Created on : May 14, 2017, 7:54:32 PM
    Author     : dell laptop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mobile Money</title>
    </head>
    <body>
        <h1></h1>
        <form action = "login.jsp" method="post">
            <table>
                <tr><td>User Name:</td>
                    <td><Input type="text" name="username"/></td></tr>
                
                <tr><td>Password</td>
                    <td><input type = "password" name = "password"/></td></tr>
                <tr><td></td>
                    <td><Input type="reset" name ="cancel" value = "Cancel"/>
                    <Input type="submit" name ="submit" value ="Submit"/></td></tr>
            </table>
        </form>
        
    </body>
</html>
