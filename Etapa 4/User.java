/**
    Classe User
    Responsável por gerenciar a conexão com o banco de dados 
    e verificar as credenciais de login do usuário.
**/

package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {    
    
    /** 
        Método para estabelecer conexão com o banco de dados.
        Retorna Objeto Connection se a conexão for bem-sucedida, ou null caso contrário.
    **/
    public Connection conectarBD() {
        Connection conn = null; // Declara uma variável de conexão como nula inicialmente.
        try {
            // Carrega o driver do MySQL. OBS: Classe especificada está incorreta.
            Class.forName("com.mysql.Driver.Manager").newInstance();
            
            // Define a URL do banco de dados com credenciais embutidas.
            String url = "jdbc:mysql://127.0.0.1/test?user=lopes&password=123";
            
            // Estabelece a conexão com o banco de dados.
            conn = DriverManager.getConnection(url);
        } catch (Exception e) { 
            // Exceções não são tratadas, o que dificulta a depuração.
        }
        return conn; // Retorna a conexão estabelecida ou null.
    }
    
    public String nome = ""; // Variável para armazenar o nome do usuário.
    public boolean result = false; // Indica se o login foi bem-sucedido.

    /**
        Método para verificar as credenciais do usuário.
        Login fornecido pelo usuário.
        Senha fornecida pelo usuário.
        Retorna True se o login for válido, false caso contrário.
    */
    public boolean verificarUsuario(String login, String senha) {
        String sql = ""; // Inicializa a variável para construir a consulta SQL.
        
        // Estabelece a conexão com o banco de dados.
        Connection conn = conectarBD();
        
        // Constrói a instrução SQL para buscar o nome do usuário com as credenciais fornecidas.
        sql += "select nome from usuarios ";
        sql += "where login = '" + login + "'"; // Vulnerável a injeção de SQL.
        sql += " and senha = '" + senha + "';"; 
        
        try {
            // Cria um objeto Statement para executar a consulta SQL.
            Statement st = conn.createStatement();
            
            // Executa a consulta e armazena o resultado.
            ResultSet rs = st.executeQuery(sql); 
            
            // Verifica se a consulta retornou algum resultado.
            if (rs.next()) {
                result = true; // Indica que o login foi bem-sucedido.
                nome = rs.getString("nome"); // Armazena o nome do usuário.
            }
        } catch (Exception e) { 
            // Nenhum tratamento de erro, o que dificulta a identificação de problemas.
        }
        
        return result; // Retorna o resultado da verificação do login.
    }
} // Fim da classe User.
