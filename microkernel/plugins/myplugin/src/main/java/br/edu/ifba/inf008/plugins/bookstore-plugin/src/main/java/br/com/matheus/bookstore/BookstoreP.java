package br.com.matheus.bookstore;

import br.com.matheus.bookstore.dao.BookDAO; 
import br.com.matheus.bookstore.model.Book;  
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.List;

public class BookstoreP implements IPlugin {

  
    @Override
    public boolean init() {
        // Pega o controlador da UI do núcleo para podermos adicionar menus e abas.
        IUIController uiController = ICore.getInstance().getUIController();

        // Adiciona o menu "Livraria" e o item "Gerenciar Livros" na janela principal.
        MenuItem menuItem = uiController.createMenuItem("Livraria", "Gerenciar Livros");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Menu 'Gerenciar Livros' foi clicado!");
            }
        });

        // Adiciona uma aba de exemplo.
        uiController.createTab("Cadastro de Livros", new Rectangle(200, 200, Color.LIGHTSTEELBLUE));

        // ==================================================================
        // NOSSO CÓDIGO DE TESTE DO BANCO DE DADOS
        // ==================================================================
        System.out.println("--- INICIANDO TESTE DE CONEXÃO COM O BANCO DE DADOS ---");
        
        // 1. Criamos uma instância do nosso especialista em livros (o DAO).
        BookDAO bookDAO = new BookDAO();
        
        // 2. Chamamos o método para buscar todos os livros do banco.
        List<Book> booksDoBanco = bookDAO.findAll();
        
        // 3. Verificamos o resultado e imprimimos no console.
        if (booksDoBanco.isEmpty()) {
            System.out.println("Não foi encontrado nenhum livro no banco de dados ou houve um erro na conexão.");
        } else {
            System.out.println("CONEXÃO BEM-SUCEDIDA! Livros encontrados:");
            for (Book book : booksDoBanco) {
                // Usamos o método toString() da classe Book para imprimir os detalhes.
                System.out.println("- " + book.toString());
            }
        }
        
        System.out.println("--- FIM DO TESTE DE CONEXÃO ---");
        // ==================================================================

        return true;
    }
}
