package br.edu.ifba.inf008.plugins.reports.controller;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifba.inf008.plugins.common.dao.LoanDAO;
import br.edu.ifba.inf008.plugins.common.model.Loan;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReportController {

    @FXML
    private TableView<Loan> reportTable;
    @FXML
    private TableColumn<Loan, String> bookTitleColumn;
    @FXML
    private TableColumn<Loan, String> userNameColumn;
    @FXML
    private TableColumn<Loan, LocalDate> loanDateColumn;

    private LoanDAO loanDAO;

    @FXML
    public void initialize() {
        // Instantiate our DAO
        this.loanDAO = new LoanDAO();

        // Configure the table columns
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("loanDate"));

        // Load the report data into the table
        loadActiveLoans();
    }

    // Method that fetches data from the DAO and updates the table
    private void loadActiveLoans() {
        reportTable.getItems().clear();
        // Calls the new method that filters only active loans
        List<Loan> activeLoans = this.loanDAO.findAllActive();
        reportTable.getItems().addAll(activeLoans);
    }

    public void refresh() {
        loadActiveLoans();
    }
}