package com.example.portfoliovaultv3.services;


import com.example.portfoliovaultv3.models.Company;
import com.example.portfoliovaultv3.models.Diplome;
import com.example.portfoliovaultv3.models.Neo4jConnectionManager;
import com.example.portfoliovaultv3.models.User;
import com.example.portfoliovaultv3.models.relationDetails.CompanyDetails;
import jakarta.ejb.Stateless;
import org.neo4j.ogm.session.Session;

import java.util.*;
import java.util.logging.Logger;


@Stateless
public class ExperienceServiceEJB {
    private Logger logger = Logger.getLogger(String.valueOf(ExperienceServiceEJB.class));
    Session session = Neo4jConnectionManager.getNeo4jSession();

    public void addExperience(String email, Company company, CompanyDetails details) {
        User user = UserServiceEJB.findUserByEmail(email);
        try {
            CompanyDetails companyDetails=new CompanyDetails();
            companyDetails.setUser(user);
            companyDetails.setCompany(company);
            companyDetails.setEndDay(details.getEndDay());
            companyDetails.setStartDay(details.getStartDay());
            companyDetails.setJobTitle(details.getJobTitle());

            user.getCompanies().add(companyDetails); // Ajoutez le diplôme à la liste de diplômes de l'utilisateur
            session.save(user); // Enregistrez l'utilisateur avec la relation établie
        } finally {
            if (session != null) {
                session.clear(); // Optionnel : pour vider le contexte de la session
            }
        }
    }

    public LinkedList<Company> getExperiences(String email) {
        User user = UserServiceEJB.findUserByEmail(email);
        if(user == null){
            return null;
        }
        Long userId = user.getId();
        User loadedUser = session.load(User.class, userId);
//        session.load(User.class, -1); // Charger complètement l'utilisateur avec toutes ses relations

        LinkedList<Company> experiences = new LinkedList<>();
        for (CompanyDetails companyDetails : loadedUser.getCompanies()) {
            experiences.add(companyDetails.getCompany());
        }
        return experiences;

    }

    public LinkedList<CompanyDetails> getExperiencesDetails(String email) {
        User user = UserServiceEJB.findUserByEmail(email);
        if(user == null){
            return null;
        }
        Long userId = user.getId();
        User loadedUser = session.load(User.class, userId,2);

        LinkedList<CompanyDetails> experiences = new LinkedList<>();
        for (CompanyDetails companyDetails : loadedUser.getCompanies()) {
            experiences.add(companyDetails);
        }
        return experiences;

    }

    public Map<Company, CompanyDetails> getDetailsForAllCompanies(String email) {
        User user = UserServiceEJB.findUserByEmail(email);
        if (user == null) {
            return null;
        }

        Long userId = user.getId();
        User loadedUser = session.load(User.class, userId); // Charger l'utilisateur avec ses relations

        Map<Company, CompanyDetails> companyDetailsMap = new HashMap<>();
        for (CompanyDetails companyDetails : loadedUser.getCompanies()) {
            Company company = companyDetails.getCompany();
            companyDetailsMap.put(company, companyDetails);
        }

        return companyDetailsMap;
    }
}