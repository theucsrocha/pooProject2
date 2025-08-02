package br.edu.ifba.inf008.plugins;

import java.io.IOException;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.plugins.menu.controller.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class MenuPlugin implements IPlugin {

    @Override
    public boolean init() {
        IUIController uiController = ICore.getInstance().getUIController();
        try {
            // The path to the main menu FXML
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins/menu/fxml/MainMenuView.fxml"));
            loader.setClassLoader(getClass().getClassLoader());
            
            // Manually create and set the controller to pass the uiController instance
            MainMenuController controller = new MainMenuController();
            controller.setUiController(uiController);
            loader.setController(controller);

            Parent root = loader.load();
            
            // Creates the main "Menu" tab
            uiController.createTab("Menu", root);
           
           
           
            

        } catch (IOException e) {
            System.err.println("Error loading the main menu:");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}