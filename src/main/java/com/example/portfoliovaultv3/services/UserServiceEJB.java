package com.example.portfoliovaultv3.services;

import com.example.portfoliovaultv3.exceptions.EmailAlreadyTakenException;
import com.example.portfoliovaultv3.models.Neo4jConnectionManager;
import com.example.portfoliovaultv3.models.User;
import com.example.portfoliovaultv3.repositories.UserRepository;
import com.example.portfoliovaultv3.session.UserSession;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Stateless
@Service
public class UserServiceEJB {


    static Session session = Neo4jConnectionManager.getNeo4jSession();

    @Inject
    private UserSession userSession;

    @Autowired
    public UserRepository userRepository;

    public void registerUser(String firstName, String lastName, String email, String password) throws EmailAlreadyTakenException{
        User user = findUserByEmail(email);
        if(user != null){
            //check if a user with same email exists
            throw  new EmailAlreadyTakenException();
        }else{
            try {
                User newUser = new User();
                newUser.setFirstname(firstName);
                newUser.setEmail(email);
                newUser.setLastname(lastName);
                newUser.setPassword(password);
//                userRepository.save(newUser);
                userSession.setEmail(email);
                //Enregistrer l'utilisateur dans la base de donnée Neo4j
                session.save(newUser);
            } finally {
                if (session != null) {
                    session.clear(); // Optionnel : pour vider le contexte de la session
                }
            }
        }

    }

    public static User findUserByEmail(String email){
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("email", email);
            String cypher = "MATCH (p:User {email: $email})-[r]-(related) RETURN p, r, related";
            Iterable<User> result = session.query(User.class, cypher, parameters);
            if (result.iterator().hasNext() ){
                return result.iterator().hasNext() ? result.iterator().next() :null ;
            }
            String cypher1 = "MATCH (p:User) WHERE p.email = $email RETURN p";
            Iterable<User> result1 = session.query(User.class, cypher1, parameters);
                return result1.iterator().hasNext() ? result1.iterator().next() :null ;

        } finally {
            if (session != null) {
                session.clear(); // Optionnel : pour vider le contexte de la session
            }
        }
    }

    public boolean login(String email,String password){
        User user = findUserByEmail(email);
        boolean loginResult = false;
        if(user != null && Objects.equals(user.getPassword(), password)){
            loginResult = true;
            userSession.setEmail(email);
        }
        return loginResult;
    }

    public void savePersonalInfos(String email,String phoneNumber,String age, String professionalTitle,String address) {
        User user = findUserByEmail(email);
        if (user != null) {
            //check if the user exists
            user.setAddress(address);
            user.setProfessionalTitle(professionalTitle);
            user.setAge(age);
            user.setPhoneNumber(phoneNumber);
            session.save(user);
        }
    }
}
