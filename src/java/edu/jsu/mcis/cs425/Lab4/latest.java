package edu.jsu.mcis.cs425.Lab4;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "latest", urlPatterns = {"/latest"})
public class latest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/json;charset=UTF-8");
        
        String path = getServletContext().getRealPath(File.separator + Rates.RATE_FILENAME);
        
        try (PrintWriter out = response.getWriter()) {
            
            out.println("Servlet Test at " + request.getContextPath());
            out.println( Rates.getRatesAsJson( Rates.getRates(path)));
            
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        doGet(request, response);
        
    }
    
    @Override
    public String getServletInfo() {
        
        return "Lab #4 Test Servlet";   
        
    }
    
}