package com.tracker.servlets;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;

/**
 * Created by Justin on 8/4/16.
 */
public class projectDataBaseServlet extends HttpServlet {

    private static ComboPooledDataSource connectionPool = null;
    private static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";
    private static final String DB_URL = "jdbc:hsqldb:expense_tracker_db";
    private static final String USER = "sa";
    private static final String PASS = "password";

    public void init(ServletConfig config) throws ServletException {
        try
        {
            final ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setDriverClass(JDBC_DRIVER);
            cpds.setJdbcUrl(DB_URL);
            cpds.setUser(USER);
            cpds.setPassword(PASS);
            connectionPool = cpds;
            System.out.println("NOTE: PROJECT DATABASE CONNECTION POOL STARTED");
            createInitialLoad();
//            droptable();
        }
        catch (PropertyVetoException pve)
        {
            pve.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try
        {
            return connectionPool.getConnection(); // Creates Connection
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    private void droptable() {
        try {
            System.out.println("Table Dropped");
            update("DROP TABLE EXPENSE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createInitialLoad() {


        try {
            Connection connection = projectDataBaseServlet.getConnection();
            if(connection != null) {
                Statement stmt = connection.createStatement();
                String query = ("SELECT * FROM expense");
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
            } else {
                System.out.println("ERROR: connection is NULL");
            }
        }
        catch(SQLException sqle){
            sqle.printStackTrace();

            try {
                System.out.println("NOTE: DATABASE DID NOT EXIST, CREATING DATABASE"); // DataBase Created Here
                update("CREATE TABLE expense (id INTEGER IDENTITY PRIMARY KEY, expenseName VARCHAR(30),expenseAmount INTEGER,expenseDate VARCHAR(30))");
                //*expenseID *expenseName *int expenseAmount  *expenseDate Date(Use Date import)
                update("INSERT INTO expense (expenseName) VALUES('name1')");
                update("INSERT INTO expense (expenseName) VALUES('name2')");
                update("INSERT INTO expense (expenseName) VALUES('name3')");

                update("INSERT INTO expense (expenseAmount) VALUES(3)");
                update("INSERT INTO expense (expenseAmount) VALUES(4)");
                update("INSERT INTO expense (expenseAmount) VALUES(10)");

                update("INSERT INTO expense (expenseDate) VALUES('date1')");
                update("INSERT INTO expense (expenseDate) VALUES('date2')");
                update("INSERT INTO expense (expenseDate) VALUES('date3')");

                //use manual updates for testing
                System.out.println("NOTE: DATABASE FINISHED CREATING");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void update(String sqlExpression) throws SQLException {
        Connection connection = projectDataBaseServlet.getConnection();
        if(connection != null) {
            System.out.println("========= sqlExpression: "+sqlExpression);
            Statement stmt = connection.createStatement();
            int i = stmt.executeUpdate(sqlExpression);
            if (i == -1) {
                System.out.println("ERROR: database error in update "+sqlExpression);
            }
        }  else {
            System.out.println("ERROR: connection is NULL.");
        }
    }

    public void destroy()
    {
        try
        {
            DataSources.destroy(connectionPool);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {}

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {}
}
