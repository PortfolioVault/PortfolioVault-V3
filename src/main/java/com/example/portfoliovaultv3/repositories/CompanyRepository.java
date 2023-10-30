package com.example.portfoliovaultv3.repositories;

import com.example.portfoliovaultv3.models.Company;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends Neo4jRepository<Company,Long> {
    @Query("MATCH (u:User)-[:HAS_EMPLOYEE]->(c:Company) WHERE u.email = $email RETURN c")
    List<Company> findCompaniesByUserEmail(@Param("email") String userEmail);
}
