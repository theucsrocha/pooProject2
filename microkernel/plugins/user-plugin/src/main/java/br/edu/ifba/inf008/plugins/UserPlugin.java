package br.edu.ifba.inf008.plugins;

import java.io.IOException;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;

public class UserPlugin implements IPlugin {

    @Override
    public boolean init() {
        IUIController uiController = ICore.getInstance().getUIController();
        
        MenuItem menuItem = uiController.createMenuItem("Registers", "Manage Users");

        menuItem.setOnAction(event -> {
            try {
                // Use an absolute path for the FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins/users/fxml/ManageUsersView.fxml"));
                loader.setClassLoader(getClass().getClassLoader());
                Parent root = loader.load();
                uiController.createTab("Users", root);
            } catch (IOException e) {
                System.err.println("Error loading the users view:");
                e.printStackTrace();
            }
        });

        return true;
    }
}