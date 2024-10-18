package com.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This is one of the simplest example.
 */

public class SimpleServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println(getPageCode(""));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        if(name.isEmpty()){
            name = "Stranger";
        }
        String greetings = "<h1>Hello "+name+"!</h1>";

        PrintWriter out = resp.getWriter();
        out.println(getPageCode(greetings));
    }

    /**
     * Helper that generates page code for both GET and POST.
     * @param content Data to output after showing form.
     * @return HTML content for page.
     */
    private String getPageCode(String content){
        return "<!DOCTYPE html><html>"
                + "<head><meta charset='UTF-8'><title>Hello page</title></head>"
                + "<body>"
                + "<form action='' method='POST'><input type='text' name='name'><input type='submit' value='Say hello'></form>"
                + content
                + "</body></html>";
    }
}