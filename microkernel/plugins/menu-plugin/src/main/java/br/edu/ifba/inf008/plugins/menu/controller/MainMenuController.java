package br.edu.ifba.inf008.plugins.menu.controller;

import java.io.IOException;
import javafx.application.Platform;
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
            // Loads the books view, just like the BooksPlugin does
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins/books/fxml/ManageBooksView.fxml"));
            loader.setClassLoader(getClass().getClassLoader()); // Important for finding the controller
            Parent root = loader.load();
            uiController.createTab("Books", root);
        } catch (IOException e) {
            System.err.println("Error loading the books view from the main menu:");
            e.printStackTrace();
        }
    }

    @FXML
    private void openManageUsers() {
        try {
            // Loads the users view, just like the UserPlugin does
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins/users/fxml/ManageUsersView.fxml"));
            loader.setClassLoader(getClass().getClassLoader()); // Important for finding the controller
            Parent root = loader.load();
            uiController.createTab("Users", root);
        } catch (IOException e) {
            System.err.println("Error loading the users view from the main menu:");
            e.printStackTrace();
        }
    }

    @FXML
    private void openManageLoans() {
        try {
            // Loads the loans view, just like the LoanPlugin does
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins/loans/fxml/ManageLoansView.fxml"));
            loader.setClassLoader(getClass().getClassLoader());
            Parent root = loader.load();
            // Creates a tab with a consistent title
            uiController.createTab("Manage Loans", root);
        } catch (IOException e) {
            // Standardized error message
            System.err.println("Error loading the loans view from the main menu:");
            e.printStackTrace();
        }
    }

    @FXML
    private void exitApplication() {
        // Platform.exit() is the correct way to close a JavaFX application.
        Platform.exit();
        System.exit(0);
    }
}