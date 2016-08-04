package com.tracker.servlets;

import com.tracker.data.Expense;
import com.tracker.data.util.expenseDataUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Justin on 8/4/16.
 */
public class projectControllerServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        String uri = request.getRequestURI();
        System.out.println("Requested URI: "+uri);

        String jspName = uri.substring(uri.lastIndexOf('/')+1);
        System.out.println("JSP Name: "+jspName);

        if(jspName.equalsIgnoreCase("viewAllExpenseData")) {
            ArrayList<Expense> expenses = expenseDataUtil.getAllExpense();
            request.setAttribute("expenses",expenses);
        } else if (jspName.equalsIgnoreCase("viewExpenseData")) {
            String expenseID = request.getParameter("expenseID");
            Expense expense = new Expense(Integer.parseInt(expenseID));
            request.setAttribute("expense",expense);
        }

        RequestDispatcher view = request.getRequestDispatcher("/testViews/"+jspName+".jsp");
        view.forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }
}


