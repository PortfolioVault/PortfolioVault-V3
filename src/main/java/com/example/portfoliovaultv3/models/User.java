package com.example.portfoliovaultv3.models;


import com.example.portfoliovaultv3.models.relationDetails.DiplomaDetails;
import com.example.portfoliovaultv3.models.relationDetails.CompanyDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class User {
    @Id @GeneratedValue
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String professionalTitle;
    private String phoneNumber;
    private String address;
    private String age;

    @Relationship(type = "HAS_DIPLOMA")
    private List<DiplomaDetails> diplomes=new ArrayList<>();

    @Relationship(type = "HAS_WORKED")
    private List<CompanyDetails> companies=new ArrayList<>();

}
