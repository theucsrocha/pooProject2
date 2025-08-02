package br.edu.ifba.inf008.plugins;

import java.io.IOException;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;

/**
 * Plugin responsible for managing books.
 * It adds a "Manage Books" option to the main menu.
 */
public class BooksPlugin implements IPlugin {

    @Override
    public boolean init() {
        // Get the UI controller from the core
        IUIController uiController = ICore.getInstance().getUIController();

        // Create a "Manage Books" menu item under a "Registers" menu
        MenuItem menuItem = uiController.createMenuItem("Registers", "Manage Books");

        // Define the action to be performed when the menu item is clicked
        menuItem.setOnAction(event -> {
            try {
                // Prepare the loader for our books screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins/books/fxml/ManageBooksView.fxml"));
                loader.setClassLoader(getClass().getClassLoader());

                // Load the FXML
                Parent root = loader.load();

                // Create a new tab with the content of our screen
                uiController.createTab("Books", root);

            } catch (IOException e) {
                System.err.println("Error loading the books view:");
                e.printStackTrace();
            }
        });

        return true;
    }
}