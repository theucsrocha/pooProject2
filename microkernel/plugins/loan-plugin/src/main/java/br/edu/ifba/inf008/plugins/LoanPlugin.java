package br.edu.ifba.inf008.plugins; // Ou o pacote correto que você definiu

import java.io.IOException;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;

// O nome da classe deve ser o mesmo nome do .jar final (ex: LoanPlugin.jar)
public class LoanPlugin implements IPlugin {

    @Override
    public boolean init() {
        // Pega o controlador da UI do núcleo
        IUIController uiController = ICore.getInstance().getUIController();

        // 1. O plugin cria seu próprio item de menu
        MenuItem menuItem = uiController.createMenuItem("Gerenciamento", "Empréstimos");
        
        // 2. O plugin define o que acontece quando seu item de menu é clicado
        menuItem.setOnAction(event -> {
            try {
                // 3. Ele carrega sua PRÓPRIA tela FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins/loans/fxml/ManageLoansView.fxml"));
                loader.setClassLoader(getClass().getClassLoader());
                Parent root = loader.load();
                
                // 4. E pede ao controlador principal para exibir sua tela em uma nova aba
                uiController.createTab("Gerenciar Empréstimos", root);

            } catch (IOException e) {
                System.err.println("Erro ao carregar a view de empréstimos:");
                e.printStackTrace();
            }
        });
        
        return true;
    }
}