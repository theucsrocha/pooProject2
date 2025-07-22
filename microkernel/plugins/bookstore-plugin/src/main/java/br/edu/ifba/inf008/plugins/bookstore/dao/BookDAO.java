package br.edu.ifba.inf008.plugins.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.inf008.plugins.bookstore.db.ConnectionFactory;
import br.edu.ifba.inf008.plugins.bookstore.model.Book;


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
