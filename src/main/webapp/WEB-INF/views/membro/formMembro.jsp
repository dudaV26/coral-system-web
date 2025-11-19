<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.servlet.jsp.jstl.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulário de Membro</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        form { width: 400px; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input[type="text"], input[type="email"], input[type="password"], select { 
            width: 100%; padding: 8px; margin-top: 5px; box-sizing: border-box; 
        }
        button { margin-top: 20px; padding: 10px 15px; background-color: #007bff; color: white; border: none; border-radius: 5px; cursor: pointer; }
    </style>
</head>
<body>
    
    <h1><c:if test="${membro != null}">Editar Membro</c:if><c:if test="${membro == null}">Novo Membro</c:if></h1>
    
    <form action="membros" method="POST">
        <input type="hidden" name="acao" value="salvar">
        
        <c:if test="${membro != null}">
            <input type="hidden" name="id" value="${membro.id}">
        </c:if>

        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" value="<c:out value="${membro.nome}"/>" required>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="<c:out value="${membro.email}"/>" required>
        
        <label for="senha">Senha:</label>
        <input type="password" id="senha" name="senha" <c:if test="${membro == null}">required</c:if> placeholder="<c:if test="${membro != null}">Deixe em branco para não alterar</c:if>">

        <label for="tipoFuncao">Função:</label>
        <select id="tipoFuncao" name="tipoFuncao" required>
            <option value="">Selecione a função</option>
            <option value="CORISTA" <c:if test="${membro.tipoFuncao == 'CORISTA'}">selected</c:if>>Corista</option>
            <option value="MUSICO" <c:if test="${membro.tipoFuncao == 'MUSICO'}">selected</c:if>>Músico</option>
        </select>
        
        <label for="detalheFuncao">Detalhe (Voz/Instrumento):</label>
        <input type="text" id="detalheFuncao" name="detalheFuncao" value="<c:out value="${membro.detalheFuncao}"/>">

        <button type="submit">Salvar Membro</button>
        <a href="membros?acao=listar">Cancelar</a>
    </form>
</body>
</html>