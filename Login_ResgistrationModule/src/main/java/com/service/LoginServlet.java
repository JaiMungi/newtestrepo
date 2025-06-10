package com.service;

import java.io.IOException;

import com.dao.UserDAOImpl;
import com.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAOImpl userDAOImpl;

    @Override
    public void init() {
        userDAOImpl = new UserDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userDAOImpl.getUserByEmailAndPassword(email, password);

        if (user != null) {
            // Login success
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("welcome.jsp"); // Redirect to a welcome page (you can create it)
        } else {
            // Login failure
            request.setAttribute("errorMessage", "Invalid email or password");
            request.getRequestDispatcher("login.html").forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.sendRedirect("login.html");
    }
}

