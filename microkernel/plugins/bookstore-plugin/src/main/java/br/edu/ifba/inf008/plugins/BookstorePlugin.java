package br.edu.ifba.inf008.plugins; 

import java.io.IOException;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.plugins.bookstore.controller.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;

public class BookstorePlugin implements IPlugin {

  
     @Override
    public boolean init() {
        IUIController uiController = ICore.getInstance().getUIController();

        MenuItem menuItem = uiController.createMenuItem("BookStore", "Manage Books");
        
        // Vamos fazer o menu item abrir nossa nova aba
        menuItem.setOnAction(event -> {
            try {
                     // 1. Prepara o carregador para o nosso novo menu principal.
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins/bookstore/fxml/MainMenuView.fxml"));
                    loader.setClassLoader(getClass().getClassLoader());
                    // 2. Carrega o FXML e cria a interface.
                    Parent root = loader.load();

                    // 3. Pega a instância do controller que o FXMLLoader acabou de criar.
                    MainMenuController menuController = loader.getController();

                    // 4. Entrega a "ponte" (o uiController) para o controller do menu.
                    menuController.setUiController(uiController);

                    // 5. Cria a aba principal com o nosso menu.
                    uiController.createTab("Main Menu", root);

            } catch (IOException e) {
                System.err.println("Erro to load the FXML of BookStore:");
                e.printStackTrace();
            }
        });

        
        
        return true;
    }
}
