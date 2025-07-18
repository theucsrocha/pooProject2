package br.com.matheus.bookstore.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory{
    private static final String URL = "jdbc:mariadb://localhost:3307/bookstore";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        try {
            // Tenta estabelecer a conexão usando o driver que o Maven baixou
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Se falhar, lança uma exceção para que o resto do programa saiba do erro
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

}
