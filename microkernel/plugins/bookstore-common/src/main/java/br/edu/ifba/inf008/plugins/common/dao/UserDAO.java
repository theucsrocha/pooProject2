package br.edu.ifba.inf008.plugins.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.inf008.plugins.common.db.ConnectionFactory;
import br.edu.ifba.inf008.plugins.common.model.User;

public class UserDAO {

    /**
     * Retrieves all users from the database.
     * @return A list of User objects.
     */
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setRegisteredAt(rs.getTimestamp("registered_at").toLocalDateTime().toLocalDate());
                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("Error while finding all users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Saves a new user to the database.
     * @param user The User object to be saved.
     */
    public void save(User user) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());

            stmt.executeUpdate();
            System.out.println("User saved successfully!");

        } catch (SQLException e) {
            System.err.println("Error while saving user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing user's information.
     * @param user The User object with updated data.
     */
    public void update(User user) {
        String sql = "UPDATE users SET name = ?, email = ? WHERE user_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getId());

            stmt.executeUpdate();
            System.out.println("User updated successfully!");

        } catch (SQLException e) {
            System.err.println("Error while updating user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deletes a user from the database by their ID.
     * @param userId The ID of the user to be deleted.
     */
    public void delete(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
            System.out.println("User deleted successfully!");

        } catch (SQLException e) {
            System.err.println("Error while deleting user: " + e.getMessage());
            e.printStackTrace();
        }
    }
}