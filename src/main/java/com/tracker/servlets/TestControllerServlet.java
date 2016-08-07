package com.tracker.servlets;

import com.tracker.data.Test;
import com.tracker.data.util.TestDataUtil;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class TestControllerServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        String uri = request.getRequestURI();
        System.out.println("Requested URI: "+uri);

        String jspName = uri.substring(uri.lastIndexOf('/')+1);
        System.out.println("JSP Name: "+jspName);

        if(jspName.equalsIgnoreCase("viewAllTestData")) {
            ArrayList<Test> tests = TestDataUtil.getAllTests();
            request.setAttribute("tests",tests);
        } else if (jspName.equalsIgnoreCase("viewTestData")) {
            String testId = request.getParameter("id");
            Test test = new Test(Integer.parseInt(testId));
            request.setAttribute("test",test);
        }

        RequestDispatcher view = request.getRequestDispatcher("/testViews/"+jspName+".jsp");
        view.forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }
}
