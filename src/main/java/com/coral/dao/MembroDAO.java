package com.coral.dao;

import java.util.List;

import com.coral.model.Membro;
import com.coral.model.Membro.TipoFuncao;
import com.coral.util.JPAUtil;
import com.coral.util.SenhaUtil; 

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class MembroDAO {


    public void salvar(Membro membro) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            
            if (membro.getSenha() != null && !membro.getSenha().isEmpty()) {
                membro.setSenha(SenhaUtil.hashPassword(membro.getSenha()));
            }
            
            if (membro.getId() == null) {
                em.persist(membro);
            } else {
                em.merge(membro);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Erro ao salvar o Membro: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Membro buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Membro.class, id);
        } finally {
            em.close();
        }
    }
    
    public void deletar(Integer id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Membro membro = em.find(Membro.class, id);
            if (membro != null) {
                em.remove(em.contains(membro) ? membro : em.merge(membro));
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Erro ao deletar o Membro: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    public List<Membro> listarTodos() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT m FROM Membro m ORDER BY m.nome", Membro.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Membro> listarPorFuncao(TipoFuncao funcao) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT m FROM Membro m WHERE m.tipoFuncao = :funcao AND m.ativo = TRUE ORDER BY m.nome";
            
            return em.createQuery(jpql, Membro.class)
                     .setParameter("funcao", funcao)
                     .getResultList();
        } finally {
            em.close();
        }
    }
    
    public Membro buscarPorEmail(String email) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT m FROM Membro m WHERE m.email = :email AND m.ativo = TRUE";
            return em.createQuery(jpql, Membro.class)
                     .setParameter("email", email)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null; 
        } finally {
            em.close();
        }
    }
}