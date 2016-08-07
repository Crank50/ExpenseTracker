<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Expense</title>
</head>
<body>
<h1>View Expense</h1>
<br><br>
<table>
    <tr>
        <td>Expense ID:</td>
        <td><c:out value="${expense.id}" /></td>
    </tr>
    <tr>
        <td>Expense Name:</td>
        <td><c:out value="${expense.expenseName}" /></td>
    </tr>
    <tr>
        <td>Expense Amount:</td>
        <td><c:out value="${expense.expenseAmount}" /></td>
    </tr>
    <tr>
        <td>Expense Date:</td>
        <td><c:out value="${expense.expenseDate}" /></td>
    </tr>
    <tr>
        <td>Expense Category:</td>
        <td><c:out value="${expense.expenseCategory}" /></td>
    </tr>
</table>
<br><br>
<a href="/">Home</a>
&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/mvc_expense/viewAllExpenses">View All Expenses</a>
</body>
</html>
