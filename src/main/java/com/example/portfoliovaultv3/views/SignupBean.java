package com.example.portfoliovaultv3.views;

import com.example.portfoliovaultv3.exceptions.EmailAlreadyTakenException;
import com.example.portfoliovaultv3.models.User;
import com.example.portfoliovaultv3.repositories.UserRepository;
import com.example.portfoliovaultv3.services.UserServiceEJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

import java.util.logging.Logger;

@Named
@ViewScoped
public class SignupBean implements Serializable {
    private static final long serialVersionUID = 2729758432756108274L;
    Logger logger = Logger.getLogger(SignupBean.class.getName());
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Inject the UserService bean
    @Inject
    private UserServiceEJB userService;


    private UserRepository userRepository;

    public String signup() {
        try{
            userService.registerUser(firstName, lastName, email, password);
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
           externalContext.redirect(externalContext.getRequestContextPath() + "/home.xhtml");
        }catch (EmailAlreadyTakenException exception){
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage("Email Already taken", "Please choose another email");
            context.addMessage(null, message);
        }
        catch(IOException e){
            logger.warning(e.getMessage());
        }catch (Exception exception){
            logger.warning(exception.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
//            FacesMessage message = new FacesMessage("Something went wrong", "");
            FacesMessage message = new FacesMessage(exception.getMessage(), "");
            context.addMessage(null, message);
        }
        return null; // Specify the navigation outcome to a success page
//        User user=new User();
//        user.setFirstname("najwa");
//        userRepository.save(user);
//        return null;
    }

    public UserServiceEJB getUserService() {
        return userService;
    }

    public void setUserService(UserServiceEJB userService) {
        this.userService = userService;
    }
}
