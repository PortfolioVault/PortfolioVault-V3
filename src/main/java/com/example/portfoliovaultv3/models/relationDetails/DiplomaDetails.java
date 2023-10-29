package com.example.portfoliovaultv3.models.relationDetails;

import com.example.portfoliovaultv3.models.Diplome;
import com.example.portfoliovaultv3.models.User;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "HAS_DIPLOMA")
public class DiplomaDetails {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private User user;
    @EndNode
    private Diplome diplome;

    private String degreeType;
    private int yearObtained;

}
