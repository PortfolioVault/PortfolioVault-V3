package com.example.portfoliovaultv3.services;

import com.example.portfoliovaultv3.exceptions.EmailAlreadyTakenException;
import com.example.portfoliovaultv3.models.Neo4jConnectionManager;
import com.example.portfoliovaultv3.models.User;
import com.example.portfoliovaultv3.repositories.UserRepository;
import com.example.portfoliovaultv3.session.UserSession;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Stateless
@Service
public class UserServiceEJB {

    @Inject
    private Neo4jConnectionManager connectionManager;

    Driver neo4jDriver = connectionManager.getNeo4jDriver();

    @Inject
    private UserSession userSession;

    public UserRepository userRepository;

    public void registerUser(String firstName, String lastName, String email, String password) throws EmailAlreadyTakenException{
        User user = userRepository.findUserByEmail(email);
        if(user != null){
            //check if a user with same email exists
            throw  new EmailAlreadyTakenException();
        }else{

            try (Session session = neo4jDriver.session()) {
                // Create a node with user data and insert it into the collection
                User newUser = new User();
                newUser.setFirstname(firstName);
                newUser.setEmail(email);
                newUser.setLastname(lastName);
                newUser.setPassword(password);
                userRepository.save(newUser);
                userSession.setEmail(email);
            }
        }
//        User user=new User();
//        user.setFirstname("najwa");
//        userRepository.save(user);
    }


    public boolean login(String email,String password){
        User user = userRepository.findUserByEmail(email);
        boolean loginResult = false;
        if(user != null && Objects.equals(user.getPassword(), password)){
            loginResult = true;
            userSession.setEmail(email);
        }
        return loginResult;
    }

    public void savePersonalInfos(String email,String phoneNumber,String age, String professionalTitle,String address) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            //check if the user exists
            user.setAddress(address);
            user.setProfessionalTitle(professionalTitle);
            user.setAge(age);
            user.setPhoneNumber(phoneNumber);
            userRepository.save(user);
        }
    }
}
