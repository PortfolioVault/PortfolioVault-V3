package com.example.portfoliovaultv3.services;

import com.example.portfoliovaultv3.repositories.CompanyRepository;
import jakarta.ejb.Stateless;



@Stateless
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


//    private static final String COLLECTION_NAME = "portfolios";
//    private final MongoCollection<Document> collection = DatabaseUtils.getCollection(COLLECTION_NAME);
//
//    private Logger logger= Logger.getLogger(String.valueOf(CompanyImpl.class));



//    public void addExperience(String email, Experience experience) {
//        Document lastExperience = experience.toDocument();
//        Bson filter = Filters.eq("email", email);
//        Bson update = Updates.addToSet("experiences", lastExperience);
//        UpdateOptions options = new UpdateOptions().upsert(true);
//
//        // Update the user document based on the userId
//        collection.updateOne(filter, update, options);
//    }

//    public LinkedList<Experience> getExperiences(String email) {
//        logger.info("hola");
//        Document user = collection.find(eq("email", email)).first();
//        if(user == null){
//            return null;
//        }
////        List<Document> experiencesDocuments = user.getList("experiences", Document.class);
////        LinkedList<Experience> experiences = new LinkedList<>();
////        for(Document document:experiencesDocuments){
////            experiences.add(Experience.documentToExperience(document));
////        }
//
//        ArrayList<Document> experiencesDoc = (ArrayList<Document>) user.get("experiences");
//        LinkedList<Experience> experiences = new LinkedList<>();
//        if(experiencesDoc!=null && experiencesDoc.size() > 0) {
//            for (Document document : experiencesDoc) {
//                experiences.add(Experience.documentToExperience(document));
//            }
//        }
//        return experiences;
//
//    }
}