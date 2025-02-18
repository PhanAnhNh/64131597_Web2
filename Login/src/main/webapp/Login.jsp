<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*, javax.servlet.*, javax.servlet.http.*" %>
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if("ABC".equals(username) && "MNK".equals(password)) {
        response.sendRedirect("UserProfile.html");
    } else {
%>
        <script>
            alert("Sai tên đăng nhập hoặc mật khẩu! Vui lòng thử lại.");
            window.location.href = "login.html";
        </script>
<%
    }
%>
