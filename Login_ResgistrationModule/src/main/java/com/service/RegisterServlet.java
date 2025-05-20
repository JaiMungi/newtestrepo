package com.service;

import java.io.IOException;

import com.dao.UserDAO;
import com.dao.UserDAOImpl;
import com.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	private UserDAOImpl userDAOImpl;

    @Override
    public void init() {
    	userDAOImpl = new UserDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        String errorMessage = null;

        if (fullName == null || fullName.trim().isEmpty()) {
            errorMessage = "Full name is required.";
        } else if (email == null || email.trim().isEmpty()) {
            errorMessage = "Email is required.";
        } else if (password == null || password.trim().isEmpty()) {
            errorMessage = "Password is required.";
        } else if (!password.equals(confirmPassword)) {
            errorMessage = "Passwords do not match.";
        } else if (userDAOImpl.getUserByEmail(email) != null) {
            errorMessage = "Email is already registered.";
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("register.html").forward(request, response);
            return;
        }

        User user = new User(fullName, email, password);
        boolean success = userDAOImpl.registerUser(user);

        if (success) {
            // Registration success, redirect to login page
            response.sendRedirect("login.html?registerSuccess=true");
        } else {
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            request.getRequestDispatcher("register.html").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.sendRedirect("register.html");
    }
}

