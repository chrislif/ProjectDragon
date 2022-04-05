package controller;

import controller.function.Authorization;
import com.google.gson.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
        ArrayList<String> errorList = new ArrayList<>();
        Gson gson = new Gson();

        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        Boolean creationFlag = Authorization.createAccount(email, name, password, errorList);

        if (creationFlag) {
            model.Account user = Authorization.loginUser(email, password, errorList);

            if (errorList.size() > 0) {
                responseOut.println(gson.toJson(errorList));
            } 
            else {
                if (user != null) {
                    responseOut.println(gson.toJson(user));
                }
                else {
                    responseOut.println(gson.toJson("Unable to Login created user"));
                }
            }
        } 
        else {
            if (errorList.size() > 0) {
                responseOut.println(gson.toJson(errorList));
            } 
            else {
                responseOut.println(gson.toJson("Unable to create user"));
            }
        }

        responseOut.flush();
    }
}