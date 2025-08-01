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
        // Pega o controlador da UI do núcleo
        IUIController uiController = ICore.getInstance().getUIController();

        // 1. Cria o item de menu "Livros Emprestados" dentro de um novo menu "Relatórios".
        MenuItem menuItem = uiController.createMenuItem("Relatórios", "Livros Emprestados");

        // 2. Define a ação que acontece ao clicar no item de menu.
        menuItem.setOnAction(event -> {
            try {
                // 3. Carrega a tela de relatório.
                FXMLLoader loader = new FXMLLoader(getClass().getResource("reports/fxml/ReportView.fxml"));
                loader.setClassLoader(getClass().getClassLoader());
                Parent root = loader.load();

                // 4. E pede ao controlador principal para exibir a tela em uma nova aba.
                uiController.createTab("Relatório de Empréstimos", root);

            } catch (IOException e) {
                System.err.println("Erro ao carregar a view de relatório:");
                e.printStackTrace();
            }
        });

        return true;
    }
}