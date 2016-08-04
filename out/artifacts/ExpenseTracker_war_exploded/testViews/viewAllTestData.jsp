<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View All Test Data</title>
</head>
<body>
<h1>View All Test Data</h1>
<br><br>
<table>
    <tr>
        <th>Test Id</th>
        <th>Str</th>
    </tr>
    <c:forEach var="test" items="${tests}">
        <tr>
            <td>
                <a href="/mvc_test/viewTestData?id=${test.id}">
                    <c:out value="${test.id}" />
                </a>
            </td>
            <td><c:out value="${test.str}" /></td>
        </tr>
    </c:forEach>
</table>
<br><br>
<a href="/">Home</a>
</body>
</html>
