package com.souza.charles.BookstoreAPI.repositories;

import com.souza.charles.BookstoreAPI.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
}