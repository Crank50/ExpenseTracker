package com.tracker.servlets;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseServlet extends HttpServlet {

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
            System.out.println("NOTE: DATABASE CONNECTION POOL STARTED");
//            dropExpenseTable();
            expenseInitialLoad();
            testInitialLoad();
        }
        catch (PropertyVetoException pve)
        {
            pve.printStackTrace();
        }
    }

    public static Connection getConnection()
    {
        try
        {
            return connectionPool.getConnection();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private void testInitialLoad() {
        try {
            Connection connection = DatabaseServlet.getConnection();
            if(connection != null) {
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM test";
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
            } else {
                System.out.println("ERROR: connection is NULL");
            }
        }
        catch(SQLException sqle){
            try {
                System.out.println("NOTE: TABLE TEST DOES NOT EXIST, CREATING DATABASE");
                update("CREATE TABLE test (id INTEGER IDENTITY PRIMARY KEY, str VARCHAR(30))");
                update("INSERT INTO test (str) VALUES('Test1')");
                update("INSERT INTO test (str) VALUES('Test2')");
                update("INSERT INTO test (str) VALUES('Test3')");
                System.out.println("NOTE: TABLE TEST CREATED AND POPULATED");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void expenseInitialLoad() {
        try {
            Connection connection = DatabaseServlet.getConnection();
            if(connection != null) {
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM expense";
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
            } else {
                System.out.println("ERROR: connection is NULL");
            }
        }
        catch(SQLException sqle){
            try {
                System.out.println("NOTE: TABLE EXPENSE DOES NOT EXIST, CREATING DATABASE");
                update("CREATE TABLE expense (id INTEGER IDENTITY PRIMARY KEY, expenseName VARCHAR(255), expenseAmount INTEGER, expenseDate DATE, expenseCategory VARCHAR(255))");
                update("INSERT INTO expense (expenseName,expenseAmount,expenseDate,expenseCategory) VALUES('Test Expense 1',1000,CURRENT_DATE,'MEAL')");
                update("INSERT INTO expense (expenseName,expenseAmount,expenseDate,expenseCategory) VALUES('Test Expense 2',500,CURRENT_DATE,'SOFTWARE')");
                System.out.println("NOTE: TABLE EXPENSE CREATED AND POPULATED");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void dropExpenseTable() {
        try {
            update("DROP TABLE expense");
            System.out.println("NOTE: DROPPED TABLE EXPENSE");
        }
        catch(SQLException sqle){
            System.out.println("WARNING: DID NOT DROP TABLE EXPENSE");
        }
    }

    private synchronized void update(String sqlExpression) throws SQLException {
        Connection connection = DatabaseServlet.getConnection();
        if(connection != null) {
            System.out.println("========= sqlExpression: "+sqlExpression);
            Statement stmt = connection.createStatement();
            int i = stmt.executeUpdate(sqlExpression);
            if (i == -1) {
                System.out.println("ERROR: database error in update "+sqlExpression);
            }
        }  else {
            System.out.println("ERROR: connection is NULL");
        }
    }

    public void destroy()
    {
        try
        {
            DataSources.destroy(connectionPool);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {}

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {}
}
