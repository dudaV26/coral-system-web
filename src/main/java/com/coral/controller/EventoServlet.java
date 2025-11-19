package com.coral.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.coral.dao.EventoDAO;
import com.coral.model.Evento;
import com.coral.model.Evento.TipoEvento;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/eventos")
public class EventoServlet extends HttpServlet {
    
    private EventoDAO eventoDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.eventoDAO = new EventoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        if (acao == null) acao = "listar";

        try {
            switch (acao) {
                case "listar":
                    listarEventos(request, response);
                    break;
                case "novoForm":
                    mostrarFormulario(request, response);
                    break;
                case "editarForm":
                    mostrarFormulario(request, response);
                    break;

                default:
                    listarEventos(request, response);
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
            salvarEvento(request, response);
        } else if ("deletar".equals(acao)) {
            deletarEvento(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ação POST não reconhecida.");
        }
    }
    
    private void listarEventos(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        List<Evento> listaEventos = eventoDAO.listarTodos();
        request.setAttribute("listaEventos", listaEventos);
        
        request.getRequestDispatcher("/WEB-INF/views/evento/listaEventos.jsp").forward(request, response);
    }
    
    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        Evento evento = new Evento();
        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            Integer id = Integer.parseInt(idParam);
            evento = eventoDAO.buscarPorId(id);
        }
        
        request.setAttribute("evento", evento);
        request.getRequestDispatcher("/WEB-INF/views/evento/formEvento.jsp").forward(request, response);
    }
    
    private void salvarEvento(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        try {
            String idParam = request.getParameter("id");
            Integer id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : null;
            
            String tipoStr = request.getParameter("tipo");
            String dataHoraStr = request.getParameter("dataHora"); 
            String local = request.getParameter("local");
            String descricao = request.getParameter("descricao");
            
            Evento evento = (id == null) ? new Evento() : eventoDAO.buscarPorId(id);
            
            evento.setTipo(TipoEvento.valueOf(tipoStr));
            
            evento.setDataHora(LocalDateTime.parse(dataHoraStr)); 
            
            evento.setLocal(local);
            evento.setDescricao(descricao);
            
            eventoDAO.salvar(evento);
            
        } catch (DateTimeParseException e) {
            System.err.println("Erro de formato de data: " + e.getMessage());
        }
        
        response.sendRedirect("eventos?acao=listar");
    }
    
    private void deletarEvento(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        eventoDAO.deletar(id); 
        response.sendRedirect("eventos?acao=listar");
    }
}