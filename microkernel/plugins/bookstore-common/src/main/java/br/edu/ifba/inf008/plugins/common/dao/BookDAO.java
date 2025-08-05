package br.edu.ifba.inf008.plugins.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.inf008.plugins.common.db.ConnectionFactory;
import br.edu.ifba.inf008.plugins.common.model.Book;


public class BookDAO {

    /**
     * Retrieves all books from the database.
     * @return A list of Book objects.
     */
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
            System.err.println("Error while fetching books: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    /**
     * Saves a new book to the database.
     * @param book The Book object to be saved.
     */
    public void save(Book book){
        String sql = "INSERT INTO books (title,author,isbn,published_year,copies_available) VALUES (?,?,?,?,?)";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1,book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setString(3, book.getIsbn());
                stmt.setInt(4, book.getPublishedYear());
                stmt.setInt(5, book.getCopiesAvailable());
                stmt.executeUpdate();
                System.out.println("Book saved with successfully!");
            }  
        
            catch (SQLException e) {
                System.err.println("Error while saving book: " + e.getMessage());
                e.printStackTrace();
    }
    }

    /**
     * Updates an existing book's information.
     * @param book The Book object with updated data.
     */
    public void update(Book book){
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, published_year = ?, copies_available = ? WHERE book_id = ?";
          try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getIsbn());
        stmt.setInt(4, book.getPublishedYear());
        stmt.setInt(5, book.getCopiesAvailable());
        stmt.setInt(6, book.getId());
        stmt.executeUpdate();

        System.out.println("Book updated successfully!");

    } catch (SQLException e) {
        System.err.println("Error while updating book: " + e.getMessage());
        e.printStackTrace();
    }
    }

    /**
     * Deletes a book from the database by its ID.
     * @param id The ID of the book to be deleted.
     */
    public void delete(int id) {
    String sql = "DELETE FROM books WHERE book_id = ?";

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        stmt.executeUpdate();

        System.out.println("Book deleted successfully!");

    } catch (SQLException e) {
        System.err.println("Error while deleting book: " + e.getMessage());
        e.printStackTrace();
    }
}

    /**
     * Decrements the available copies of a book by one.
     * @param bookId The ID of the book to be updated.
     */
    public void decrementCopies(int bookId) {
        String sql = "UPDATE books SET copies_available = copies_available - 1 WHERE book_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while decrementing book copies: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Increments the available copies of a book by one.
     * @param bookId The ID of the book to be updated.
     */
    public void incrementCopies(int bookId) {
        String sql = "UPDATE books SET copies_available = copies_available + 1 WHERE book_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while incrementing book copies: " + e.getMessage());
            e.printStackTrace();
        }
    }
}