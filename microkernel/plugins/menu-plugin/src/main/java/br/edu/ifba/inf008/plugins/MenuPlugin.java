// NOVO ARQUIVO: c:\Users\Matheus\Documents\Trabalho2\microkernel\plugins\menu-plugin\src\main\java\br\edu\ifba\inf008\plugins\MenuPlugin.java
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
            // O caminho para o FXML do menu principal
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins/menu/fxml/MainMenuView.fxml"));
            loader.setClassLoader(getClass().getClassLoader());
            
            MainMenuController controller = new MainMenuController();
            controller.setUiController(uiController);
            loader.setController(controller);

            Parent root = loader.load();
            
            // Cria a aba principal "Menu"
            uiController.createTab("Menu", root);

        } catch (IOException e) {
            System.err.println("Erro ao carregar o menu principal:");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}