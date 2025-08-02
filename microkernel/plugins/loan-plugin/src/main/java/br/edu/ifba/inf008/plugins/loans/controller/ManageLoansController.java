package br.edu.ifba.inf008.plugins.loans.controller;

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

/**
 * Controller for the loan management view.
 */
public class ManageLoansController {

    // --- View Components (from FXML) ---
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

    // --- Data Layer ---
    private BookDAO bookDAO;
    private UserDAO userDAO;
    private LoanDAO loanDAO;

    /**
     * The initialize() method is called automatically by JavaFX after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        // 1. Instantiate all the DAOs we will need on this screen
        this.bookDAO = new BookDAO();
        this.userDAO = new UserDAO();
        this.loanDAO = new LoanDAO();

        // 2. Configure the columns of the loans table
        loanIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        
        // 3. Call the method to format the ComboBoxes
        setupComboBoxes();
        
        // 4. Load the initial data
        loadBooksIntoComboBox();
        loadUsersIntoComboBox();
        loadLoansIntoTable();
    }

    /**
     * Fetches books from the database and puts them in the combo box.
     */
    private void loadBooksIntoComboBox() {
        List<Book> books = this.bookDAO.findAll();
        bookComboBox.getItems().setAll(books);
    }

    /**
     * Fetches users from the database and puts them in the combo box.
     */
    private void loadUsersIntoComboBox() {
        List<User> users = this.userDAO.findAll();
        userComboBox.getItems().setAll(users);
    }
    
    /**
     * Fetches the loans from the DAO and updates the table.
     */
    private void loadLoansIntoTable() {
        loansTable.getItems().clear();
        List<Loan> loans = this.loanDAO.findAll();
        loansTable.getItems().addAll(loans);
    }

    /**
     * Handles the "Register Loan" button action.
     */
    @FXML
    private void registerLoan() {
        // 1. Get the selected objects from the ComboBoxes
        Book selectedBook = bookComboBox.getSelectionModel().getSelectedItem();
        User selectedUser = userComboBox.getSelectionModel().getSelectedItem();

        // 2. Validation: check if something was selected
        if (selectedBook == null || selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "Incomplete Selection", "Please select a book and a user.");
            return;
        }

        // 3. Business Rule: check if there are copies available
        if (selectedBook.getCopiesAvailable() <= 0) {
            showAlert(Alert.AlertType.ERROR, "Out of Stock", "There are no available copies of the book '" + selectedBook.getTitle() + "' for loan.");
            return;
        }

        // 4. If everything is correct, create and save the new loan
        Loan newLoan = new Loan();
        newLoan.setBookId(selectedBook.getId());
        newLoan.setUserId(selectedUser.getId());
        newLoan.setLoanDate(LocalDate.now()); // Sets the loan date to today

        this.loanDAO.save(newLoan);

        // 5. Decrement the number of copies of the book
        this.bookDAO.decrementCopies(selectedBook.getId());

        // 6. Feedback and screen update
        showAlert(Alert.AlertType.INFORMATION, "Success", "Loan registered successfully.");

        // Clear and reload all data
        loadLoansIntoTable();
        loadBooksIntoComboBox(); // Reload the books to show the new copy count
        userComboBox.getSelectionModel().clearSelection();
    }

    /**
     * A helper method to display alerts to the user.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Configures the ComboBoxes to display human-readable text instead of object references.
     */
    private void setupComboBoxes() {
        // Creates a converter for the Book ComboBox
        StringConverter<Book> bookConverter = new StringConverter<>() {
            @Override
            public String toString(Book book) {
                // If the object is not null, return its title. Otherwise, return an empty string.
                return book == null ? "" : book.getTitle();
            }

            @Override
            public Book fromString(String string) {
                // This method is not needed for us, as we won't be typing a new book.
                return null;
            }
        };

        // Creates a converter for the User ComboBox
        StringConverter<User> userConverter = new StringConverter<>() {
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

    /**
     * Handles the "Confirm Return" button action.
     */
    @FXML
    private void confirmReturn() {
        // 1. Get the loan that is selected in the table.
        Loan selectedLoan = loansTable.getSelectionModel().getSelectedItem();

        // 2. Check if a loan was actually selected.
        if (selectedLoan == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a loan to register the return.");
            return;
        }

        // 3. Check if the loan has already been returned.
        if (selectedLoan.getReturnDate() != null) {
            showAlert(Alert.AlertType.INFORMATION, "Notice", "This loan has already been returned.");
            return;
        }

        // 4. Create a confirmation dialog.
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Return");
        confirmationAlert.setHeaderText("Register return for the book: " + selectedLoan.getBookTitle());
        confirmationAlert.setContentText("Are you sure?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // 5. If the user clicked "OK"...
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // ...call the DAO method to update the return date.
            this.loanDAO.markAsReturned(selectedLoan.getLoanId());

            // ...call the DAO method to return the copy to the stock.
            this.bookDAO.incrementCopies(selectedLoan.getBookId());

            // Reload the tables to show the updated data.
            loadLoansIntoTable();
            loadBooksIntoComboBox();
        }
    }
}