package br.edu.ifba.inf008.plugins.common.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.inf008.plugins.common.db.ConnectionFactory;
import br.edu.ifba.inf008.plugins.common.model.Loan;

public class LoanDAO {

    /**
     * Salva um novo empréstimo no banco de dados.
     * @param loan O objeto Loan a ser salvo.
     */
    public void save(Loan loan) {
        String sql = "INSERT INTO loans (user_id, book_id, loan_date) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, loan.getUserId());
            stmt.setInt(2, loan.getBookId());
            stmt.setDate(3, Date.valueOf(loan.getLoanDate()));

            stmt.executeUpdate();
            System.out.println("Loan saved successfully!");

        } catch (SQLException e) {
            System.err.println("Error while saving loan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Busca todos os empréstimos, juntando dados de usuários e livros para exibição.
     * @return Uma lista de objetos Loan preenchidos.
     */
    public List<Loan> findAll() {
        // SQL com JOIN para buscar nomes e títulos em vez de apenas IDs.
        String sql = "SELECT l.loan_id, l.user_id, l.book_id, l.loan_date, l.return_date, " +
                     "u.name as user_name, b.title as book_title " +
                     "FROM loans l " +
                     "JOIN users u ON l.user_id = u.user_id " +
                     "JOIN books b ON l.book_id = b.book_id";
        
        List<Loan> loans = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Loan loan = new Loan();
                loan.setId(rs.getInt("loan_id"));
                loan.setUserId(rs.getInt("user_id"));
                loan.setBookId(rs.getInt("book_id"));
                
                Date loanDate = rs.getDate("loan_date");
                if(loanDate != null) loan.setLoanDate(loanDate.toLocalDate());
                
                Date returnDate = rs.getDate("return_date");
                if(returnDate != null) loan.setReturnDate(returnDate.toLocalDate());
                
                // Preenche os atributos extras que vieram dos JOINs
                loan.setUserName(rs.getString("user_name"));
                loan.setBookTitle(rs.getString("book_title"));

                loans.add(loan);
            }

        } catch (SQLException e) {
            System.err.println("Error finding all loans: " + e.getMessage());
            e.printStackTrace();
        }
        return loans;
    }

    /**
     * Registra a devolução de um livro, definindo a data de devolução como a data atual.
     * @param loanId O ID do empréstimo a ser atualizado.
     */
    public void markAsReturned(int loanId) {
        String sql = "UPDATE loans SET return_date = ? WHERE loan_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define a data de devolução como a data de hoje.
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setInt(2, loanId);

            stmt.executeUpdate();
            System.out.println("Loan marked as returned successfully!");

        } catch (SQLException e) {
            System.err.println("Error while updating loan return date: " + e.getMessage());
            e.printStackTrace();
        }
    }
}