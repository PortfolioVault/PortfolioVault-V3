package com.example.portfoliovaultv3.repositories;

import com.example.portfoliovaultv3.models.User;
import jakarta.inject.Named;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<User,Long> {
    User findUserByEmail(String email);
}
