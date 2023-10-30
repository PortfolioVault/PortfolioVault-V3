package com.example.portfoliovaultv3.services;


import com.example.portfoliovaultv3.models.Company;
import com.example.portfoliovaultv3.models.Diplome;
import com.example.portfoliovaultv3.models.Neo4jConnectionManager;
import com.example.portfoliovaultv3.models.User;
import com.example.portfoliovaultv3.models.relationDetails.CompanyDetails;
import com.example.portfoliovaultv3.models.relationDetails.DiplomaDetails;
import jakarta.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.session.Session;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;


@Stateless
@Slf4j
public class ExperienceServiceEJB {
    private Logger logger = Logger.getLogger(String.valueOf(ExperienceServiceEJB.class));
    Session session = Neo4jConnectionManager.getNeo4jSession();
    List<CompanyDetails> test=new ArrayList<>() ;

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

        //Importer le noeud avec ses relation
        Long userId = user.getId();
        User loadedUser = session.load(User.class, userId);

        LinkedList<Company> experiences = new LinkedList<>();
        for (CompanyDetails companyDetails : loadedUser.getCompanies()) {
            experiences.add(companyDetails.getCompany());
        }
        return experiences;
    }
 public static void main(String[] args) {
        ExperienceServiceEJB experienceServiceEJB=new ExperienceServiceEJB();

     System.out.println(experienceServiceEJB.getExperiences("leghrisnajwa@gmail.com"));
     System.out.println(experienceServiceEJB.test);
}
}