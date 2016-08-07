package com.tracker.data.util;

import com.tracker.data.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TestDataUtil {

    public static ArrayList<Test> getAllTests() {
        ArrayList<Test> tests = new ArrayList<>();
        try
        {
            Connection connection = DatabaseServlet.getConnection();

            Statement stmt = connection.createStatement();
            String query = "SELECT id FROM test";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                Test t = new Test(rs.getInt("id"));
                tests.add(t);
            }
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }

        return tests;
    }
}
