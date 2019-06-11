<%-- 
    Document   : user
    Created on : May 18, 2017, 8:23:01 AM
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
        <h1>Add a new user</h1>
        <form action = "addUser.jsp" method = "post">
            <table>
                <tr>
                    <td>User Name</td>
                    <td><input type="text" name = "username"/></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name = "password"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value = "Submit"/>
                    <input type="reset" value = "Cancel"/></td>
                </tr>
            </table>
        </form>
    </body>
</html>
