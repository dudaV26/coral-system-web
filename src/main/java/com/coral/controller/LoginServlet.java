package com.coral.controller;

import java.io.IOException;

import com.coral.dao.MembroDAO;
import com.coral.model.Membro;
import com.coral.util.SenhaUtil;
import com.coral.util.TokenUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
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
        if ("logout".equals(acao)) {
            request.getSession().invalidate();
            
            Cookie jwtCookie = new Cookie("JWT_TOKEN", "");
            jwtCookie.setMaxAge(0); 
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);
            
            response.sendRedirect("login");
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/views/login/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        
        Membro membro = membroDAO.buscarPorEmail(email);
        
        if (membro != null && SenhaUtil.checkPassword(senha, membro.getSenha())) {
            String token = TokenUtil.generateToken(membro.getEmail());
            
            Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
            jwtCookie.setMaxAge(60 * 60 * 4); 
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);
            
            response.sendRedirect("eventos?acao=listar");
        } else {
            request.setAttribute("erro", "Email ou senha inválidos.");
            request.getRequestDispatcher("/WEB-INF/views/login/login.jsp").forward(request, response);
        }
    }
}