package br.edu.ifba.inf008.plugins;

import java.io.IOException;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;

public class BooksPlugin implements IPlugin {

    @Override
    public boolean init() {
        // Pega o controlador da UI do núcleo
        IUIController uiController = ICore.getInstance().getUIController();

        // Cria o item de menu "Gerenciar Livros" dentro de um menu "Cadastros"
        MenuItem menuItem = uiController.createMenuItem("Cadastros", "Gerenciar Livros");

        // Define a ação que acontece ao clicar no item de menu
        menuItem.setOnAction(event -> {
            try {
                // Prepara o carregador para a nossa tela de livros
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifba/inf008/plugins/books/fxml/ManageBooksView.fxml"));
                loader.setClassLoader(getClass().getClassLoader());

                // Carrega o FXML
                Parent root = loader.load();

                // Cria uma nova aba com o conteúdo da nossa tela
                uiController.createTab("Livros", root);

            } catch (IOException e) {
                System.err.println("Erro ao carregar a view de livros:");
                e.printStackTrace();
            }
        });

        return true;
    }
}