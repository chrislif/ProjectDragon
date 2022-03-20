package controller;

import controller.function.Authorization;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Public extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        switch (action) {
            
            default:
                
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseOut = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        switch (action) {
            
            case "Login":
                
                responseOut.println("login successful");
                break;
                
            case "Logout":
                
                break;
                
            default:
                
                break;
        }
        
        responseOut.flush();
    }
    
    public Public() {}
    
    @Override
    public String getServletInfo() {
        return "Hello Public Servlet";
    }
}
