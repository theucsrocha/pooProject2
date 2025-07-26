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
import java.util.List;
import java.util.Optional;

public class ManageUsersController {

    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, Integer> idColumn;
    @FXML private TableColumn<User, String> nameColumn;
    @FXML private TableColumn<User, String> emailColumn;
    @FXML private TextField nameField;
    @FXML private TextField emailField;

    private UserDAO userDAO;
    private User selectedUser;

    @FXML
    public void initialize() {
        this.userDAO = new UserDAO();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        usersTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> populateForm(newValue)
        );

        loadUsers();
    }

    private void loadUsers() {
        usersTable.getItems().clear();
        List<User> users = this.userDAO.findAll();
        usersTable.getItems().addAll(users);
    }

    @FXML
    private void saveUser() {
        String name = nameField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Nome e Email são obrigatórios.");
            return;
        }

        if (this.selectedUser == null) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            this.userDAO.save(newUser);
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Usuário criado com sucesso!");
        } else {
            this.selectedUser.setName(name);
            this.selectedUser.setEmail(email);
            this.userDAO.update(this.selectedUser);
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Usuário atualizado com sucesso!");
        }

        loadUsers();
        clearFormAction();
    }

    @FXML
    private void deleteUser() {
        if (this.selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "Nenhuma Seleção", "Por favor, selecione um usuário para deletar.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmar Deleção");
        confirmation.setHeaderText("Deletar usuário: " + this.selectedUser.getName());
        confirmation.setContentText("Você tem certeza? Esta ação não pode ser desfeita.");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            this.userDAO.delete(this.selectedUser.getId());
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Usuário deletado com sucesso!");
            loadUsers();
            clearFormAction();
        }
    }

    private void populateForm(User user) {
        this.selectedUser = user;
        if (user != null) {
            nameField.setText(user.getName());
            emailField.setText(user.getEmail());
        } else {
            clearFields();
        }
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
    }

    @FXML
    private void clearFormAction() {
        usersTable.getSelectionModel().clearSelection();
        clearFields();
        this.selectedUser = null;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}