//package com.example.utils;
//
//import jakarta.servlet.ServletContextEvent;
//import jakarta.servlet.ServletContextListener;
//import jakarta.servlet.annotation.WebListener;
//
//import java.sql.Connection;
//
//@WebListener
//public class DbListener implements ServletContextListener {
//    public void contextInitialized(ServletContextEvent sce) {
//        Connection connection = DBConnection.getConnection();
//    }
//
//    public void contextDestroyed(ServletContextEvent sce) {
//        DBConnection.releaseConnection(DBConnection.getConnection());
//    }
//}
