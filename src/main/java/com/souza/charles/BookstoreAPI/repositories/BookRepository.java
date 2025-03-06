package com.souza.charles.BookstoreAPI.repositories;

import com.souza.charles.BookstoreAPI.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
}