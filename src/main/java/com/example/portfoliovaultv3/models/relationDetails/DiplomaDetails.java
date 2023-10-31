package com.example.portfoliovaultv3.models.relationDetails;

import com.example.portfoliovaultv3.models.Diplome;
import com.example.portfoliovaultv3.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "HAS_DIPLOMA")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DiplomaDetails {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private User user;
    @EndNode
    private Diplome diplome;

    private String degreeType;
    private String yearOfObtention;

    public DiplomaDetails(String degreeType, String yearOfObtention ) {
        this.degreeType=degreeType;
        this.yearOfObtention=yearOfObtention;
    }
}
