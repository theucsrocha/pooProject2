package br.edu.ifba.inf008.plugins.bookstore.controller;

import java.util.List;

import br.edu.ifba.inf008.plugins.bookstore.dao.BookDAO;
import br.edu.ifba.inf008.plugins.bookstore.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn; // ADICIONE ESTA LINHA
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField isbnField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField copiesField;

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

    @FXML
    private void saveBook(){
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        int year = Integer.parseInt(yearField.getText());
        int copies = Integer.parseInt(copiesField.getText());

         if (title.isEmpty() || author.isEmpty()) {
        System.err.println("Title and Author are mandatory.");
        return; 
    }

    // 2. Criar um objeto Book com os dados lidos da tela.
    Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setIsbn(isbn);
        newBook.setPublishedYear(year);
        newBook.setCopiesAvailable(copies);

        // 3. Entregar o novo objeto para o DAO salvar no banco.
        this.bookDAO.save(newBook);

        // 4. Atualizar a tabela para mostrar o novo livro.
        loadBooks();

        // 5. Limpar os campos de texto.
        clearFields();
    }
    private void clearFields() {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        yearField.clear();
        copiesField.clear();
    }

}
