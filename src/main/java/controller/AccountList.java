package controller;

import model.Account;

import controller.function.Authorization;
import com.google.gson.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class AccountList extends HttpServlet {

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();

        responseOut.println("AccountList GET, no functionality defined");

        responseOut.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        ArrayList<String> errorList = new ArrayList<>();
        Gson gson = new Gson();

        Account user = gson.fromJson(request.getParameter("user"), Account.class);

        ArrayList<Account> accountList = Authorization.getAllAccounts(user, errorList);

        if (errorList.size() > 0) {
            responseOut.println(gson.toJson(errorList));
        }
        else {
            responseOut.println(gson.toJson(accountList));
        }

        responseOut.flush();
    }
}