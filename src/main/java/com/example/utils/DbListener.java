package com.example.utils;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DbListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        DBConnection.getInstance();
    }

    public void contextDestroyed(ServletContextEvent event) {
        DBConnection.getInstance().destroy();
    }
}