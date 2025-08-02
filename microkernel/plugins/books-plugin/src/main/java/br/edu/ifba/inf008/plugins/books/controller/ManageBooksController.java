package br.edu.ifba.inf008.plugins.books.controller; 

import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import br.edu.ifba.inf008.plugins.common.dao.BookDAO;
import br.edu.ifba.inf008.plugins.common.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller for the book management view.
 */
public class ManageBooksController {
    
    // FXML components injected from the view
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

    // Data Access Object and the currently selected book
    private BookDAO bookDAO;
    private Book selectedBook;

    /**
     * Initializes the controller after its root element has been completely processed.
     */
    @FXML
    public void initialize() {
        this.bookDAO = new BookDAO();

        // Set up the table columns to display book properties.
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        copiesColumn.setCellValueFactory(new PropertyValueFactory<>("copiesAvailable"));
        
        // Load initial data into the table.
        loadBooks();

        // Add a listener to handle row selection events in the table.
        booksTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> populateForm(newValue)
        );
    }

    /**
     * Fetches all books from the database and populates the table.
     */
    private void loadBooks() {
        booksTable.getItems().clear();
        List<Book> books = this.bookDAO.findAll();
        booksTable.getItems().addAll(books);
    }

    /**
     * Handles the "Save" button action. It creates a new book or updates an
     * existing one based on whether a book is selected.
     */
    @FXML
    private void saveBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        
        if (title.isEmpty() || author.isEmpty() || yearField.getText().isEmpty() || copiesField.getText().isEmpty()) {
            System.err.println("All fields must be filled.");
            return;
        }
        
        int year = Integer.parseInt(yearField.getText());
        int copies = Integer.parseInt(copiesField.getText());

        // If no book is selected, create a new one.
        if (this.selectedBook == null) {
            Book newBook = new Book();
            newBook.setTitle(title);
            newBook.setAuthor(author);
            newBook.setIsbn(isbn);
            newBook.setPublishedYear(year);
            newBook.setCopiesAvailable(copies);
            this.bookDAO.save(newBook);
        // Otherwise, update the selected book.
        } else {
            this.selectedBook.setTitle(title);
            this.selectedBook.setAuthor(author);
            this.selectedBook.setIsbn(isbn);
            this.selectedBook.setPublishedYear(year);
            this.selectedBook.setCopiesAvailable(copies);
            this.bookDAO.update(this.selectedBook);
        }

        loadBooks();
        clearFormAction();
    }

    /**
     * A helper method to clear all text fields in the form.
     */
    private void clearFields() {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        yearField.clear();
        copiesField.clear();
    }

    /**
     * Fills the form fields with data from the selected book.
     * @param book The book selected in the table.
     */
    private void populateForm(Book book) {
        this.selectedBook = book;
        if (book != null) {
            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            isbnField.setText(book.getIsbn());
            yearField.setText(String.valueOf(book.getPublishedYear()));
            copiesField.setText(String.valueOf(book.getCopiesAvailable()));
        } else {
            clearFields();
        }
    }

    /**
     * Handles the "Clear" button action. Resets the form for a new entry.
     */
    @FXML
    private void clearFormAction() {
        booksTable.getSelectionModel().clearSelection();
        clearFields();
        this.selectedBook = null;
    }

    /**
     * Handles the "Delete" button action. Deletes the selected book after confirmation.
     */
    @FXML
    private void deleteBook() {
        Book bookToDelete = this.selectedBook;
        if (bookToDelete == null) {
            System.err.println("No book selected to delete.");
            return;
        }

        // Show a confirmation dialog before deleting.
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("You are about to delete the book: " + bookToDelete.getTitle());
        confirmationAlert.setContentText("This action cannot be undone. Are you sure?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            this.bookDAO.delete(bookToDelete.getId());
            loadBooks();
            clearFormAction();
        }
    }
}