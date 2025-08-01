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
        // Instancia nosso DAO
        this.loanDAO = new LoanDAO();

        // Configura as colunas da tabela
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("loanDate"));

        // Carrega os dados do relatório na tabela
        loadActiveLoans();
    }

    // Método que busca os dados no DAO e atualiza a tabela
    private void loadActiveLoans() {
        reportTable.getItems().clear();
        // Chama o novo método que filtra apenas os empréstimos ativos
        List<Loan> activeLoans = this.loanDAO.findAllActive();
        reportTable.getItems().addAll(activeLoans);
    }
}