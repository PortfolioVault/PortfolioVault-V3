package com.example.portfoliovaultv3.services;

import com.example.portfoliovaultv2.exceptions.EmailAlreadyTakenException;
import com.example.portfoliovaultv2.models.User;
import com.example.portfoliovaultv2.services.utils.DatabaseUtils;
import com.example.portfoliovaultv2.session.UserSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.bson.Document;

import java.util.Objects;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

@Stateless
public class UserServiceEJB {
    private static final String COLLECTION_NAME = "portfolios";
    private final MongoCollection<Document> collection = DatabaseUtils.getCollection(COLLECTION_NAME);

    private Logger logger= Logger.getLogger(String.valueOf(UserServiceEJB.class));

    @Inject
    private UserSession userSession;
    public void registerUser(String firstName, String lastName, String email, String password) throws EmailAlreadyTakenException{
        User user = findUserByEmail(email);
        if(user != null){
            //check if a user with same email exists
            throw  new EmailAlreadyTakenException();
        }else{

        // Create a document with user data and insert it into the collection
        Document userDocument = new Document()
                .append("firstname", firstName)
                .append("lastname", lastName)
                .append("email", email)
                .append("password", password);

        collection.insertOne(userDocument);
        userSession.setEmail(email);
        }
    }

    public User findUserByEmail(String email){
        Document user =  collection.find(eq("email",email)).first();
        return User.documentToUser(user);
    }

    public boolean login(String email,String password){
        logger.info("hola");
        User user = findUserByEmail(email);
        boolean loginResult = false;
        if(user != null && Objects.equals(user.getPassword(), password)){
            loginResult = true;
            userSession.setEmail(email);
        }
        return loginResult;
    }

    public void savePersonalInfos(String email,String phoneNumber,String age, String professionalTitle,String address){
        Document document = new Document("$set",new Document()
                .append("professionalTitle",professionalTitle)
                .append("phoneNumber",phoneNumber)
                .append("address",address)
                .append("age",age));
        collection.updateOne(Filters.eq("email",email),document);
    }
}
