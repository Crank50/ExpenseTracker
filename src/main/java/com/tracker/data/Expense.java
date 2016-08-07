package com.tracker.data;

import com.tracker.servlets.DatabaseServlet;

import java.sql.*;
import java.time.LocalDate;


public class Expense {
    public static enum ExpenseCategory { MEAL, HOTEL, AIRFAIR, CAR, HARDWARE, SOFTWARE, UTILITY, SUPPLIES }

    private Connection connection;
    private int id;
    private String expenseName;
    private int expenseAmount;
    private LocalDate expenseDate;
    private ExpenseCategory expenseCategory;
    private boolean exists = false;

    public Expense() {}

    public Expense(int id) {
        load(id);
    }

    // This is the READ in CRUD
    public void load(int id) {
        try
        {
            connection = DatabaseServlet.getConnection();

            Statement stmt = connection.createStatement();
            String query = "SELECT expenseName, expenseAmount, expenseDate, expenseCategory FROM expense WHERE id = "+id;
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            this.id = id;
            this.setExpenseName(rs.getString("expenseName"));
            this.setExpenseAmount(rs.getInt("expenseAmount"));
            this.setExpenseDate(rs.getDate("expenseDate").toLocalDate());
            this.setExpenseCategory(ExpenseCategory.valueOf(rs.getString("expenseCategory")));
            exists = true;

            rs.close();
            stmt.close();
            connection.close();
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }

    // This is the CREATE in CRUD
    public void saveNew() {
        if(!exists){
            try
            {
                connection = DatabaseServlet.getConnection();

                String query = "INSERT INTO expense (expenseName, expenseAmount, expenseDate, expenseCategory) VALUES (?,?,?,?);";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
//                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1,this.getExpenseName());
                preparedStatement.setInt(2,this.getExpenseAmount());
                preparedStatement.setDate(3,Date.valueOf(this.getExpenseDate()));
                preparedStatement.setString(4,this.getExpenseCategory().toString());
                preparedStatement.executeUpdate();
//                ResultSet rs = preparedStatement.getGeneratedKeys();
//                if (rs != null && rs.next()) {
//                    this.id = rs.getInt(1);
//                    exists = true;
//                }
                preparedStatement.close();
                connection.close();
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
                connection = DatabaseServlet.getConnection();

                String query = "UPDATE expense SET expenseName = ?, expenseAmount = ?, expenseDate = ?, expenseCategory = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,this.getExpenseName());
                preparedStatement.setInt(2,this.getExpenseAmount());
                preparedStatement.setDate(3,Date.valueOf(this.getExpenseDate()));
                preparedStatement.setString(4,this.getExpenseCategory().toString());
                preparedStatement.setInt(5,this.getId());
                preparedStatement.executeUpdate();

                preparedStatement.close();
                connection.close();
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
                connection = DatabaseServlet.getConnection();

                String query = "DELETE FROM expense WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,this.getId());
                preparedStatement.executeUpdate();
                exists = false;

                preparedStatement.close();
                connection.close();
            }
            catch(SQLException sqle){
                sqle.printStackTrace();
            }
        } else {
            System.out.println("ERROR: Object does not exist in database yet. You must load() object before you can delete()");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public int getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(int expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }
}
