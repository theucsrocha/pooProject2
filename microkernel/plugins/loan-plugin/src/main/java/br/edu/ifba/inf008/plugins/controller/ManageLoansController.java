// O pacote deve seguir a estrutura do seu plugin de empréstimos
package br.edu.ifba.inf008.plugins.controller;

// Importações de todas as classes que vamos precisar
import java.time.LocalDate;
import java.util.List;

import br.edu.ifba.inf008.plugins.common.dao.BookDAO;
import br.edu.ifba.inf008.plugins.common.dao.LoanDAO;
import br.edu.ifba.inf008.plugins.common.dao.UserDAO;
import br.edu.ifba.inf008.plugins.common.model.Book;
import br.edu.ifba.inf008.plugins.common.model.Loan;
import br.edu.ifba.inf008.plugins.common.model.User;
import javafx.fxml.FXML;
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
        
        // 3. Carrega os dados iniciais
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
        System.out.println("Register Button has cliked");
        // A lógica para registrar o empréstimo virá aqui.
    }
}