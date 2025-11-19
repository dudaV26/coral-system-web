<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.servlet.jsp.jstl.core" prefix="c" %>
<%@ taglib uri="jakarta.servlet.jsp.jstl.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro de Presença</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f9; }
        .container { max-width: 800px; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        h1 { color: #333; border-bottom: 2px solid #5cb85c; padding-bottom: 10px; }
        h3 { color: #5cb85c; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #5cb85c; color: white; }
        .centralizado { text-align: center; }
        button { 
            margin-top: 20px; padding: 10px 15px; background-color: #007bff; color: white; 
            border: none; border-radius: 5px; cursor: pointer; transition: background-color 0.3s; 
        }
        button:hover { background-color: #0056b3; }
        .voltar { text-decoration: none; color: #666; margin-left: 10px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Marcação de Presença</h1>
        
        <h2>
            Evento: <c:out value="${evento.tipo}" /> 
            em <fmt:formatDate value="${evento.dataHora}" pattern="dd/MM/yyyy HH:mm"/>
        </h2>
        <h3>Descrição: <c:out value="${evento.descricao}" /></h3>
    
        <form action="presencas" method="POST">
            <input type="hidden" name="idEvento" value="${evento.id}">
            
            <table>
                <thead>
                    <tr>
                        <th>Nome do Membro</th>
                        <th>Função</th>
                        <th>Detalhe (Voz/Inst.)</th>
                        <th class="centralizado">Presente</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="membro" items="${listaMembros}">
                        <tr>
                            <td><c:out value="${membro.nome}" /></td>
                            <td><c:out value="${membro.tipoFuncao}" /></td>
                            <td><c:out value="${membro.detalheFuncao}" /></td>
                            <td class="centralizado">
                                <%-- O nome do checkbox DEVE ser 'presente_' + ID do Membro --%>
                                <input type="checkbox" 
                                       name="presente_${membro.id}" 
                                       <c:if test="${membro.presencaTemporaria.presente}">checked</c:if>>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <button type="submit">Salvar Presenças</button>
            <a href="eventos?acao=listar" class="voltar">Cancelar e Voltar para Agenda</a>
        </form>
    </div>
</body>
</html>