package com.tracker.data;

import com.tracker.servlets.DatabaseServlet;

import java.sql.*;

public class Test {
    private Connection connection;
    private int id;
    private String str;
    private boolean exists = false;

    public Test() {

    }

    public Test(int id1) {
        load(id1);
    }

    // This is the READ in CRUD
    public void load(int id) {
        try
        {
            connection = DatabaseServlet.getConnection();

            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM test WHERE id = "+id;
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            this.id = id;
            this.setStr(rs.getString("str"));
            exists = true;
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

                String query = "INSERT INTO test (str) VALUES (?);";
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1,this.getStr());
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    this.id = rs.getInt(1);
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
                connection = DatabaseServlet.getConnection();

                String query = "UPDATE test SET str = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1,this.getStr());
                preparedStatement.setInt(2,this.getId());
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
                connection = DatabaseServlet.getConnection();

                String query = "DELETE FROM test WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,this.getId());
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
