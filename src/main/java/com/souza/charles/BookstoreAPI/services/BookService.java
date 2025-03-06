package com.souza.charles.BookstoreAPI.services;

import com.souza.charles.BookstoreAPI.dtos.BookRequestDTO;
import com.souza.charles.BookstoreAPI.dtos.BookResponseDTO;
import com.souza.charles.BookstoreAPI.models.Author;
import com.souza.charles.BookstoreAPI.models.Book;
import com.souza.charles.BookstoreAPI.models.LegalDeposit;
import com.souza.charles.BookstoreAPI.models.Publisher;
import com.souza.charles.BookstoreAPI.repositories.AuthorRepository;
import com.souza.charles.BookstoreAPI.repositories.BookRepository;
import com.souza.charles.BookstoreAPI.repositories.LegalDepositRepository;
import com.souza.charles.BookstoreAPI.repositories.PublisherRepository;
import com.souza.charles.BookstoreAPI.utils.BookstoreMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private LegalDepositRepository legalDepositRepository;

    @Transactional
    public BookResponseDTO create(@Valid BookRequestDTO dto) {
        Publisher publisher = publisherRepository.findById(dto.publisherId()).orElseThrow(() -> new IllegalArgumentException(BookstoreMessages.PUBLISHER_NOT_FOUND));

        Set<Author> authors = new HashSet<>(authorRepository.findAllById(dto.authorIds()));
        if (authors.isEmpty()) {
            throw new IllegalArgumentException(BookstoreMessages.AT_LEAST_ONE_AUTHOR_REQUIRED);
        }

        Book book = new Book(dto, publisher, authors, null);
        book = bookRepository.save(book);

        LegalDeposit legalDeposit = new LegalDeposit();
        legalDeposit.setDepositCode(dto.depositCodeRegistration());
        legalDeposit.setCountry(dto.country().toUpperCase(Locale.ROOT));
        legalDeposit.setBook(book);
        legalDeposit = legalDepositRepository.save(legalDeposit);

        book.setLegalDeposit(legalDeposit);
        bookRepository.save(book);

        return new BookResponseDTO(book);
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> readAll() {
        return bookRepository.findAll().stream().map(BookResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public BookResponseDTO readOne(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(BookstoreMessages.BOOK_NOT_FOUND));
        return new BookResponseDTO(book);
    }

    @Transactional
    public BookResponseDTO update(UUID id, @Valid BookRequestDTO dto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(BookstoreMessages.BOOK_NOT_FOUND));

        Publisher publisher = publisherRepository.findById(dto.publisherId()).orElseThrow(() -> new IllegalArgumentException(BookstoreMessages.PUBLISHER_NOT_FOUND));

        Set<Author> authors = new HashSet<>(authorRepository.findAllById(dto.authorIds()));
        if (authors.isEmpty()) {
            throw new IllegalArgumentException(BookstoreMessages.AT_LEAST_ONE_AUTHOR_REQUIRED);
        }

        book.setTitle(dto.title());
        book.setIsbn(dto.isbn());
        book.setPages(dto.pages());
        book.setLanguage(dto.language());
        book.setPublisher(publisher);
        book.setAuthors(authors);

        LegalDeposit legalDeposit = book.getLegalDeposit();
        if (legalDeposit == null) {
            legalDeposit = new LegalDeposit();
            legalDeposit.setBook(book);
        }
        legalDeposit.setDepositCode(dto.depositCodeRegistration());
        legalDeposit.setCountry(dto.country().toUpperCase(Locale.ROOT));
        legalDepositRepository.save(legalDeposit);

        book.setLegalDeposit(legalDeposit);
        book = bookRepository.save(book);

        return new BookResponseDTO(book);
    }

    @Transactional
    public void delete(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException(BookstoreMessages.BOOK_NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }
}