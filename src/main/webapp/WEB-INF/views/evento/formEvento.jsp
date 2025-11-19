<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.servlet.jsp.jstl.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulário de Evento</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        form { width: 450px; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input[type="text"], input[type="datetime-local"], select, textarea { 
            width: 100%; padding: 8px; margin-top: 5px; box-sizing: border-box; 
        }
        button { margin-top: 20px; padding: 10px 15px; background-color: #28a745; color: white; border: none; border-radius: 5px; cursor: pointer; }
    </style>
</head>
<body>
    
    <h1><c:if test="${evento.id != null}">Editar Evento</c:if><c:if test="${evento.id == null}">Novo Evento</c:if></h1>
    
    <form action="eventos" method="POST">
        <input type="hidden" name="acao" value="salvar">
        
        <c:if test="${evento.id != null}">
            <input type="hidden" name="id" value="${evento.id}">
        </c:if>

        <label for="tipo">Tipo de Evento:</label>
        <select id="tipo" name="tipo" required>
            <option value="">Selecione...</option>
            <option value="ENSAIO" <c:if test="${evento.tipo == 'ENSAIO'}">selected</c:if>>Ensaio</option>
            <option value="APRESENTACAO" <c:if test="${evento.tipo == 'APRESENTACAO'}">selected</c:if>>Apresentação</option>
        </select>
        
        <label for="dataHora">Data e Hora:</label>
        <%-- O input type="datetime-local" espera o formato ISO (yyyy-MM-ddThh:mm) --%>
        <c:if test="${evento.dataHora != null}">
            <c:set var="formattedDateTime"><fmt:formatDate value="${evento.dataHora}" pattern="yyyy-MM-dd'T'HH:mm"/></c:set>
        </c:if>
        <input type="datetime-local" id="dataHora" name="dataHora" value="${formattedDateTime}" required>

        <label for="local">Local:</label>
        <input type="text" id="local" name="local" value="<c:out value="${evento.local}"/>" required>

        <label for="descricao">Descrição:</label>
        <textarea id="descricao" name="descricao" rows="4"><c:out value="${evento.descricao}"/></textarea>

        <button type="submit">Salvar Evento</button>
        <a href="eventos?acao=listar">Cancelar</a>
    </form>
</body>
</html>