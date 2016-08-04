<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Test Data</title>
</head>
<body>
<h1>View Test Data</h1>
<br><br>
<table>
    <tr>
        <td>ID:</td>
        <td><c:out value="${test.id}" /></td>
    </tr>
    <tr>
        <td>Str:</td>
        <td><c:out value="${test.str}" /></td>
    </tr>
</table>
<br><br>
<a href="/">Home</a>
&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/mvc_test/viewAllTestData">View All Test Data</a>
</body>
</html>
