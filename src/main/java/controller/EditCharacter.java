package controller;

import model.Character;
import controller.function.CharacterManager;

import com.google.gson.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class EditCharacter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        Gson gson = new Gson();

        responseOut.println(gson.toJson("Edit GET, no functionality defined"));

        responseOut.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        ArrayList<String> errorList = new ArrayList<>();
        Gson gson = new Gson();

        Character editedCharacter = gson.fromJson(request.getParameter("character"), Character.class);

        CharacterManager.deleteCharacter(editedCharacter, errorList);
        CharacterManager.createCharacter(editedCharacter, errorList);

        if (errorList.size() > 0) {
            responseOut.println(gson.toJson(errorList));
        }
        else {
            responseOut.println(gson.toJson("character edited"));
        }
        responseOut.flush();
    }
}
