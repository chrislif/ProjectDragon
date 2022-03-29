package controller;

import controller.function.Authorization;
import com.google.gson.*;
import model.Account;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        Gson gson = new Gson();

        responseOut.println(gson.toJson("Login GET, no functionality defined"));

        responseOut.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter responseOut = response.getWriter();
    	ArrayList<String> errorList = new ArrayList<>();
    	Gson gson = new Gson();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Account user = Authorization.loginUser(email, password, errorList);
        
        if (errorList.size() > 0) {
        	responseOut.println(gson.toJson(errorList));
        }
        else {
        	responseOut.println(gson.toJson(user));
        }

        responseOut.flush();
    }
}
