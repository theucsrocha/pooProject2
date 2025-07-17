package br.com.matheus.bookstore.dao;

import br.com.matheus.bookstore.db.ConnectionFactory;
import br.com.matheus.bookstore.model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookDAO {

    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setPublishedYear(rs.getInt("published_year"));
                book.setCopiesAvailable(rs.getInt("copies_available"));

                books.add(book);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar os livros: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }
}
