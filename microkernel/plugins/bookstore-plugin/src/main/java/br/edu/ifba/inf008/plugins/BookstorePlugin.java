package br.edu.ifba.inf008.plugins; 

import java.io.IOException;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IUIController;
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
                 FXMLLoader loader = new FXMLLoader();
                
                // Defina a localização do FXML
                loader.setLocation(getClass().getResource("/br/edu/ifba/inf008/plugins/bookstore/fxml/ManageBooksView.fxml"));
                
                // Diga ao loader para usar o ClassLoader deste plugin para encontrar o Controller
                loader.setClassLoader(getClass().getClassLoader());

                // Carrega o FXML e devolve o layout principal (nosso BorderPane)
                Parent root = loader.load();
                
                uiController.createTab("Manage Books", root);

            } catch (IOException e) {
                System.err.println("Erro to load the FXML of BookStore:");
                e.printStackTrace();
            }
        });

        
        
        return true;
    }
}
