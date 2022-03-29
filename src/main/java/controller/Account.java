package controller;

import controller.function.Authorization;
import com.google.gson.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Account extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();

        responseOut.println("Account GET, no functionality defined");

        responseOut.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        Gson gson = new Gson();

        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        String result = Authorization.createAccount(email, name, password);

        responseOut.println(gson.toJson(result));

        responseOut.flush();
    }
}