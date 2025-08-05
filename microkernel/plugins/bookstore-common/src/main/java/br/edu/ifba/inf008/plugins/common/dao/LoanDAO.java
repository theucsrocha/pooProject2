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
     * Saves a new loan to the database.
     * @param loan The Loan object to be saved.
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
     * Retrieves all loans, joining user and book data for display.
     * @return A list of populated Loan objects.
     */
    public List<Loan> findAll() {
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
     * Marks a loan as returned by setting the current date as the return date.
     * @param loanId The ID of the loan to be updated.
     */
    public void markAsReturned(int loanId) {
        String sql = "UPDATE loans SET return_date = ? WHERE loan_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setInt(2, loanId);

            stmt.executeUpdate();
            System.out.println("Loan marked as returned successfully!");

        } catch (SQLException e) {
            System.err.println("Error while updating loan return date: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all loans that are still active (have no return date).
     * @return A list of Loan objects representing active loans.
     */
    public List<Loan> findAllActive() {
        String sql = "SELECT l.loan_id, l.user_id, l.book_id, l.loan_date, l.return_date, " +
                    "u.name as user_name, b.title as book_title " +
                    "FROM loans l " +
                    "JOIN users u ON l.user_id = u.user_id " +
                    "JOIN books b ON l.book_id = b.book_id " +
                    "WHERE l.return_date IS NULL";

        List<Loan> activeLoans = new ArrayList<>();

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

                loan.setUserName(rs.getString("user_name"));
                loan.setBookTitle(rs.getString("book_title"));

                activeLoans.add(loan);
            }

        } catch (SQLException e) {
            System.err.println("Error finding active loans: " + e.getMessage());
            e.printStackTrace();
        }
        return activeLoans;
    }
}