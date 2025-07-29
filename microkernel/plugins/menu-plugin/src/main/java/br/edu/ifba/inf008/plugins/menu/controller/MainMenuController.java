package br.edu.ifba.inf008.plugins.menu.controller;

import java.io.IOException;

import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MainMenuController {

    private IUIController uiController;

    public void setUiController(IUIController uiController) {
        this.uiController = uiController;
    }

    @FXML
    private void openManageBooks() {
        try {
            // Carrega a view de livros, exatamente como o BooksPlugin faz
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins/books/fxml/ManageBooksView.fxml"));
            loader.setClassLoader(getClass().getClassLoader()); // Importante para encontrar o controller
            Parent root = loader.load();
            uiController.createTab("Livros", root);
        } catch (IOException e) {
            System.err.println("Erro ao carregar a view de livros a partir do menu principal:");
            e.printStackTrace();
        }
    }

    @FXML
    private void openManageUsers() {
        try {
            // Carrega a view de usuários, exatamente como o UserPlugin faz
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins/users/fxml/ManageUsersView.fxml"));
            loader.setClassLoader(getClass().getClassLoader()); // Importante para encontrar o controller
            Parent root = loader.load();
            uiController.createTab("Usuários", root);
        } catch (IOException e) {
            System.err.println("Erro ao carregar a view de usuários a partir do menu principal:");
            e.printStackTrace();
        }
    }

    @FXML
    private void openManageLoans() {
        // Esta funcionalidade pode ser implementada no futuro
        try {
            // Carrega a view de usuários, exatamente como o UserPlugin faz
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins//fxml/ManageUsersView.fxml"));
            loader.setClassLoader(getClass().getClassLoader()); // Importante para encontrar o controller
            Parent root = loader.load();
            uiController.createTab("Usuários", root);
        } catch (IOException e) {
            System.err.println("Erro ao carregar a view de usuários a partir do menu principal:");
            e.printStackTrace();
        }
    }
}