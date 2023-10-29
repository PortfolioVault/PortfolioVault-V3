package com.example.portfoliovaultv3.repositories;

import com.example.portfoliovaultv3.models.Company;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends Neo4jRepository<Company,Long> {
}
