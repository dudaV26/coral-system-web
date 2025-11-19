package com.coral.controller;

import java.io.IOException;
import java.util.List;

import com.coral.dao.EventoDAO;
import com.coral.dao.MembroDAO;
import com.coral.dao.PresencaDAO;
import com.coral.model.Evento;
import com.coral.model.Membro;
import com.coral.model.Presenca;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/presencas")
public class PresencaServlet extends HttpServlet {
    
    private MembroDAO membroDAO;
    private EventoDAO eventoDAO;
    private PresencaDAO presencaDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.membroDAO = new MembroDAO();
        this.eventoDAO = new EventoDAO();
        this.presencaDAO = new PresencaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
        
        String idEventoParam = request.getParameter("idEvento");
        
        if (idEventoParam == null || idEventoParam.isEmpty()) {
            response.sendRedirect("eventos?acao=listar");
            return;
        }

        Integer idEvento = Integer.parseInt(idEventoParam);
        
        Evento evento = eventoDAO.buscarPorId(idEvento);
        if (evento == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Evento não encontrado.");
            return;
        }

        List<Membro> listaMembros = membroDAO.listarTodos();

        for (Membro membro : listaMembros) {
            Presenca presenca = presencaDAO.buscarPorEventoEMembro(idEvento, membro.getId());
            
            if (presenca == null) {
                presenca = new Presenca();
                presenca.setMembro(membro);
                presenca.setEvento(evento);
                presenca.setPresente(false); 
            }
            membro.setPresencaTemporaria(presenca);
        }
        
        request.setAttribute("evento", evento);
        request.setAttribute("listaMembros", listaMembros);
        
        request.getRequestDispatcher("/WEB-INF/views/presenca/registrarPresencas.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        Integer idEvento = Integer.parseInt(request.getParameter("idEvento"));
        
        List<Membro> listaMembros = membroDAO.listarTodos();
        Evento evento = eventoDAO.buscarPorId(idEvento);

        if (evento == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Evento não encontrado.");
            return;
        }

        for (Membro membro : listaMembros) {
            
            String paramPresente = request.getParameter("presente_" + membro.getId());
            
            boolean presente = "on".equals(paramPresente); 

            Presenca presenca = presencaDAO.buscarPorEventoEMembro(idEvento, membro.getId());

            if (presenca == null) {
                presenca = new Presenca();
                presenca.setMembro(membro);
                presenca.setEvento(evento);
            }
            
            presenca.setPresente(presente);

            presencaDAO.salvar(presenca);
        }
        
        response.sendRedirect("eventos?acao=listar");
    }
}