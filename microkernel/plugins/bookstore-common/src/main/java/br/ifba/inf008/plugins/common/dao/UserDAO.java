package br.edu.ifba.inf008.plugins.bookstore.dao;

import br.edu.ifba.inf008.plugins.bookstore.db.ConnectionFactory;
import br.edu.ifba.inf008.plugins.bookstore.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    /**
     * Busca todos os usuários da tabela 'users'.
     * O padrão é o mesmo do BookDAO, apenas mudam a SQL e o tipo de objeto.
     * @return Uma lista de objetos User.
     */
    public List<User> findAll() {
        String sql = "SELECT * FROM users"; // SQL para a tabela 'users'
        List<User> users = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Criamos um objeto User vazio
                User user = new User();

                // Usamos os setters de User para preenchê-lo
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                // O Timestamp é um pouco diferente para pegar
                user.setRegisteredAt(rs.getTimestamp("registered_at").toLocalDateTime().toLocalDate());

                // Adicionamos o objeto User preenchido à lista
                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("Error while finding all users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Salva um novo usuário no banco de dados.
     * @param user O objeto User a ser salvo (sem o ID e registeredAt).
     */
    public void save(User user) {
        // A SQL para inserir na tabela 'users'. O registered_at é automático.
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Preenchemos os '?' com os dados do objeto User
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());

            stmt.executeUpdate();
            System.out.println("User saved successfully!");

        } catch (SQLException e) {
            System.err.println("Error while saving user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Adicione estes dois métodos dentro da sua classe UserDAO.java

/**
 * Atualiza um usuário existente no banco de dados.
 * @param user O objeto User a ser atualizado. O ID do usuário deve estar preenchido.
 */
public void update(User user) {
    // A SQL para ATUALIZAR um registro na tabela 'users' ONDE o 'user_id' corresponder.
    String sql = "UPDATE users SET name = ?, email = ? WHERE user_id = ?";

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        // Preenchemos os '?' com os novos dados do objeto User.
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getEmail());
        // O ID é o último, para a cláusula WHERE.
        stmt.setInt(3, user.getId());

        stmt.executeUpdate();
        System.out.println("User updated successfully!");

    } catch (SQLException e) {
        System.err.println("Error while updating user: " + e.getMessage());
        e.printStackTrace();
    }
}

/**
 * Deleta um usuário do banco de dados com base no seu ID.
 * @param userId O ID do usuário a ser deletado.
 */
public void delete(int userId) {
    // A SQL para DELETAR um registro da tabela 'users' ONDE o 'user_id' corresponder.
    String sql = "DELETE FROM users WHERE user_id = ?";

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        // Substituímos o '?' pelo ID que recebemos.
        stmt.setInt(1, userId);

        stmt.executeUpdate();
        System.out.println("User deleted successfully!");

    } catch (SQLException e) {
        System.err.println("Error while deleting user: " + e.getMessage());
        e.printStackTrace();
    }
}
}