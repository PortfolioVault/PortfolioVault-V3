package com.example.portfoliovaultv3.views;

import com.example.portfoliovaultv3.models.Company;
import com.example.portfoliovaultv3.models.Diplome;
import com.example.portfoliovaultv3.models.relationDetails.CompanyDetails;
import com.example.portfoliovaultv3.models.relationDetails.DiplomaDetails;
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
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
@Getter @Setter
public class ExperienceBean implements Serializable {

    @Inject
    private UserServiceEJB userServiceEJB;
    @Inject
    private ExperienceServiceEJB experienceServiceEJB;
    @Inject
    private UserSession userSession;

    private Company experience;
    private CompanyDetails companyDetails;

    private String name;
    private String jobTitle;
    private String startDay;
    private String endDay;

    public String ajouterExperience() {
        experience=new Company(name);
        companyDetails=new CompanyDetails(jobTitle,startDay,endDay);

        //ajouter les données dans la database
        experienceServiceEJB.addExperience(userSession.getEmail(), experience,companyDetails);

        //redirection
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        try{
            externalContext.redirect(externalContext.getRequestContextPath() + "/home.xhtml");
        }catch(IOException e){
            e.printStackTrace();
        }
        jobTitle = "";
        name ="";
        startDay="";
        endDay="";
        return null;

    }

    public List<Company> getAllExperiences(){
        return experienceServiceEJB.getExperiences(userSession.getEmail());
    }
}
