<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Expense</title>
</head>
<body>
<h1>Add Expense</h1>
<br><br>
<form name="newExpenseForm" method="POST" action="/mvc_exp/saveNewExpense">
    Expense Name: <input type="text" name="expenseName" /><br>
    Expense Amount: <input type="text" name="expenseAmount" /><br>

    Expense Date: Day <select name="expenseDay">
    <c:forEach var="expenseDay" items="${expenseDays}">
        <option value="<c:out value="${expenseDay}"/>"><c:out value="${expenseDay}"/></option>
    </c:forEach>
</select>
    Month <select name="expenseMonth">
    <c:forEach var="expenseMonth" items="${expenseMonths}">
        <option value="<c:out value="${expenseMonth}"/>"><c:out value="${expenseMonth}"/></option>
    </c:forEach>
</select>
    Year <select name="expenseYear">
    <c:forEach var="expenseYear" items="${expenseYears}">
        <option value="<c:out value="${expenseYear}"/>"><c:out value="${expenseYear}"/></option>
    </c:forEach>
</select><br>

    Expense Category: <select name="expenseCategory">
    <c:forEach var="expenseCategory" items="${expenseCategories}">
        <option value="<c:out value="${expenseCategory}"/>"><c:out value="${expenseCategory}"/></option>
    </c:forEach>
</select><br>
    <input type="submit">
</form>
</body>
</html>