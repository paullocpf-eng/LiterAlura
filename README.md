# 📚 LiterAlura - Catálogo de Livros

O **LiterAlura** é um sistema de gerenciamento de livros que integra o consumo de uma API externa (Gutendex) com a persistência de dados relacional.
Esse projeto foi desenvolvido como parte de um desafio da formação Java da **Alura**.

## 🛠️ Funcionalidades

O sistema oferece as seguintes opções através de um menu interativo no console:

1.  **Buscar livro pelo título**: Consulta a API Gutendex, filtra o resultado e salva o livro (e seu autor) no banco de dados.
2.  **Listar livros registrados**: Exibe todos os livros que já foram salvos no MySQL.
3.  **Listar autores registrados**: Lista os autores cadastrados e os livros associados a cada um.
4.  **Listar autores vivos em determinado ano**: Utiliza consultas JPQL para filtrar autores que estavam vivos em um período específico.
5.  **Listar livros em um determinado idioma**: Permite filtrar a biblioteca local por siglas (pt, en, es, fr).

## 🚀 Tecnologias Utilizadas

* **Java 17+**: Linguagem base (utilizando Records, Streams e Optionals).
* **Spring Boot 3**: Framework para inicialização e gerenciamento da aplicação.
* **Spring Data JPA**: Para abstração da camada de persistência e criação de consultas.
* **Hibernate**: Motor de ORM (Object-Relational Mapping).
* **MySQL**: Banco de dados relacional para armazenamento dos dados.
* **Jackson**: Biblioteca para desserialização de JSON recebidos da API.
* **API Gutendex**: Fonte de dados externa para busca de livros.

## ⚙️ Configuração e Segurança

Para garantir as boas práticas de segurança, o projeto foi configurado para não expor dados sensíveis. As credenciais do banco de dados e as configurações de conexão são tratadas como **Variáveis de Ambiente**:

* As chaves de acesso e URL do banco foram configuradas diretamente no **Runner do IntelliJ IDEA** e são chamadas no `application.properties` da seguinte forma:

```properties
spring.datasource.url=jdbc:mysql://localhost:----/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
