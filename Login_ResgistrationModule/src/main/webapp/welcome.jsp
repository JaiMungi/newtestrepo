<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true" %>
<%@ page import="com.model.User" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        // User not logged in, redirect to login page
        response.sendRedirect("login.html");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Welcome</title>
<style>
  body {
    font-family: Arial, sans-serif;
    background: linear-gradient(135deg, #667eea, #764ba2);
    color: #fff;
    min-height: 100vh;
    margin: 0;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .welcome-container {
    background: rgba(0,0,0,0.6);
    padding: 40px 50px;
    border-radius: 14px;
    text-align: center;
    box-shadow: 0 8px 24px rgba(0,0,0,0.3);
    max-width: 400px;
    width: 90vw;
  }
  h1 {
    margin-bottom: 20px;
    font-weight: 700;
    font-size: 2.5rem;
  }
  p {
    font-size: 1.25rem;
    margin-bottom: 30px;
  }
  a.logout-btn {
    display: inline-block;
    padding: 12px 28px;
    background-color: #764ba2;
    color: white;
    text-decoration: none;
    border-radius: 10px;
    font-weight: bold;
    transition: background-color 0.3s ease;
  }
  a.logout-btn:hover {
    background-color: #5d3782;
  }
</style>
</head>
<body>
  <div class="welcome-container" role="main" aria-label="Welcome Page">
    <h1>Welcome, <%= user.getFullName() %>!</h1>
    <p>You have successfully logged in.</p>
    <a class="logout-btn" href="logout">Logout</a>
  </div>
</body>
</html>