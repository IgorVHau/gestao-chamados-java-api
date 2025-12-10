# Service Desk API

API REST para gerenciamento de chamados, desenvolvida com Spring Boot 3 e Java 17.
O projeto aplica boas práticas de arquitetura, segurança, validação e documentação.

## 📌 Funcionalidades

- ✅ Cadastro, listagem, atualização e exclusão de chamados
- ✅ Validação de dados com Jakarta Validation
- ✅ Autenticação via JWT
- ✅ Endpoints protegidos com Spring Security
- ✅ Documentação automática com Swagger (OpenAPI 3)
- ✅ Padronização de respostas da API
- ✅ Tratamento global de exceções

## 🛠️ Tecnologias utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security
- JWT (jjwt)
- H2 Database
- Lombok
- Springdoc OpenAPI (Swagger)

## 📐 Arquitetura

O projeto segue uma arquitetura em camadas:

- **Controller** – exposição dos endpoints REST
- **Service** – regras de negócio
- **Repository** – acesso a dados via JPA
- **Model / DTO** – entidades e objetos de transporte
- **Security** – autenticação e autorização com JWT
- **Exception Handler** – tratamento centralizado de erros

## 🔐 Segurança

- Autenticação baseada em JWT
- Filtro de segurança customizado
- Integração com Swagger para autorização via token

## 📚 Documentação da API

Após iniciar a aplicação, a documentação estará disponível em:

- Swagger UI:  
  👉 http://localhost:8080/swagger-ui/index.html

- OpenAPI JSON:  
  👉 http://localhost:8080/v3/api-docs

## ▶️ Como executar o projeto

Certifique-se de ter **Java 17+** e **Maven** instalados no ambiente.

Abra um terminal (PowerShell, Terminal do Linux/macOS ou Git Bash no Windows) e execute:

```bash
git clone https://github.com/IgorVHau/gestao-chamados-java-api.git
cd gestao-chamados-java-api
mvn spring-boot:run
```

A aplicação subirá em: http://localhost:8080

� Observação
Este projeto foi desenvolvido com foco em aprendizado prático, evolução técnica e aplicação de boas práticas no ecossistema Spring.
