package com.tracker.data.util;

import com.tracker.data.Expense;
import com.tracker.data.Test;
import com.tracker.servlets.projectDataBaseServlet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Justin on 8/4/16.
 */
public class expenseDataUtil {


    public static ArrayList<Expense> getAllExpense() {
        ArrayList<Expense> Expenses = new ArrayList<>();
        try
        {
            Connection connection = projectDataBaseServlet.getConnection();

            Statement stmt = connection.createStatement();
            String query = "SELECT id FROM test";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                Expense e = new Expense(rs.getInt("expenseID"));
                Expenses.add(e);
            }
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }

        return Expenses;
    }
}
