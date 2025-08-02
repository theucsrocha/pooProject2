package br.edu.ifba.inf008.plugins.users.controller;

import br.edu.ifba.inf008.plugins.common.dao.UserDAO;
import br.edu.ifba.inf008.plugins.common.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the user management view.
 */
public class ManageUsersController {

    // FXML components
    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, Integer> idColumn;
    @FXML private TableColumn<User, String> nameColumn;
    @FXML private TableColumn<User, String> emailColumn;
    @FXML private TableColumn<User, LocalDate> registrationDateColumn;
    @FXML private TextField nameField;
    @FXML private TextField emailField;

    private UserDAO userDAO;
    private User selectedUser;

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        this.userDAO = new UserDAO();
        
        // Configure table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        registrationDateColumn.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));

        // Add listener for table selection
        usersTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> populateForm(newValue)
        );

        loadUsers();
    }

    /**
     * Fetches users from the database and populates the table.
     */
    private void loadUsers() {
        usersTable.getItems().clear();
        List<User> users = this.userDAO.findAll();
        usersTable.getItems().addAll(users);
    }

    /**
     * Handles the "Save" button action.
     */
    @FXML
    private void saveUser() {
        String name = nameField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Name and Email are required.");
            return;
        }

        // If no user is selected, create a new one.
        if (this.selectedUser == null) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            this.userDAO.save(newUser);
            showAlert(Alert.AlertType.INFORMATION, "Success", "User created successfully!");
        // Otherwise, update the existing user.
        } else {
            this.selectedUser.setName(name);
            this.selectedUser.setEmail(email);
            this.userDAO.update(this.selectedUser);
            showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully!");
        }

        loadUsers();
        clearFormAction();
    }

    /**
     * Handles the "Delete" button action.
     */
    @FXML
    private void deleteUser() {
        if (this.selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a user to delete.");
            return;
        }

        // Show confirmation dialog
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Deletion");
        confirmation.setHeaderText("Delete user: " + this.selectedUser.getName());
        confirmation.setContentText("Are you sure? This action cannot be undone.");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            this.userDAO.delete(this.selectedUser.getId());
            showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully!");
            loadUsers();
            clearFormAction();
        }
    }

    /**
     * Fills the form with data from the selected user.
     */
    private void populateForm(User user) {
        this.selectedUser = user;
        if (user != null) {
            nameField.setText(user.getName());
            emailField.setText(user.getEmail());
        } else {
            clearFields();
        }
    }

    /**
     * Clears the text fields in the form.
     */
    private void clearFields() {
        nameField.clear();
        emailField.clear();
    }

    /**
     * Handles the "Clear" button action.
     */
    @FXML
    private void clearFormAction() {
        usersTable.getSelectionModel().clearSelection();
        clearFields();
        this.selectedUser = null;
    }

    /**
     * Helper method to show an alert dialog.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}