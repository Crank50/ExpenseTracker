package com.tracker.data.util;


import com.tracker.data.Expense;
import com.tracker.servlets.DatabaseServlet;
import com.tracker.servlets.projectDataBaseServlet;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Justin on 8/4/16.
 */
public class expenseUtil {


    public static ArrayList<Expense> getAllExpense() {
        ArrayList<Expense> Expenses = new ArrayList<>();
        try
        {
            Connection connection = projectDataBaseServlet.getConnection();

            Statement stmt = connection.createStatement();
            String query = ("SELECT id FROM expense");
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                Expense e = new Expense(rs.getInt("id"));
                Expenses.add(e);
            }
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }

        return Expenses;
    }
    public static ArrayList<Expense> getExpensesInCategory(Expense.ExpenseCategory expenseCategory) {
        ArrayList<Expense> expenses = new ArrayList<>();
        try
        {
            Connection connection = DatabaseServlet.getConnection();

            PreparedStatement stmt = connection.prepareStatement("SELECT id FROM expense WHERE expenseCategory = ?");
            stmt.setString(1,expenseCategory.toString());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Expense exp = new Expense(rs.getInt("id"));
                expenses.add(exp);
            }
            rs.close();
            stmt.close();
            connection.close();
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return expenses;
    }
    public static ArrayList<Expense> getExpensesInDateRange(LocalDate startDate, LocalDate endDate) {
        ArrayList<Expense> expenses = new ArrayList<>();
        try
        {
            Connection connection = DatabaseServlet.getConnection();

            PreparedStatement stmt = connection.prepareStatement("SELECT id FROM expense WHERE expenseDate >= ? AND expenseDate <= ?");
            stmt.setDate(1,Date.valueOf(startDate));
            stmt.setDate(2,Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Expense exp = new Expense(rs.getInt("id"));
                expenses.add(exp);
            }
            rs.close();
            stmt.close();
            connection.close();
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return expenses;
    }
}
