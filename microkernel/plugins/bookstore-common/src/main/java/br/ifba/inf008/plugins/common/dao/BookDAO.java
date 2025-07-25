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

    public void update(Book book){
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, published_year = ?, copies_available = ? WHERE book_id = ?";
          try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        // 3. Preenchendo os placeholders (?) com os novos dados do objeto Book.
        // A ORDEM AQUI DEVE SER EXATAMENTE A MESMA DA SQL.
        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getIsbn());
        stmt.setInt(4, book.getPublishedYear());
        stmt.setInt(5, book.getCopiesAvailable());
        
        // 6. O placeholder MAIS IMPORTANTE: o ID para a cláusula WHERE.
        stmt.setInt(6, book.getId());

        // 7. Executa o comando de atualização no banco.
        stmt.executeUpdate();

        System.out.println("Book updated successfully!");

    } catch (SQLException e) {
        System.err.println("Error while updating book: " + e.getMessage());
        e.printStackTrace();
    }
    }
    public void delete(int id) {
    // 1. O comando SQL para deletar um registro com base no seu ID.
    // A cláusula WHERE é ESSENCIAL para não apagar a tabela inteira.
    String sql = "DELETE FROM books WHERE book_id = ?";

    // 2. Usamos o 'try-with-resources' para a conexão e o statement.
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        // 3. Substituímos o placeholder '?' pelo ID que recebemos.
        stmt.setInt(1, id);

        // 4. Executa o comando de deleção no banco.
        stmt.executeUpdate();

        System.out.println("Book deleted successfully!");

    } catch (SQLException e) {
        System.err.println("Error while deleting book: " + e.getMessage());
        e.printStackTrace();
    }
}
}
