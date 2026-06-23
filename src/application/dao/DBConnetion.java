package application.dao;

import java.sql.*;

public class DBConnetion {

    protected Connection conecxao;

    private String url = "jdbc:mysql://127.0.0.1:3306/ecommerce"; // caso testar na faculdade alterar a porta para 3307 e a senha do seu mySql
    private String password = "senha";                            // caso esteja em casa altere altere a porta para 3306 e a senha do seu mySql
    private String username = "root";

    protected Connection getConecxao() {
        return conecxao;
    }

    protected void setConecxao(Connection conecxao) {
        this.conecxao = conecxao;
    }

    protected void getCon() {

        try {
            setConecxao(DriverManager.getConnection(url, username, password));
            System.out.println("Connection.isValid(0) = " + conecxao.isValid(0));
        } catch (SQLException e) {
            System.out.println("ocorreu um erro :< |:" + e + ":|");
        }
    }



}