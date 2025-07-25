package br.edu.ifba.inf008.plugins.bookstore.controller;

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
        // Chamamos um método de ajuda para carregar a tela de livros.
        loadTab("Manage Books", "fxml/ManageBooksView.fxml");
    }

    @FXML
    private void openManageUsers() {
       
        System.out.println("Funcionalidade de Gerenciar Usuários ainda não implementada.");
    }

    @FXML
    private void openManageLoans() {
   
        System.out.println("Funcionalidade de Gerenciar Empréstimos ainda não implementada.");
    }

    // Método de ajuda para carregar um FXML e criar uma nova aba.
    private void loadTab(String tabName, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            uiController.createTab(tabName, root);
        } catch (IOException e) {
            System.err.println("Erro ao carregar o FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }
}