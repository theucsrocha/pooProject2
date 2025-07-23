package br.edu.ifba.inf008.plugins.bookstore.controller;

import java.util.List;

import br.edu.ifba.inf008.plugins.bookstore.dao.BookDAO;
import br.edu.ifba.inf008.plugins.bookstore.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn; // ADICIONE ESTA LINHA
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManageBooksController {
    
    @FXML
    private TableView<Book> booksTable;
    
    @FXML
    private TableColumn<Book, Integer> idColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, Integer> copiesColumn;

    private BookDAO bookDAO;

     @FXML
    public void initialize() {
     
        this.bookDAO = new BookDAO();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        copiesColumn.setCellValueFactory(new PropertyValueFactory<>("copiesAvailable"));
        
        loadBooks();
    }
     private void loadBooks() {
        // Limpa a tabela antes de carregar novos dados.
        booksTable.getItems().clear();
        
        // Usa nosso DAO para buscar a lista de livros do banco.
        List<Book> books = this.bookDAO.findAll();
        
        // Adiciona todos os livros da lista na tabela.
        booksTable.getItems().addAll(books);
    }

}
