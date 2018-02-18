package com.anthony;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

// com.lei.webapp.quickstart.HelloServlet
public class SimpleBackend extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        out.println( "Hello World!" );
        out.flush();
        out.close();
    }
}