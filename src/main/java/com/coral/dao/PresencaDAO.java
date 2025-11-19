package com.coral.dao;

import java.util.List;

import com.coral.model.Presenca;
import com.coral.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class PresencaDAO {

    public Presenca buscarPorEventoEMembro(Integer idEvento, Integer idMembro) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT p FROM Presenca p WHERE p.evento.id = :idEvento AND p.membro.id = :idMembro";
            
            return em.createQuery(jpql, Presenca.class)
                     .setParameter("idEvento", idEvento)
                     .setParameter("idMembro", idMembro)
                     .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null; 
        } finally {
            em.close();
        }
    }

    public void salvar(Presenca presenca) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (presenca.getId() == null) {
                em.persist(presenca);
            } else {
                em.merge(presenca);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Erro ao salvar a Presença: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    public List<Presenca> listarPorEvento(Integer idEvento) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT p FROM Presenca p JOIN FETCH p.membro m WHERE p.evento.id = :idEvento ORDER BY m.nome";
            
            return em.createQuery(jpql, Presenca.class)
                     .setParameter("idEvento", idEvento)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public Long contarPresencasPorMembro(Integer idMembro) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT COUNT(p) FROM Presenca p WHERE p.membro.id = :idMembro AND p.presente = TRUE";
            
            return em.createQuery(jpql, Long.class)
                     .setParameter("idMembro", idMembro)
                     .getSingleResult();
        } finally {
            em.close();
        }
    }
    
}