package com.example.portfoliovaultv3.repositories;

import com.example.portfoliovaultv3.models.Diplome;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiplomeRepository extends Neo4jRepository<Diplome,Long> {
}
