package controller;

import controller.function.Authorization;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author chris
 */
public class Public extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url;
        
        String action = request.getParameter("action");
        
        switch (action) {
            
            default:
                url = "/index.jsp";
                break;
        }
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url;
        
        String action = request.getParameter("action");
        
        switch (action) {
            
            case "toHome":
                String result = Authorization.testDBConnection();
                
                request.setAttribute("result", result);
                url = "/index.jsp";
                break;
            
            default:
                url = "/index.jsp";
                break;
        }
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
    public Public() {}
    
    @Override
    public String getServletInfo() {
        return "Hello Public Servlet";
    }
}
