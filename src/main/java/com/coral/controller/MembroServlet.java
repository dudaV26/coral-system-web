package com.coral.controller;

import java.io.IOException;
import java.util.List;

import com.coral.dao.MembroDAO;
import com.coral.model.Membro;
import com.coral.model.Membro.TipoFuncao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/membros")
public class MembroServlet extends HttpServlet {
    
    private MembroDAO membroDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.membroDAO = new MembroDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        
        if (acao == null) {
            acao = "listar"; 
        }

        try {
            switch (acao) {
                case "listar":
                    listarMembros(request, response);
                    break;
                case "novoForm":
                    mostrarFormularioNovo(request, response);
                    break;
                case "editarForm":
                    mostrarFormularioEdicao(request, response);
                    break;
                default:
                    listarMembros(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        
        if ("salvar".equals(acao)) {
            salvarMembro(request, response);
        } else if ("deletar".equals(acao)) {
            deletarMembro(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ação POST não reconhecida.");
        }
    }
    

    private void listarMembros(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        List<Membro> listaMembros = membroDAO.listarTodos();
        
        request.setAttribute("listaMembros", listaMembros);
        
        request.getRequestDispatcher("/WEB-INF/views/membro/listaMembros.jsp").forward(request, response);
    }
    
    private void salvarMembro(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        String idParam = request.getParameter("id");
        Integer id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : null;
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha"); 
        String tipoFuncaoStr = request.getParameter("tipoFuncao");
        TipoFuncao tipoFuncao = TipoFuncao.valueOf(tipoFuncaoStr);
        String detalheFuncao = request.getParameter("detalheFuncao");
        
        Membro membro = (id == null) ? new Membro() : membroDAO.buscarPorId(id);
        
        membro.setNome(nome);
        membro.setEmail(email);
        
        if (id == null || !senha.isEmpty()) {
            membro.setSenha(senha); 
        }
        
        membro.setTipoFuncao(tipoFuncao);
        membro.setDetalheFuncao(detalheFuncao);
        
        membroDAO.salvar(membro);
        
        response.sendRedirect("membros?acao=listar");
    }
    
    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/membro/formMembro.jsp").forward(request, response);
    }
    
    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Membro membroExistente = membroDAO.buscarPorId(id);
        request.setAttribute("membro", membroExistente);
        request.getRequestDispatcher("/WEB-INF/views/membro/formMembro.jsp").forward(request, response);
    }
    
    private void deletarMembro(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        membroDAO.deletar(id);
        response.sendRedirect("membros?acao=listar");
    }
}