package br.edu.ifba.inf008.plugins;

import java.io.IOException;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;

public class ReportPlugin implements IPlugin {

    @Override
    public boolean init() {
        // Get the UI controller from the core
        IUIController uiController = ICore.getInstance().getUIController();

        // 1. Creates the "Borrowed Books" menu item under a new "Reports" menu.
        MenuItem menuItem = uiController.createMenuItem("Reports", "Borrowed Books");

        // 2. Defines the action that occurs when the menu item is clicked.
        menuItem.setOnAction(event -> {
            try {
                // 3. Loads the report screen. Using an absolute path is more reliable.
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins/reports/fxml/ReportView.fxml"));
                loader.setClassLoader(getClass().getClassLoader());
                Parent root = loader.load();

                // 4. And asks the main controller to display the screen in a new tab.
                uiController.createTab("Loan Report", root);

            } catch (IOException e) {
                System.err.println("Error loading the report view:");
                e.printStackTrace();
            }
        });

        return true;
    }
}