/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.big3_2020.gestionetudiant.Controller;

/**
 *
 * @author HP
 */
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;

public abstract class HibernateConfiguration {

    private static SessionFactory ourSessionFactory;

    private HibernateConfiguration(){}

    public static synchronized SessionFactory getSessionFactory() {

        if (ourSessionFactory == null) {
            ourSessionFactory = new Configuration().configure("database/hibernate.cfg.xml").
                    buildSessionFactory();
        }
        return ourSessionFactory;
    }

    public static void initDatabase() throws Exception {
        final Session session = getSessionFactory().openSession();
        try {
            System.out.println("querying all the managed entities...");
            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                final String entityName = entityType.getName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
            }
        } finally {
            session.close();
        }
    }
}
