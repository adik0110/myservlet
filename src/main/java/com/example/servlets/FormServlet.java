package com.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class FormServlet extends SimpleServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");
        out.println(showForm());
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Enumeration<String> paramNames = req.getParameterNames();
        boolean emptyEnum = true;

        while (paramNames.hasMoreElements()) {
            String parName = paramNames.nextElement();
            String parValue = req.getParameter(parName);
            if (parValue != null && !parValue.trim().isEmpty()) {
                emptyEnum = false;
                break;
            }
        }

        PrintWriter out = res.getWriter();
        res.setContentType("text/html");

        if (emptyEnum) {
            out.println(showValues("<h1>Request doesn't contain any parameters</h1>"));
        } else {
            StringBuilder content = new StringBuilder();
            content.append("<h1>Here are the values</h1>");
            paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String parName = paramNames.nextElement();
                String parValue = req.getParameter(parName);
                content.append("<p>").append(parName).append(": ").append(parValue).append("</p>");
            }
            out.println(showValues(content.toString()));
        }
    }

    private String showForm() {
        return "<!DOCTYPE html><html>"
                + "<head><meta charset='UTF-8'><title>Form</title></head>"
                + "<body>"
                + "<h1>Please submit your information</h1>"
                + "<form action='' method='POST'>"
                + "<label for='first_name'>First name:</label><br>"
                + "<input type='text' id='first_name' name='first_name'><br>"
                + "<label for='last_name'>Last name:</label><br>"
                + "<input type='text' id='last_name' name='last_name'><br>"
                + "<label for='email'>Email:</label><br>"
                + "<input type='email' id='email' name='email'><br><br>"
                + "<input type='submit' value='Submit info'>"
                + "</form>"
                + "</body></html>";
    }

    private String showValues(String content) {
        return "<!DOCTYPE html><html>"
                + "<head><meta charset='UTF-8'><title>Values</title></head>"
                + "<body>"
                + content
                + "</body></html>";
    }
}
