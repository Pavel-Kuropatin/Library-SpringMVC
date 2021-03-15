package com.kuropatin.library.repository.interfaces;

import com.kuropatin.library.models.Book;
import java.util.List;

public interface BookRepository {

    List<Book> getAllBooks();

    Book getBookByBookId(long id);

    List<Book> getBooksByAuthorId(long authorId);

    Book getBookByName(String name);

    void createBook(Book book);

    void updateBook(long id, Book book);

    void deleteBook(long id);
}