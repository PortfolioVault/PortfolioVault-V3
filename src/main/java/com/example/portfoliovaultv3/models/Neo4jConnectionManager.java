package com.example.portfoliovaultv3.models;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.springframework.data.neo4j.core.Neo4jClient;

@Named@ApplicationScoped @NoArgsConstructor
@Slf4j
public class Neo4jConnectionManager implements AutoCloseable  {
    private static String NEO4J_URI = "bolt://localhost:7687";
    private static String NEO4J_USERNAME = "neo4j";
    private static String NEO4J_PASSWORD = "portfolio";
    private static Driver neo4jDriver;
    private static Neo4jClient mongoClient;

    private Driver driver;

    public Neo4jConnectionManager(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }


    @Override
    public void close() throws RuntimeException {
        driver.close();
    }


    public static void main(String... args) {
//        try (var greeter = new Neo4jConnectionManager("bolt://localhost:7687", "neo4j", "portfolio")) {

        try{
            neo4jDriver = GraphDatabase.driver(NEO4J_URI, AuthTokens.basic(NEO4J_USERNAME, NEO4J_PASSWORD));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Driver getNeo4jDriver() {
        return neo4jDriver;
    }


//    static {
//        try {
//            neo4jDriver = GraphDatabase.driver(NEO4J_URI, AuthTokens.basic(NEO4J_USERNAME, NEO4J_PASSWORD));
////            Connection con = (Connection) DriverManager.getConnection("jdbc:neo4j:bolt://localhost", "neo4j", NEO4J_PASSWORD);
//
////                try{
////                    mongoClient = Neo4jClient.create(neo4jDriver);
////                }catch (Exception exception){
////                    exception.printStackTrace();
////                }
//
//            // Ouvrez une session
//            try (Session session = neo4jDriver.session()) {
//                // Exécutez une requête Cypher
//                Result result = session.run("MATCH (n) RETURN n LIMIT 5");
//
//                // Traitez les résultats
//                while (result.hasNext()) {
//                    Record record = result.next();
//                    System.out.println(record.get("n").asNode().asMap());
//                }
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }


}

