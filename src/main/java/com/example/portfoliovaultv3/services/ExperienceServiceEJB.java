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
    static Session session = Neo4jConnectionManager.getNeo4jSession();

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

    public static Company findCompanyByName(String name){
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", name);
            String cypher = "MATCH (d:Company {name: $name })--(relatedNode) RETURN d, relatedNode";
            Iterable<Company> result = session.query(Company.class, cypher, parameters);
            if (result.iterator().hasNext() ){
                return result.iterator().hasNext() ? result.iterator().next() :null ;
            }
            String cypher1 = "MATCH (p:Company) WHERE p.name = $name RETURN p";
            Iterable<Company> result1 = session.query(Company.class, cypher1, parameters);
            return result1.iterator().hasNext() ? result1.iterator().next() :null ;

        } finally {
            if (session != null) {
                session.clear(); // Optionnel : pour vider le contexte de la session
            }
        }
    }

    public List<Map<String, Object>> getDetailsForAllCompanies(String email) {
        User user = UserServiceEJB.findUserByEmail(email);
        if (user == null) {
            return null;
        }

        Long userId = user.getId();
        User loadedUser = session.load(User.class, userId, 1); // Charger l'utilisateur avec ses relations

        List<Map<String, Object>> companyDetailsList = new ArrayList<>();
        for (CompanyDetails companyDetails : loadedUser.getCompanies()) {
            Company company = companyDetails.getCompany();

            Map<String, Object> entry = new HashMap<>();
            entry.put("company", company);
            entry.put("companyDetails", companyDetails);

            companyDetailsList.add(entry);
        }

        return companyDetailsList;
    }


//    public Map<Company, CompanyDetails> getDetailsForAllCompanies(String email) {
//        User user = UserServiceEJB.findUserByEmail(email);
//        if (user == null) {
//            return null;
//        }
//
//        Long userId = user.getId();
//        User loadedUser = session.load(User.class, userId,1); // Charger l'utilisateur avec ses relations
//
//            Map<Company, CompanyDetails> companyDetailsMap = new HashMap<>();
//            for (CompanyDetails companyDetails : loadedUser.getCompanies()) {
//                Company company = companyDetails.getCompany();
//                companyDetailsMap.put(company, companyDetails);
//            }
//            return companyDetailsMap;
//
//    }
}