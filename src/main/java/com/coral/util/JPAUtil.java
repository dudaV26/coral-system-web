package com.coral.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    
    private static final EntityManagerFactory FACTORY = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            return Persistence.createEntityManagerFactory("coral-pu");
        } catch (Exception ex) {
            System.err.println("Erro ao inicializar a EntityManagerFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return FACTORY;
    }

    public static void closeEntityManagerFactory() {
        if (FACTORY != null && FACTORY.isOpen()) {
            FACTORY.close();
        }
    }
}