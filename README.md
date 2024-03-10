# API DiogoWeb

API **RESTful** responsável por prover os dados e regras de negócio da plataforma **DiogoWeb**.  
Construída com **Java** e **Spring Boot**, a API gerencia a comunicação com o banco de dados, aplica as regras do sistema escolar e expõe **endpoints seguros** que são consumidos pela aplicação web.

A autenticação é baseada em **JWT (JSON Web Token)** utilizando **Spring Security**, garantindo que cada tipo de usuário acesse apenas os recursos permitidos pelo seu **perfil (PROFILE)**.

---

# 🚀 Tecnologias e Bibliotecas

A API foi construída utilizando as seguintes tecnologias:

- **Java 17+** – Linguagem principal da aplicação.
- **Spring Boot** – Framework para criação de aplicações Java robustas e escaláveis.
- **Spring Web** – Construção de APIs REST.
- **Spring Security** – Autenticação e autorização baseada em JWT.
- **Spring Data JPA** – Abstração para acesso ao banco de dados.
- **Hibernate** – Implementação ORM para persistência de dados.
- **JWT (JSON Web Token)** – Autenticação stateless.
- **Maven** – Gerenciamento de dependências e build do projeto.
- **Banco de Dados Relacional** – Persistência das entidades da aplicação.

---

# 📂 Estrutura do Projeto

A arquitetura da API segue uma separação clara de responsabilidades:

```text
src/
├── config/           # Configurações gerais da aplicação (CORS, Security, JWT, etc)
├── controllers/      # Camada responsável por expor os endpoints REST
├── services/         # Implementação das regras de negócio
├── repositories/     # Interfaces do Spring Data JPA para acesso ao banco
├── models/           # Entidades do banco de dados
├── dto/              # Objetos de transferência de dados (requisições e respostas)
├── enums/            # Enumerações utilizadas no sistema
├── security/         # Configurações de autenticação e filtros JWT
├── exceptions/       # Tratamento global de exceções
└── Application.java  # Classe principal da aplicação