package com.example.portfoliovaultv3.models;

import com.example.portfoliovaultv3.models.relationDetails.DiplomaDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Diplome {
    @Id
    @GeneratedValue
    private String id;
    private String university;
    private String fieldOfStudy;

    @Relationship(type = "HAS_DIPLOMA", direction = Relationship.Direction.INCOMING)
    private List<DiplomaDetails> Students;


}
