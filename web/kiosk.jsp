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
        <h1>Form for registering Kiosks</h1>
        <form action = "insert.jsp" method="post">
            <table>
                <tr><td>Kiosk Name:</td>
                    <td><Input type="text" name="kiosk"/></td></tr>
                <tr><td>Location:</td>
                    <td><Input type="text" name ="location"/></td></tr>
                <tr><td>Attendants Name:</td>
                    <td><input type="text" name="username"/></td></tr>
                <tr><td>Attendants Password:</td>
                    <td><input type="password" name="password"/></td></tr>
                <tr><td>Mtn Sim Card Number:</td>
                    <td><Input type="tel" name ="mtnSimNo"/></td></tr>
                <tr><td>Mtn Float:</td>
                    <td><input type = "text" name = "mtnFloat"/></td></tr>
                <tr><td>Mtn Cash:</td>
                    <td><input type = "text" name = "mtnCash"/></td></tr>
                <tr><td>Airtel Sim Card Number:</td>
                    <td><Input type="tel" name ="airtelSimNo"/></td></tr>
                <tr><td>Airtel Float:</td>
                    <td><input type = "text" name = "airtelFloat"/></td></tr>
                <tr><td>Airtel Cash:</td>
                    <td><input type = "text" name = "airtelCash"/></td></tr>
                    <tr><td><Input type="submit" name ="submit" value = "Submit"/>
                    <Input type="reset" name ="cancel" value ="Cancel"/></td></tr>
            </table>
        </form>
        <br/>
        <p><a href="user.jsp">To add a new user </a></p>
    </body>
</html>
