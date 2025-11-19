<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.servlet.jsp.jstl.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Portal do Coral</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f0f4f8; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-container { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.15); width: 350px; text-align: center; }
        h1 { color: #333; margin-bottom: 30px; }
        label { display: block; text-align: left; margin-top: 15px; font-weight: bold; }
        input[type="email"], input[type="password"] { width: 100%; padding: 10px; margin-top: 5px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { width: 100%; padding: 12px; margin-top: 25px; background-color: #5cb85c; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        button:hover { background-color: #4cae4c; }
        .error-message { color: #d9534f; margin-top: 15px; font-weight: bold; }
    </style>
</head>
<body>
    <div class="login-container">
        <h1>Portal do Coral</h1>
        
        <c:if test="${not empty erro}">
            <div class="error-message">${erro}</div>
        </c:if>

        <form action="login" method="POST">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required placeholder="Seu email de acesso">

            <label for="senha">Senha:</label>
            <input type="password" id="senha" name="senha" required placeholder="Sua senha">

            <button type="submit">Entrar</button>
        </form>
    </div>
</body>
</html>