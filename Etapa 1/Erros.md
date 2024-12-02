# **Sistema de Login de Usuário**

## Descrição
Este documento lista os problemas identificados no código original que afetam sua funcionalidade, segurança e aderência às melhores práticas de desenvolvimento.

---

## Problemas Identificados

### **1. Driver JDBC Configurado Incorretamente**
- O driver especificado no código está incorreto: `"com.mysql.Driver.Manager"`.
- A configuração correta para o MySQL é `"com.mysql.cj.jdbc.Driver"`.

---

### **2. Credenciais Expostas no Código**
- As informações de autenticação do banco de dados foram diretamente inseridas no código:
```Java
String url = "jdbc:mysql://127.0.0.1/test?user=lopes&password=123";
```
Essa abordagem é insegura, pois expõe dados confidenciais, especialmente se o código for compartilhado.

---

### **Ausência de Tratamento Apropriado de Exceções**
- As exceções capturadas não recebem tratamento nem são registradas:
```Java
} catch (Exception e) { }
``` 
  Isso dificulta a análise e solução de problemas durante a execução do programa.

---

### **4. Risco de Injeção de SQL**
- A construção de consultas SQL é feita por meio de concatenação de strings:
```Java
sql += "select nome from usuarios ";
sql += "where login = '" + login + "'";
sql += " and senha = '" + senha + "';";
``` 

---

### 5. **Falta de Fechamento de Recursos**
- Conexões (`Connection`), `Statement` e `ResultSet` não são fechados após o uso.
- Isso pode levar a **vazamentos de recursos**, prejudicando o desempenho e a estabilidade do sistema.

---

### 6. **Conexão Não Verificada**
- O código não verifica se a conexão com o banco de dados foi bem-sucedida antes de realizar operações.
- Isso pode resultar em exceções, como `NullPointerException`, se a conexão estiver nula.

---

### **Consulta SQL Difícil de Manter**
- A construção manual das consultas SQL torna o código mais complicado de atualizar em sistemas maiores.
- O uso de `PreparedStatement` é uma solução mais eficiente e segura, facilitando a manutenção e protegendo contra falhas.