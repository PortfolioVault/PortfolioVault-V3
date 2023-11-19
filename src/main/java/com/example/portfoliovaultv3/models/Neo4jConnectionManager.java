package com.example.portfoliovaultv3.models;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

@Named @ApplicationScoped

public class Neo4jConnectionManager  {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration.Builder()
                .uri("bolt://localhost:7687")
                .credentials("neo4j", "fatifati12")
                .build();

        return new SessionFactory(configuration, "com.example.portfoliovaultv3.models.*");
    }

    public static Session getNeo4jSession() {
        return  sessionFactory.openSession();
    }


}

