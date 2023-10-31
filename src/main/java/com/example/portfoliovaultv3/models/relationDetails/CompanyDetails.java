package com.example.portfoliovaultv3.models.relationDetails;

import com.example.portfoliovaultv3.models.Company;
import com.example.portfoliovaultv3.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

import java.util.Date;

@RelationshipEntity(type = "HAS_WORKED")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetails {
    @Id @GeneratedValue
    private Long id;
    @StartNode
    private User user;
    @EndNode
    private Company company;

    private String jobTitle;
    private String startDay;
    private String endDay;

    public CompanyDetails(String jobTitle, String startDay, String endDay) {
        this.jobTitle=jobTitle;
        this.startDay=startDay;
        this.endDay=endDay;
    }

}
