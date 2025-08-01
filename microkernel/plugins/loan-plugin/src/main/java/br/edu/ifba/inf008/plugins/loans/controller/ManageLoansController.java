// O pacote deve seguir a estrutura do seu plugin de empréstimos
package br.edu.ifba.inf008.plugins.loans.controller;

// Importações de todas as classes que vamos precisar
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javafx.util.StringConverter;
import br.edu.ifba.inf008.plugins.common.dao.BookDAO;
import br.edu.ifba.inf008.plugins.common.dao.LoanDAO;
import br.edu.ifba.inf008.plugins.common.dao.UserDAO;
import br.edu.ifba.inf008.plugins.common.model.Book;
import br.edu.ifba.inf008.plugins.common.model.Loan;
import br.edu.ifba.inf008.plugins.common.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManageLoansController {

    // --- Componentes da View (FXML) ---
    @FXML
    private TableView<Loan> loansTable;
    @FXML
    private TableColumn<Loan, Integer> loanIdColumn;
    @FXML
    private TableColumn<Loan, String> bookTitleColumn;
    @FXML
    private TableColumn<Loan, String> userNameColumn;
    @FXML
    private TableColumn<Loan, LocalDate> loanDateColumn;
    @FXML
    private TableColumn<Loan, LocalDate> returnDateColumn;

    @FXML
    private ComboBox<Book> bookComboBox;
    @FXML
    private ComboBox<User> userComboBox;

    // --- Camada de Dados ---
    private BookDAO bookDAO;
    private UserDAO userDAO;
    private LoanDAO loanDAO;

    // O método initialize() é chamado automaticamente pelo JavaFX
   @FXML
    public void initialize() {
        // 1. Instancia todos os DAOs que vamos precisar nesta tela
        this.bookDAO = new BookDAO();
        this.userDAO = new UserDAO();
        this.loanDAO = new LoanDAO();

        // 2. Configura as colunas da tabela de empréstimos
        loanIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        
        // 3. CHAMA O MÉTODO DE FORMATAÇÃO DOS COMBOBOXES
        setupComboBoxes();
        
        // 4. Carrega os dados iniciais (APENAS UMA VEZ)
        loadBooksIntoComboBox();
        loadUsersIntoComboBox();
        loadLoansIntoTable();
    }

    // Método para buscar os livros no banco e colocá-los na caixa de seleção
    private void loadBooksIntoComboBox() {
        List<Book> books = this.bookDAO.findAll();
        bookComboBox.getItems().setAll(books);
    }

    // Método para buscar os usuários no banco e colocá-los na caixa de seleção
    private void loadUsersIntoComboBox() {
        List<User> users = this.userDAO.findAll();
        userComboBox.getItems().setAll(users);
    }
    
    // Método que busca os empréstimos no DAO e atualiza a tabela
    private void loadLoansIntoTable() {
        loansTable.getItems().clear();
        List<Loan> loans = this.loanDAO.findAll();
        loansTable.getItems().addAll(loans);
    }

    // Método para o botão "Registrar Empréstimo" (ainda não implementado)
   @FXML
private void registerLoan() {
    // 1. Pega os objetos selecionados nos ComboBoxes
    Book selectedBook = bookComboBox.getSelectionModel().getSelectedItem();
    User selectedUser = userComboBox.getSelectionModel().getSelectedItem();

    // 2. Validação: verifica se algo foi selecionado
    if (selectedBook == null || selectedUser == null) {
        showAlert(Alert.AlertType.WARNING, "Incomplete Selection", "Please select a book and a user.");
        return;
    }

    // 3. Regra de Negócio: verifica se há cópias disponíveis
    if (selectedBook.getCopiesAvailable() <= 0) {
        showAlert(Alert.AlertType.ERROR, "Out of stock", "There are no copies of the book available. '" + selectedBook.getTitle() + "' para empréstimo.");
        return;
    }

    // 4. Se tudo estiver certo, cria e salva o novo empréstimo
    Loan newLoan = new Loan();
    newLoan.setBookId(selectedBook.getId());
    newLoan.setUserId(selectedUser.getId());
    newLoan.setLoanDate(LocalDate.now()); // Define a data do empréstimo como hoje

    this.loanDAO.save(newLoan);

    // 5. Decrementa o número de cópias do livro
    this.bookDAO.decrementCopies(selectedBook.getId());

    // 6. Feedback e atualização da tela
    showAlert(Alert.AlertType.INFORMATION, "Sucessefull", "Loan registred");

    // Limpa e recarrega todos os dados
    loadLoansIntoTable();
    loadBooksIntoComboBox(); // Recarrega os livros para mostrar a nova contagem de cópias
    userComboBox.getSelectionModel().clearSelection();
}


private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

// Adicione este novo método à sua classe ManageLoansController

private void setupComboBoxes() {
    // Cria um conversor para o ComboBox de Livros
    StringConverter<Book> bookConverter = new StringConverter<Book>() {
        @Override
        public String toString(Book book) {
            // Se o objeto não for nulo, retorne o título dele. Senão, retorne uma string vazia.
            return book == null ? "" : book.getTitle();
        }

        @Override
        public Book fromString(String string) {
            // Este método não é necessário para nós, pois não vamos digitar um livro novo.
            return null;
        }
    };


    StringConverter<User> userConverter = new StringConverter<User>() {
        @Override
        public String toString(User user) {
            
            return user == null ? "" : user.getName();
        }

        @Override
        public User fromString(String string) {
            return null;
        }
    };

    
    bookComboBox.setConverter(bookConverter);
    userComboBox.setConverter(userConverter);
}
    @FXML
    private void confirmReturn(){
        // 1. Pega o empréstimo que está selecionado na tabela.
    Loan selectedLoan = loansTable.getSelectionModel().getSelectedItem();

    // 2. Verifica se algum empréstimo foi realmente selecionado.
    if (selectedLoan == null) {
        showAlert(Alert.AlertType.WARNING, "Nenhuma Seleção", "Por favor, selecione um empréstimo para registrar a devolução.");
        return;
    }

    // 3. Verifica se o empréstimo já não foi devolvido.
    if (selectedLoan.getReturnDate() != null) {
        showAlert(Alert.AlertType.INFORMATION, "Aviso", "Este empréstimo já foi devolvido anteriormente.");
        return;
    }

    // 4. Cria uma janela de confirmação.
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmationAlert.setTitle("Confirmar Devolução");
    confirmationAlert.setHeaderText("Registrar devolução para o livro: " + selectedLoan.getBookTitle());
    confirmationAlert.setContentText("Você tem certeza?");

    Optional<ButtonType> result = confirmationAlert.showAndWait();

    // 5. Se o usuário clicou em "OK"...
    if (result.isPresent() && result.get() == ButtonType.OK) {
        // ...chama o método do DAO para atualizar a data de devolução.
        this.loanDAO.markAsReturned(selectedLoan.getLoanId());

        // ...chama o método do DAO para devolver a cópia ao estoque.
        this.bookDAO.incrementCopies(selectedLoan.getBookId());

        // Recarrega as tabelas para mostrar os dados atualizados.
        loadLoansIntoTable();
        loadBooksIntoComboBox(); // Para atualizar a contagem de cópias se a tela de livros estiver aberta
    }
    }
}