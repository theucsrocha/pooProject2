
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
        
        MenuItem menuItem = uiController.createMenuItem("Cadastros", "Gerenciar Usuários");

        menuItem.setOnAction(event -> {
            try {
                // 2. USE O CAMINHO ABSOLUTO PARA O FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins/users/fxml/ManageUsersView.fxml"));
                loader.setClassLoader(getClass().getClassLoader());
                Parent root = loader.load();
                uiController.createTab("Usuários", root);
            } catch (IOException e) {
                System.err.println("Erro ao carregar a view de usuários:");
                e.printStackTrace();
            }
        });

        return true;
    }
}