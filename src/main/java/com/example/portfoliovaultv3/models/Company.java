package com.example.portfoliovaultv3.models;

import com.example.portfoliovaultv3.models.relationDetails.EmploymentDetails;
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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id @GeneratedValue
    private String id;
    private String name;

    @Relationship(type = "HAS_EMPLOYEE", direction = Relationship.Direction.INCOMING)
    private List<EmploymentDetails> employees;
}
