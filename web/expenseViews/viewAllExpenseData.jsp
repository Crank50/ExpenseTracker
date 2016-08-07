<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Expenses</title>
</head>
<body>
<h1>View Expenses</h1>
<br><br>
<table>
    <tr>
        <th>Expense Id</th>
        <th>Expense Name</th>
        <th>Expense Amount</th>
        <th>Expense Date</th>
        <th>Expense Category</th>
    </tr>
    <c:forEach var="expense" items="${expenses}">
        <tr>
            <td>
                <a href="/mvc_expense/viewExpense?id=${expense.id}">
                    <c:out value="${expense.id}" />
                </a>
            </td>
            <td><c:out value="${expense.expenseName}" /></td>
            <td><c:out value="${expense.expenseAmount}" /></td>
            <td><c:out value="${expense.expenseDate}" /></td>
            <td><c:out value="${expense.expenseCategory}" /></td>
        </tr>
    </c:forEach>
</table>
<br><br>
<hr>
<h3>Search By Category</h3>
<form name="searchByCategoryForm" method="POST" action="/mvc_expense/viewExpensesInCategory">
    Search By Expense Category: <select name="expenseCategory">
    <c:forEach var="expenseCategory" items="${expenseCategories}">
        <option value="<c:out value="${expenseCategory}"/>"><c:out value="${expenseCategory}"/></option>
    </c:forEach>
</select><br>
    <input type="submit">
</form>
<hr>
<h3>Search By Date Range</h3>
<form name="searchByDateRangeForm" method="POST" action="/mvc_expense/viewExpensesInDateRange">
    Start Date: Day <select name="expenseDayStart">
    <c:forEach var="expenseDay" items="${expenseDays}">
        <option value="<c:out value="${expenseDay}"/>"><c:out value="${expenseDay}"/></option>
    </c:forEach>
</select>
    Month <select name="expenseMonthStart">
    <c:forEach var="expenseMonth" items="${expenseMonths}">
        <option value="<c:out value="${expenseMonth}"/>"><c:out value="${expenseMonth}"/></option>
    </c:forEach>
</select>
    Year <select name="expenseYearStart">
    <c:forEach var="expenseYear" items="${expenseYears}">
        <option value="<c:out value="${expenseYear}"/>"><c:out value="${expenseYear}"/></option>
    </c:forEach>
</select><br>
    End Date: Day <select name="expenseDayEnd">
    <c:forEach var="expenseDay" items="${expenseDays}">
        <option value="<c:out value="${expenseDay}"/>"><c:out value="${expenseDay}"/></option>
    </c:forEach>
</select>
    Month <select name="expenseMonthEnd">
    <c:forEach var="expenseMonth" items="${expenseMonths}">
        <option value="<c:out value="${expenseMonth}"/>"><c:out value="${expenseMonth}"/></option>
    </c:forEach>
</select>
    Year <select name="expenseYearEnd">
    <c:forEach var="expenseYear" items="${expenseYears}">
        <option value="<c:out value="${expenseYear}"/>"><c:out value="${expenseYear}"/></option>
    </c:forEach>
</select><br>
    <input type="submit">
</form>
<hr>
<a href="/">Home</a>
<br>
<a href="/mvc_expense/addExpense">Add Expense</a>
<br>
<a href="/mvc_expense/viewAllExpenses">View All Expenses</a>
</body>
</html>
