package com.example.portfoliovaultv3.views;

import com.example.portfoliovaultv3.models.Company;
import com.example.portfoliovaultv3.models.Diplome;
import com.example.portfoliovaultv3.models.User;
import com.example.portfoliovaultv3.models.relationDetails.DiplomaDetails;
import com.example.portfoliovaultv3.services.DiplomeServiceEJB;
import com.example.portfoliovaultv3.services.ExperienceServiceEJB;
import com.example.portfoliovaultv3.services.UserServiceEJB;
import com.example.portfoliovaultv3.session.UserSession;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class ProfilPageBean implements Serializable {
    @Inject
    private UserServiceEJB userServiceEJB;
    @Inject
    private ExperienceServiceEJB experienceServiceEJB;
    @Inject
    private DiplomeServiceEJB diplomeServiceEJB ;
    @Inject
    private UserSession userSession;


    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String age;
    private String professionalTitle;
    private LinkedList<Company> experiences = new LinkedList<>();
    private LinkedList<Diplome> educations = new LinkedList<>();


    public void getUser() {
        // Initialize properties using values from UserSessionBean
        User user = userServiceEJB.findUserByEmail(userSession.getEmail());
        this.firstName = user.getFirstname();
        this.lastName = user.getLastname();
        this.email = userSession.getEmail();
        this.address = user.getAddress() != null ? user.getAddress() : "";
        this.age = user.getAge() != null ? user.getAge() : "";
        this.professionalTitle = user.getProfessionalTitle() != null ? user.getProfessionalTitle() : "";
        this.phoneNumber = user.getPhoneNumber() != null ? user.getPhoneNumber() : "";
//        this.experiences = experienceServiceEJB.getExperiences(userSession.getEmail());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getProfessionalTitle() {
        return professionalTitle;
    }

    public void setProfessionalTitle(String professionalTitle) {
        this.professionalTitle = professionalTitle;
    }

    public List<Company> getExperiences() {
        return experienceServiceEJB.getExperiences(userSession.getEmail());
    }

    public void setExperiences(LinkedList<Company> experiences) {
        this.experiences = experiences;
    }

    public List<Map<String, Object>> getEducations() {
        return diplomeServiceEJB.getDetailsForAllDiplomes(userSession.getEmail());
    }

    public void setEducations(LinkedList<Diplome> educations) {
        this.educations = educations;
    }
}