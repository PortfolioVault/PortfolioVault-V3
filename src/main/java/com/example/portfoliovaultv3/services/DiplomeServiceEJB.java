package com.example.portfoliovaultv3.services;

import com.example.portfoliovaultv3.models.Diplome;
import com.example.portfoliovaultv3.models.Neo4jConnectionManager;
import com.example.portfoliovaultv3.models.User;
import com.example.portfoliovaultv3.models.relationDetails.DiplomaDetails;
import jakarta.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.session.Session;

import java.util.*;

@Stateless
@Slf4j
public class DiplomeServiceEJB {
    static Session session = Neo4jConnectionManager.getNeo4jSession();

    public void addDiploma(String email, Diplome diplome, DiplomaDetails details) {
//        Diplome existedDiplome= findDiplomeByUniversity(diplome.getUniversity());
        User user = UserServiceEJB.findUserByEmail(email);
//        if(existedDiplome == null){
        try {
            DiplomaDetails diplomaDetails = new DiplomaDetails();
            diplomaDetails.setUser(user);
            diplomaDetails.setDiplome(diplome);
            diplomaDetails.setDegreeType(details.getDegreeType());
            diplomaDetails.setYearOfObtention(details.getYearOfObtention());
            diplomaDetails.setFieldOfStudy(details.getFieldOfStudy());

            user.getDiplomes().add(diplomaDetails); // Ajoutez le diplôme à la liste de diplômes de l'utilisateur
            session.save(user); // Enregistrez l'utilisateur avec la relation établie
        } finally {
            if (session != null) {
                session.clear(); // Optionnel : pour vider le contexte de la session
            }
        }

    }

    public static Diplome findDiplomeByUniversity(String university){
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("university", university);
            String cypher = "MATCH (d:Diplome {university: $university })--(relatedNode) RETURN d, relatedNode";
            Iterable<Diplome> result = session.query(Diplome.class, cypher, parameters);
            if (result.iterator().hasNext() ){
                return result.iterator().hasNext() ? result.iterator().next() :null ;
            }
            String cypher1 = "MATCH (p:Diplome) WHERE p.university = $university RETURN p";
            Iterable<Diplome> result1 = session.query(Diplome.class, cypher1, parameters);
            return result1.iterator().hasNext() ? result1.iterator().next() :null ;

        } finally {
            if (session != null) {
                session.clear(); // Optionnel : pour vider le contexte de la session
            }
        }
    }

    public LinkedList<Diplome> getDiplomats(String email) {
        User user = UserServiceEJB.findUserByEmail(email);
        if(user == null){
            return null;
        }
        Long userId = user.getId();
        User loadedUser = session.load(User.class, userId);
//        session.load(User.class, -1); // Charger complètement l'utilisateur avec toutes ses relations

        LinkedList<Diplome> diplomes = new LinkedList<>();
        for (DiplomaDetails diplomaDetails : loadedUser.getDiplomes()) {
            diplomes.add(diplomaDetails.getDiplome());
        }
        return diplomes;

    }

//    public Map<Diplome, DiplomaDetails> getDetailsForAllDiplomes(String email) {
//        User user = UserServiceEJB.findUserByEmail(email);
//        if (user == null) {
//            return null;
//        }
//
//        Long userId = user.getId();
//        User loadedUser = session.load(User.class, userId,1); // Charger l'utilisateur avec ses relations
//
//        Map<Diplome, DiplomaDetails> diplomeDetailsMap = new HashMap<>();
//        for (DiplomaDetails diplomaDetails : loadedUser.getDiplomes()) {
//            Diplome diplome = diplomaDetails.getDiplome();
//            diplomeDetailsMap.put(diplome, diplomaDetails);
//        }
//
//        return diplomeDetailsMap;
//    }

    public List<Map<String, Object>> getDetailsForAllDiplomes(String email) {
        User user = UserServiceEJB.findUserByEmail(email);
        if (user == null) {
            return null;
        }

        Long userId = user.getId();
        User loadedUser = session.load(User.class, userId, 1); // Charger l'utilisateur avec ses relations

        List<Map<String, Object>> diplomeDetailsList = new ArrayList<>();
        for (DiplomaDetails diplomaDetails : loadedUser.getDiplomes()) {
            Diplome diplome = diplomaDetails.getDiplome();

            Map<String, Object> entry = new HashMap<>();
            entry.put("diplome", diplome);
            entry.put("diplomaDetails", diplomaDetails);

            diplomeDetailsList.add(entry);
        }

        return diplomeDetailsList;
    }



}
