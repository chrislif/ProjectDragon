package controller;

import controller.function.Authorization;
import com.google.gson.*;
import model.Account;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Character extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        Gson gson = new Gson();

        responseOut.println(gson.toJson("Character GET, no functionality defined"));

        responseOut.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter responseOut = response.getWriter();
        Gson gson = new Gson();

        Account user = gson.fromJson(request.getParameter("user"), Account.class);
        String characterName = request.getParameter("characterName");

        

        responseOut.println(gson.toJson("Character POST, no functionality defined"));

        responseOut.flush();
    }
}
