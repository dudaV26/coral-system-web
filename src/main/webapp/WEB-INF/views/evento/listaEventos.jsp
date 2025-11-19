<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.servlet.jsp.jstl.core" prefix="c" %>
<%@ taglib uri="jakarta.servlet.jsp.jstl.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agenda de Eventos</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { width: 90%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <h1>Agenda de Eventos (Ensaios e Apresentações)</h1>
    
    <p><a href="eventos?acao=novoForm">Adicionar Novo Evento</a></p>

    <table>
        <thead>
            <tr>
                <th>Tipo</th>
                <th>Data e Hora</th>
                <th>Local</th>
                <th>Descrição</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="evento" items="${listaEventos}">
                <tr>
                    <td><c:out value="${evento.tipo}" /></td>
                    <td>
                        <%-- Formata o LocalDateTime para um formato legível --%>
                        <fmt:formatDate value="${evento.dataHora}" pattern="dd/MM/yyyy HH:mm"/>
                    </td>
                    <td><c:out value="${evento.local}" /></td>
                    <td><c:out value="${evento.descricao}" /></td>
                    <td>
                        <a href="eventos?acao=editarForm&id=${evento.id}">Editar</a> 
                        
                        <form action="eventos" method="POST" style="display:inline;">
                            <input type="hidden" name="acao" value="deletar">
                            <input type="hidden" name="id" value="${evento.id}">
                            <button type="submit" onclick="return confirm('Tem certeza que deseja deletar este evento?')">Deletar</button>
                        </form>
                        
                        <%-- Futura Ação: Ir para Presenças --%>
                        <a href="presencas?idEvento=${evento.id}">Presenças</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>