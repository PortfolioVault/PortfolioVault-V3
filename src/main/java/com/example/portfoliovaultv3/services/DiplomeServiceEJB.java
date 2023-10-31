package com.example.portfoliovaultv3.services;

import com.example.portfoliovaultv3.models.Diplome;
import com.example.portfoliovaultv3.models.Neo4jConnectionManager;
import com.example.portfoliovaultv3.models.User;
import com.example.portfoliovaultv3.models.relationDetails.DiplomaDetails;
import jakarta.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.session.Session;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Stateless
@Slf4j
public class DiplomeServiceEJB {
    Session session = Neo4jConnectionManager.getNeo4jSession();

    public void addDiploma(String email, Diplome diplome, DiplomaDetails details) {
        User user = UserServiceEJB.findUserByEmail(email);
        try {
            DiplomaDetails diplomaDetails = new DiplomaDetails();
            diplomaDetails.setUser(user);
            diplomaDetails.setDiplome(diplome);
            diplomaDetails.setDegreeType(details.getDegreeType());
            diplomaDetails.setYearOfObtention(details.getYearOfObtention());

            user.getDiplomes().add(diplomaDetails); // Ajoutez le diplôme à la liste de diplômes de l'utilisateur
            session.save(user); // Enregistrez l'utilisateur avec la relation établie
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

    public Map<Diplome, DiplomaDetails> getDetailsForAllDiplomes(String email) {
        User user = UserServiceEJB.findUserByEmail(email);
        if (user == null) {
            return null;
        }

        Long userId = user.getId();
        User loadedUser = session.load(User.class, userId); // Charger l'utilisateur avec ses relations

        Map<Diplome, DiplomaDetails> diplomeDetailsMap = new HashMap<>();
        for (DiplomaDetails diplomaDetails : loadedUser.getDiplomes()) {
            Diplome diplome = diplomaDetails.getDiplome();
            diplomeDetailsMap.put(diplome, diplomaDetails);
        }

        return diplomeDetailsMap;
    }

}
