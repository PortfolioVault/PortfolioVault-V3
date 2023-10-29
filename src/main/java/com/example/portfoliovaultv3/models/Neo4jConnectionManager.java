package com.example.portfoliovaultv3.models;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;


@Named
@ApplicationScoped
public class Neo4jConnectionManager {
    private static String NEO4J_URI = "bolt://localhost:7687";
    private static String NEO4J_USERNAME = "neo4j";
    private static String NEO4J_PASSWORD = "neo4j";
    private static Driver neo4jDriver;


//    static {
//        try {
//            neo4jDriver = GraphDatabase.driver(NEO4J_URI, AuthTokens.basic(NEO4J_USERNAME, NEO4J_PASSWORD));
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }
//
//    public static Driver getNeo4jDriver() {
//        return neo4jDriver;
//    }

    // Créez une instance du pilote Neo4j
    static {
        try (Driver driver = GraphDatabase.driver(NEO4J_URI, AuthTokens.basic(NEO4J_USERNAME, NEO4J_PASSWORD))) {
            // Ouvrez une session
            try (Session session = driver.session()) {
                // Exécutez une requête Cypher
                Result result = session.run("MATCH (n) RETURN n LIMIT 5");

                // Traitez les résultats
                while (result.hasNext()) {
                    Record record = result.next();
                    System.out.println(record.get("n").asNode().asMap());
                }
            }
        }
    }
}

