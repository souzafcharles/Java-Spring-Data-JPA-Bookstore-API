package com.souza.charles.BookstoreAPI.dtos;

import com.souza.charles.BookstoreAPI.models.Author;
import com.souza.charles.BookstoreAPI.models.Book;
import com.souza.charles.BookstoreAPI.models.LegalDeposit;
import com.souza.charles.BookstoreAPI.models.Publisher;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import java.util.Optional;
public record BookResponseDTO(UUID id,
                              String title,
                              String isbn,
                              Integer pages,
                              String language,
                              Optional<UUID> publisherId,
                              Set<UUID> authorIds,
                              Optional<String> depositCode) implements Serializable {

    public BookResponseDTO(Book book) {
        this(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPages(),
                book.getLanguage(),
                Optional.ofNullable(book.getPublisher()).map(Publisher::getId),
                book.getAuthors() != null ? book.getAuthors().stream().map(Author::getId).collect(Collectors.toSet()) : Set.of(),
                Optional.ofNullable(book.getLegalDeposit()).map(LegalDeposit::getDepositCode)
        );
    }
}