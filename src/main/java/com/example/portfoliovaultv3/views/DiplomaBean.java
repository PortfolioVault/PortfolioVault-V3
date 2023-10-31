package com.example.portfoliovaultv3.views;

import com.example.portfoliovaultv3.models.Company;
import com.example.portfoliovaultv3.models.Diplome;
import com.example.portfoliovaultv3.models.relationDetails.CompanyDetails;
import com.example.portfoliovaultv3.models.relationDetails.DiplomaDetails;
import com.example.portfoliovaultv3.services.DiplomeServiceEJB;
import com.example.portfoliovaultv3.services.ExperienceServiceEJB;
import com.example.portfoliovaultv3.services.UserServiceEJB;
import com.example.portfoliovaultv3.session.UserSession;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@Named
@ViewScoped
@Getter
@Setter
public class DiplomaBean implements Serializable {
    @Inject
    private UserServiceEJB userServiceEJB;
    @Inject
    private DiplomeServiceEJB diplomeServiceEJB;
    @Inject
    private UserSession userSession;

    private Diplome diplome;
    private DiplomaDetails diplomaDetails;


    private String degreeType;
    private String yearOfObtention;
    private String university;
    private String fieldOfStudy;

    public String ajouterDiplome() {
        diplome=new Diplome(university,fieldOfStudy);
        diplomaDetails=new DiplomaDetails(degreeType,yearOfObtention);

        //ajouter les données dans la database
        diplomeServiceEJB.addDiploma(userSession.getEmail(), diplome,diplomaDetails);

        //redirection
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        try{
            externalContext.redirect(externalContext.getRequestContextPath() + "/home.xhtml");
        }catch(IOException e){
            e.printStackTrace();
        }
        yearOfObtention="";
        degreeType="";
        fieldOfStudy="";
        university="";

        return null;

    }

    public Map<Diplome, DiplomaDetails> getAllDiplomesDetails(){
        return diplomeServiceEJB.getDetailsForAllDiplomes(userSession.getEmail());
    }
}
