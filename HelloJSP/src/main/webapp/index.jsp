<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello JSP</title>
</head>
<body>
<h1>Hello JSP - 64CNTT-CLC1</h1>
<%= new Date().toString() %>
<%
int x = 5;
int y = x + 100;
%>
<hr>
<%=y %>
<hr>
<%
out.print(y);
%>
</body>
</html>