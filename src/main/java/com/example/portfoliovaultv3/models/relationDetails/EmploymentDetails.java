package com.example.portfoliovaultv3.models.relationDetails;

import com.example.portfoliovaultv3.models.Company;
import com.example.portfoliovaultv3.models.User;
import org.neo4j.ogm.annotation.*;

import java.util.Date;

@RelationshipEntity(type = "HAS_EMPLOYEE")
public class EmploymentDetails {
    @Id @GeneratedValue
    private Long id;
    @StartNode
    private User user;
    @EndNode
    private Company company;

    private String jobTitle;
    private Date startDay;
    private Date endDay;
}
