package com.coral.dao;

import java.util.List;

import com.coral.model.Evento;
import com.coral.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class EventoDAO {


    public void salvar(Evento evento) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (evento.getId() == null) {
                em.persist(evento); 
            } else {
                em.merge(evento); 
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Erro ao salvar o Evento: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public List<Evento> listarTodos() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Evento e ORDER BY e.dataHora DESC", Evento.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Evento> listarProximosEventos() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Evento e WHERE e.dataHora >= CURRENT_TIMESTAMP() ORDER BY e.dataHora ASC", Evento.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }
    
    public Evento buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Evento.class, id);
        } finally {
            em.close();
        }
    }
    
    public void deletar(Integer id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Evento evento = em.find(Evento.class, id);
            if (evento != null) {
                em.remove(em.contains(evento) ? evento : em.merge(evento)); 
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Erro ao deletar o Evento: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }}