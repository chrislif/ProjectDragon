package controller;

import model.Character;
import controller.function.CharacterManager;

import com.google.gson.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DisplayCharacter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        Gson gson = new Gson();

        ArrayList<String> errorList = new ArrayList<>();
        int characterID = Integer.parseInt(request.getParameter("characterID"));

        Character selectedCharacter = CharacterManager.getCharacterByID(characterID, errorList);

        if (errorList.size() > 0) {
        	responseOut.println(gson.toJson(errorList));
        }
        else {
            if (selectedCharacter == null) {
                responseOut.println(gson.toJson("Error"));
            }
            else {
        	    responseOut.println(gson.toJson(selectedCharacter));
            }
        }

        responseOut.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        Gson gson = new Gson();

        responseOut.println(gson.toJson("Display POST, no functionality defined"));

        responseOut.flush();
    }
}