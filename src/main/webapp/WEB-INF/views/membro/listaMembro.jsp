<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.servlet.jsp.jstl.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Membros do Coral</title>
    <style>
        body { font-family: Arial, sans-serif; }
        table { width: 80%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <h1>Gestão de Membros (Coristas e Músicos)</h1>
    
    <p><a href="membros?acao=novoForm">Adicionar novo Membro</a></p>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Função</th>
                <th>Detalhe</th>
                <th>Email</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="membro" items="${listaMembros}">
                <tr>
                    <td><c:out value="${membro.id}" /></td>
                    <td><c:out value="${membro.nome}" /></td>
                    <td><c:out value="${membro.tipoFuncao}" /></td>
                    <td><c:out value="${membro.detalheFuncao}" /></td>
                    <td><c:out value="${membro.email}" /></td>
                    <td>
                        <a href="membros?acao=editarForm&id=${membro.id}">Editar</a> 
                        
                        <form action="membros" method="POST" style="display:inline;">
                            <input type="hidden" name="acao" value="deletar">
                            <input type="hidden" name="id" value="${membro.id}">
                            <button type="submit" onclick="return confirm('Tem certeza que deseja deletar?')">Deletar</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>