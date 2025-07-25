package br.edu.ifba.inf008.plugins.bookstore.controller;

import br.edu.ifba.inf008.plugins.bookstore.dao.UserDAO;
import br.edu.ifba.inf008.plugins.bookstore.model.User;
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
    private User selectedUser;
    // --- Componentes da View (FXML) ---
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;

    // --- Camada de Dados ---
    private UserDAO userDAO;
    
    // O método initialize() é chamado automaticamente pelo JavaFX
    @FXML
    public void initialize() {
        // Instancia nosso DAO
        this.userDAO = new UserDAO();

        // Configura as colunas da tabela para saberem de onde pegar os dados
        // da classe 'User' (usando os métodos getId(), getName(), etc.)
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Carrega os dados iniciais do banco na tabela
        loadUsers();
        usersTable.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> populateForm(newValue)
);
    }

    // Método que busca os dados no DAO e atualiza a tabela
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
        System.err.println("Nome e Email são obrigatórios.");
        // Futuramente, adicionaremos um Alert aqui.
        return;
    }

    // Lógica inteligente: se nenhum usuário estiver selecionado, crie um novo.
    if (this.selectedUser == null) {
        System.out.println("Criando novo usuário...");
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        
        this.userDAO.save(newUser);
    } else {
        // Se um usuário já estiver selecionado, atualize-o.
        System.out.println("Atualizando usuário existente...");
        this.selectedUser.setName(name);
        this.selectedUser.setEmail(email);
        
        this.userDAO.update(this.selectedUser);
    }

    // Passos finais: recarregar a tabela e limpar o formulário
    loadUsers();
    clearFormAction();
}
// Adicione estes novos métodos à sua classe ManageUsersController

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
@FXML
private void deleteUser() {
    User userToDelete = this.selectedUser;

    if (userToDelete == null) {
        System.err.println("Nenhum usuário selecionado para deletar.");
        // Futuramente, adicionaremos um Alert aqui.
        return;
    }

    // Cria uma janela de confirmação
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmationAlert.setTitle("Confirm Deletion");
    confirmationAlert.setHeaderText("You are about to delete the user: " + userToDelete.getName());
    confirmationAlert.setContentText("This action cannot be undone. Are you sure?");

    Optional<ButtonType> result = confirmationAlert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
        this.userDAO.delete(userToDelete.getId());
        
        loadUsers();
        clearFormAction();
    }
}
}