<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
        double a = Double.parseDouble(request.getParameter("a"));
        double b = Double.parseDouble(request.getParameter("b"));
        String operation = request.getParameter("operation");
        double result = 0;
        
        switch (operation) {
            case "add":
                result = a + b;
                break;
            case "subtract":
                result = a - b;
                break;
            case "multiply":
                result = a * b;
                break;
            case "divide":
                if (b != 0) {
                    result = a / b;
                } else {
                    out.println("Không thể chia cho 0");
                    return;
                }
                break;
            default:
                out.println("Phép toán không hợp lệ");
                return;
        }
    %>
    <h2>Kết quả:</h2>
    <p><%= a %> <%= operation.equals("add") ? "+" : operation.equals("subtract") ? "-" : operation.equals("multiply") ? "*" : "/" %> <%= b %> = <%= result %></p>
    <a href="formtinhtoan.html">Quay lại</a>
</body>
</html>