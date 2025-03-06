package com.souza.charles.BookstoreAPI.utils;

public class BookstoreMessages {

    private BookstoreMessages() {
        throw new IllegalStateException("Utility class");
    }

    // Book Messages
    public static final String BOOK_NOT_FOUND = "Book not found";
    public static final String BOOK_DELETE_SUCCESSFULLY = "Book delete successfully!";

    // Publisher Messages
    public static final String PUBLISHER_NOT_FOUND = "Publisher not found";

    // Author Messages
    public static final String AT_LEAST_ONE_AUTHOR_REQUIRED = "At least one author is required";
}