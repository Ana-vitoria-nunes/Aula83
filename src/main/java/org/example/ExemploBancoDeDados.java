package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ExemploBancoDeDados {

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");

            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE alunos (id INT PRIMARY KEY, nome VARCHAR(50), idade INT)");

            adicionarAluno(connection, 1, "João", 20);
            adicionarAluno(connection, 2, "Maria", 22);

            listarAlunos(connection);

            removerAluno(connection, 1);

            listarAlunos(connection);

            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void adicionarAluno(Connection connection, int id, String nome, int idade) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO alunos (id, nome, idade) VALUES (?, ?, ?)");
            insertStatement.setInt(1, id);
            insertStatement.setString(2, nome);
            insertStatement.setInt(3, idade);
            insertStatement.executeUpdate();
            System.out.println("Aluno " + nome + " adicionado.");
            insertStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void removerAluno(Connection connection, int id) {
        try {
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM alunos WHERE id = ?");
            deleteStatement.setInt(1, id);
            int affectedRows = deleteStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Aluno com ID " + id + " removido.");
            } else {
                System.out.println("Nenhum aluno encontrado com ID " + id + ".");
            }
            deleteStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listarAlunos(Connection connection) {
        try {
            Statement selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery("SELECT * FROM alunos");

            System.out.println("Lista de alunos:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                int idade = resultSet.getInt("idade");
                System.out.println("ID: " + id + ", Nome: " + nome + ", Idade: " + idade);
            }

            resultSet.close();
            selectStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

