package controller;

import controller.function.Authorization;
import com.google.gson.*;

import model.Character;
import controller.function.CharacterManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class AddCharacter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        Gson gson = new Gson();

        responseOut.println(gson.toJson("Character GET, no functionality defined"));

        responseOut.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ArrayList<String> errorList = new ArrayList<>();
        PrintWriter responseOut = response.getWriter();
        Gson gson = new Gson();

        Character newCharacter = gson.fromJson(request.getParameter("character"), Character.class);

        Boolean isCharacterCreated = CharacterManager.createCharacter(newCharacter, errorList);

        if (errorList.size() > 0) {
            responseOut.println(gson.toJson(errorList));
        }
        else {
            responseOut.println(gson.toJson("Character Created!"));
        }

        responseOut.flush();
    }
}
