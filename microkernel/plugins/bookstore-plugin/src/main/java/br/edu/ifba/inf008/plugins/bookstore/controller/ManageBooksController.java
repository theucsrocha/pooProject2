package br.edu.ifba.inf008.plugins.bookstore.controller;

import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;
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
    private Book selectedBook;

     @FXML
    public void initialize() {
     
        this.bookDAO = new BookDAO();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        copiesColumn.setCellValueFactory(new PropertyValueFactory<>("copiesAvailable"));
        
        loadBooks();
        booksTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                // 'newValue' é o novo item selecionado (o livro em que o usuário clicou)
                populateForm(newValue);
            }
        );
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
private void saveBook() {
    // 1. Pega os dados do formulário (isso não muda).
    String title = titleField.getText();
    String author = authorField.getText();
    String isbn = isbnField.getText();
    
    // Validação básica para garantir que campos não estão vazios antes de converter para número
    if (title.isEmpty() || author.isEmpty() || yearField.getText().isEmpty() || copiesField.getText().isEmpty()) {
        System.err.println("Todos os campos devem ser preenchidos.");
        // Futuramente, mostraremos um alerta para o usuário.
        return;
    }
    
    int year = Integer.parseInt(yearField.getText());
    int copies = Integer.parseInt(copiesField.getText());

    // 2. AQUI ESTÁ A DECISÃO INTELIGENTE (if/else)
    if (this.selectedBook == null) {
        // --- LÓGICA PARA CRIAR UM NOVO LIVRO ---
        System.out.println("Nenhum livro selecionado, criando um novo...");
        
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setIsbn(isbn);
        newBook.setPublishedYear(year);
        newBook.setCopiesAvailable(copies);

        // Chama o método SAVE do DAO
        this.bookDAO.save(newBook);

    } else {
        // --- LÓGICA PARA ATUALIZAR UM LIVRO EXISTENTE ---
        System.out.println("Livro selecionado encontrado, atualizando...");
        
        // Atualiza o objeto 'selectedBook' que já temos na memória com os novos dados do formulário.
        this.selectedBook.setTitle(title);
        this.selectedBook.setAuthor(author);
        this.selectedBook.setIsbn(isbn);
        this.selectedBook.setPublishedYear(year);
        this.selectedBook.setCopiesAvailable(copies);
        
        // Chama o método UPDATE do DAO, passando o objeto já atualizado.
        this.bookDAO.update(this.selectedBook);
    }

    // 3. Etapas finais que acontecem em ambos os casos (Criar ou Atualizar)
    loadBooks();      // Recarrega a tabela para mostrar os dados atualizados.
    clearFormAction(); // Limpa o formulário e o estado de seleção.
}
    private void clearFields() {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        yearField.clear();
        copiesField.clear();
    }
    private void populateForm(Book book) {
    // Guarda a referência do livro selecionado
    this.selectedBook = book;

    // Se o livro não for nulo (ou seja, se uma linha real foi selecionada)
    if (book != null) {
        // Preenche os campos de texto com os dados do livro
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        isbnField.setText(book.getIsbn());
        yearField.setText(String.valueOf(book.getPublishedYear()));
        copiesField.setText(String.valueOf(book.getCopiesAvailable()));
    } else {
        // Se o usuário clicou fora das linhas, limpa o formulário
        clearFields();
    }
}
@FXML
private void clearFormAction() {
    // 1. Limpa a seleção na própria tabela.
    booksTable.getSelectionModel().clearSelection();

    // 2. Chama nosso método de ajuda para limpar os campos de texto.
    clearFields();

    // 3. Anula a referência ao livro selecionado.
    // Isso é CRUCIAL para que o botão "Salvar" saiba que deve CRIAR um novo livro, e não ATUALIZAR.
    this.selectedBook = null;
}

@FXML
private void deleteBook() {
    // 1. Pega o livro que está selecionado na tabela.
    Book bookToDelete = this.selectedBook;

    // 2. Verifica se algum livro foi realmente selecionado.
    if (bookToDelete == null) {
        System.err.println("Nenhum livro selecionado para deletar.");
        // Futuramente, podemos mostrar um alerta de erro aqui.
        return;
    }

    // 3. Cria uma janela de confirmação para o usuário.
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmationAlert.setTitle("Confirm Deletion");
    confirmationAlert.setHeaderText("You are about to delete the book: " + bookToDelete.getTitle());
    confirmationAlert.setContentText("This action cannot be undone. Are you sure?");

    // 4. Mostra a janela e espera o usuário clicar em um botão (OK ou Cancelar).
    Optional<ButtonType> result = confirmationAlert.showAndWait();

    // 5. Se o usuário clicou no botão "OK"...
    if (result.isPresent() && result.get() == ButtonType.OK) {
        // ...chama o método delete do DAO, passando o ID do livro.
        this.bookDAO.delete(bookToDelete.getId());

        // E, finalmente, recarrega a tabela e limpa o formulário.
        loadBooks();
        clearFormAction();
    }
}
}
