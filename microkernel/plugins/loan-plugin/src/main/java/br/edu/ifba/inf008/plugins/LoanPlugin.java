package br.edu.ifba.inf008.plugins;

import java.io.IOException;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;

// The class name must match the final .jar name (e.g., LoanPlugin.jar)
public class LoanPlugin implements IPlugin {

    @Override
    public boolean init() {
        // Get the UI controller from the core
        IUIController uiController = ICore.getInstance().getUIController();

        // 1. The plugin creates its own menu item
        MenuItem menuItem = uiController.createMenuItem("Management", "Loans");
        
        // 2. The plugin defines what happens when its menu item is clicked
        menuItem.setOnAction(event -> {
            try {
                // 3. It loads its OWN FXML screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins/loans/fxml/ManageLoansView.fxml"));
                loader.setClassLoader(getClass().getClassLoader());
                Parent root = loader.load();
                
                // 4. And asks the main controller to display its screen in a new tab
                uiController.createTab("Manage Loans", root);

            } catch (IOException e) {
                System.err.println("Error loading the loans view:");
                e.printStackTrace();
            }
        });
        
        return true;
    }
}