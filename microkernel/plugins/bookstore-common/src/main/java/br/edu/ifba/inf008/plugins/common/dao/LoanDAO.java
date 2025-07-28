package br.edu.ifba.inf008.plugins.common.dao;

import br.edu.ifba.inf008.plugins.common.db.ConnectionFactory;
import br.edu.ifba.inf008.plugins.common.model.Loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date; // Usaremos java.sql.Date para compatibilidade com a coluna DATE

public class LoanDAO {

    /**
     * Salva um novo empréstimo no banco de dados.
     * @param loan O objeto Loan a ser salvo.
     */
    public void save(Loan loan) {
        // A SQL para inserir na tabela 'loans'.
        String sql = "INSERT INTO loans (user_id, book_id, loan_date) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Preenchemos os '?' com os dados do objeto Loan.
            stmt.setInt(1, loan.getUserId());
            stmt.setInt(2, loan.getBookId());
            // Convertemos o java.time.LocalDate para java.sql.Date
            stmt.setDate(3, Date.valueOf(loan.getLoanDate()));

            stmt.executeUpdate();
            System.out.println("Loan saved successfully!");

        } catch (SQLException e) {
            System.err.println("Error while saving loan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
}