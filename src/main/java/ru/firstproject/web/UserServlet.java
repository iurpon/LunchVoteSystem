package ru.firstproject.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends javax.servlet.http.HttpServlet {
    public static Logger logger = LoggerFactory.getLogger(UserServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(System.getProperty("user.dir"));
        logger.info("UserServlet doGet method");
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
