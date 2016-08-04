package com.tracker.data;

import com.tracker.servlets.projectDataBaseServlet;

import java.sql.*;

/**
 * Created by Justin on 8/4/16.
 */
public class Expense {private Connection connection;
    private int expenseID;
    private String expenseName;
    private String expenseDate;
    private int expenseAmount;
    private boolean exists = false;

    public Expense() {

    }

    public Expense(int id) {
        expenseload(id);
    }

    // This is the READ in CRUD
    public void expenseload(int id) {
        try
        {
            connection = projectDataBaseServlet.getConnection();

            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM test WHERE id = "+id;
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            this.expenseID = id;
            this.setExpenseName(rs.getString("name"));
            this.setExpenseAmount(rs.getInt("amount"));
            this.setExpenseDate(rs.getString("date"));
            exists = true;
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }

    // This is the CREATE in CRUD
    public void createExpense() {
        if(!exists){
            try
            {
                connection = projectDataBaseServlet.getConnection();

                String query = "INSERT INTO expense (expenseName,expenseAmount,expenseDate) VALUES (?,?,?);";
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1,this.getExpenseName());
                preparedStatement.setString(2,this.getExpenseDate());
                preparedStatement.setInt(3,this.getExpenseAmount());
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    this.expenseID = rs.getInt(1);
                    exists = true;
                }
            }
            catch(SQLException sqle){
                sqle.printStackTrace();
            }
        } else {
            System.out.println("ERROR: Object already exists in database. Don't use saveNew(), use update().");
        }
    }

    // This is the UPDATE in CRUD
    private void update() {
        if(exists){
            try
            {
                connection = projectDataBaseServlet.getConnection();

                String query = "UPDATE expense SET expenseName = ?, expenseAmount = ?, expenseDate = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1,this.getExpenseName());
                preparedStatement.setInt(2,this.getExpenseAmount());
                preparedStatement.setString(3,this.getExpenseDate());
                preparedStatement.setInt(4,this.getExpenseID());
                preparedStatement.executeUpdate();
            }
            catch(SQLException sqle){
                sqle.printStackTrace();
            }
        } else {
            System.out.println("ERROR: Object does not exist in database yet. Don't use update(), use saveNew().");
        }
    }

    // This is the DELETE in CRUD
    private void delete(){
        if(exists){
            try
            {
                connection = projectDataBaseServlet.getConnection();

                String query = "DELETE FROM expense WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,this.getExpenseID());
                preparedStatement.executeUpdate();
                exists = false;
            }
            catch(SQLException sqle){
                sqle.printStackTrace();
            }
        } else {
            System.out.println("ERROR: Object does not exist in database yet. You must load() object before you can delete()");
        }
    }

    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public int getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(int expenseAmount) {
        this.expenseAmount = expenseAmount;
    }
}
