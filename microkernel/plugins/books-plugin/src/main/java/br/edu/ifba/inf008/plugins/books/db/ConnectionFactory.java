package br.edu.ifba.inf008.plugins.bookstore.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    // URL de conexão: aponta para o MariaDB rodando no Docker na porta 3307
    private static final String URL = "jdbc:mariadb://localhost:3307/bookstore";
    
    // Usuário do banco, definido no docker-compose.yml
    private static final String USER = "root";
    
    // Senha do banco, definida no docker-compose.yml
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        try {
            // Tenta estabelecer a conexão com os dados acima
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Se falhar, imprime o erro detalhado no console para nos ajudar a depurar
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            
            // Lança uma exceção para que o código que chamou saiba do erro.
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
}