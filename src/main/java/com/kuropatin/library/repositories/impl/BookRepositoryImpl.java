package com.kuropatin.library.repositories.impl;

import com.kuropatin.library.mappers.BookMapper;
import com.kuropatin.library.models.entities.Book;
import com.kuropatin.library.repositories.interfaces.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final JdbcTemplate JDBC_TEMPLATE;

    @Autowired
    public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.JDBC_TEMPLATE = jdbcTemplate;
    }

    @Override
    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM books ORDER BY book_name";
        return JDBC_TEMPLATE.query(sql, new BookMapper());
    }

    @Override
    public Book getBookByBookId(long id) {
        String sql = "SELECT * FROM books WHERE id=?";
        return JDBC_TEMPLATE.query(sql, new BookMapper(), id).stream().findAny().orElse(null);
    }

    @Override
    public List<Book> getBooksByAuthorId(long authorId) {
        String sql = "SELECT books.id, books.book_name, books.year_of_publication, books.publisher " +
                     "FROM books, authors, book_author " +
                     "WHERE books.id = book_author.book_id AND authors.id = book_author.author_id AND book_author.author_id = ? " +
                     "ORDER BY year_of_publication";
        return JDBC_TEMPLATE.query(sql, new BookMapper(), authorId);
    }

    @Override
    public Book getBookByName(String name) {
        String sql = "SELECT * FROM books WHERE book_name=?";
        return JDBC_TEMPLATE.query(sql, new BookMapper(), name).stream().findAny().orElse(null);
    }

    @Override
    public void createBook(Book book) {
        String sql = "INSERT INTO books VALUES(default, ?, ?, ?)";
        JDBC_TEMPLATE.update(sql, book.getName(), book.getYearOfPublication(), book.getPublisher());
    }

    @Override
    public void updateBook(long id, Book book) {
        String sql = "UPDATE books SET book_name=?, year_of_publication=?, publisher=? WHERE id=?";
        JDBC_TEMPLATE.update(sql, book.getName(), book.getYearOfPublication(), book.getPublisher(), id);
    }

    @Override
    public void deleteBook(long id) {
        String sql = "DELETE FROM books WHERE id=?";
        JDBC_TEMPLATE.update(sql, id);
    }
}