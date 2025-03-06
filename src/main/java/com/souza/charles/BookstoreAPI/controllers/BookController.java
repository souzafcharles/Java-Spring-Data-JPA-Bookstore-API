package com.souza.charles.BookstoreAPI.controllers;

import com.souza.charles.BookstoreAPI.dtos.BookRequestDTO;
import com.souza.charles.BookstoreAPI.dtos.BookResponseDTO;
import com.souza.charles.BookstoreAPI.services.BookService;
import com.souza.charles.BookstoreAPI.utils.BookstoreMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookstore/books")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> create(@RequestBody BookRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> readAll() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.readAll());
    }

    @GetMapping(value = "/{id}")
    public BookResponseDTO readOne(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.readOne(id)).getBody();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookResponseDTO> update(@PathVariable UUID id, @RequestBody BookRequestDTO dto) {
        BookResponseDTO responseDTO = bookService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(BookstoreMessages.BOOK_DELETE_SUCCESSFULLY);
    }
}