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
- Spring Boot Actuator
- JWT (jjwt)
- H2 Database
- Lombok
- Springdoc OpenAPI (Swagger)
- JUnit 5
- Mockito

## 🧪 Testes

O projeto possui testes automatizados utilizando **JUnit 5** e **Mockito**, cobrindo diferentes camadas da aplicação.

Foram implementados 2 tipos de testes, [os testes unitários de serviço](src/test/java/service_desk_api/api/service/ChamadoServiceTest.java) e [testes de camada Web](src/test/java/service_desk_api/api/controller/ChamadoControllerTest.java).
Os testes unitários de serviço validam regras de negócio de forma isolada com dependências mockadas. Os testes de camada Web (Controller) utilizam anotações `WebMvcTest` e `MockMvc` para validação de estrutura das respostas JSON, status HTTP, tratamento de exceções e contratos dos endpoints.

Para executar todos os testes automatizados, execute o comando:

```bash
mvn test
```

## 📐 Arquitetura

O projeto segue uma arquitetura em camadas:

- **[Controller](src/main/java/service_desk_api/api/controller)** – exposição dos endpoints REST
- **[Service](src/main/java/service_desk_api/api/service)** – regras de negócio
- **[Repository](src/main/java/service_desk_api/api/repository)** – acesso a dados via JPA
- **[Model](src/main/java/service_desk_api/api/model) / [DTO](src/main/java/service_desk_api/api/dto)** – entidades e objetos de transporte
- **[Security](src/main/java/service_desk_api/api/security)** – autenticação e autorização com JWT
- **[Config](src/main/java/service_desk_api/api/config)** - configurações da aplicação (segurança, OpenAPI, beans)
- **[Exception Handler](src/main/java/service_desk_api/api/exception)** – tratamento centralizado de erros

## ▶️ Como executar o projeto

Certifique-se de ter **Java 17+** e **Maven** instalados no ambiente.

Abra um terminal (PowerShell, Terminal do Linux/macOS ou Git Bash no Windows) e execute:

```bash
git clone https://github.com/IgorVHau/gestao-chamados-java-api.git
cd gestao-chamados-java-api
mvn spring-boot:run
```

A aplicação subirá em: 

```bash 
http://localhost:8080
```

## 📚 Documentação da API

Após iniciar a aplicação, a documentação estará disponível em:

- Swagger UI:  
```bash
http://localhost:8080/swagger-ui/index.html
```

- OpenAPI JSON:  
```bash
http://localhost:8080/v3/api-docs
```

## 🔐 Segurança

- Autenticação baseada em JWT
- Filtro de segurança customizado
- Integração com Swagger para autorização via token

## 🔓 Autenticação

Antes de realizar alguma operação na API, é necessário autenticar o usuário por meio de login e senha. Caso contrário, todas as operações serão bloqueadas.

⚠️ **Atenção**: as credenciais abaixo são fictícias e utilizadas apenas para fins de teste local.

| Usuário | E-mail | Senha | Perfil |
|:-------:|:-------:|:-------:|:-------:|
| Jorge | user@email.com | 654321 | USER |
| Fernando | admin@email.com | 123456 | ADMIN |

---------------------------------------------------------------
O fluxo de autenticação pode ser descrito da seguinte forma:

1. Prepare uma autenticação POST para a rota /auth/login
2. No body, prepare um JSON com os campos "email" e "senha" preenchendo os valores de acordo com as informações fornecidas na tabela acima
3. Envie a requisição
4. Copie o token JWT retornado
5. Use esse token no header Authorization com o prefixo Bearer para realizar chamadas nas requisições da API

🕐 O token possui tempo de expiração configurado para ser válido por 1 hora. Após esse período, é necessário realizar uma nova autenticação para obter um novo token.

## 📲 Endpoints principais

Abaixo os métodos HTTP e rotas para realização das chamadas. Todos os endpoints abaixo exigem autenticação JWT.

🔑 Legenda de permissões
- 🔓 USER

- 🔐 ADMIN

🟡 **GET /chamados** 🔓🔐

Objetivo: Ler todos os chamados registrados.

🟡 **GET /chamados/{id}** 🔓🔐

Objetivo: Ler o chamado correspondente ao id selecionado.

🟢 **POST /chamados** 🔐

Objetivo: Criar um chamado para ser registrado no banco de dados. 

Exemplo:
_Body (JSON)_
```json
{
	"titulo": "Acesso criado para Jonas",
	"descricao": "Jonas recebeu acesso ao e-mail newUser@email.com.",
	"status": "ABERTO"
}
```

🔵 **PUT /chamados/{id}** 🔐

Objetivo: Editar um ou mais campos do chamado correspondente ao id selecionado.

Exemplo:
_Body (JSON)_
```json
{
	"status": "FECHADO"
}
```

🔴 **DELETE /chamados/{id}** 🔐

Objetivo: Remover o chamado no banco de dados.

## 🔬 Monitoramento e Observabilidade

A aplicação utiliza **Spring Boot Actuator** para expor informações operacionais e de build. Para monitorar a aplicação por meio dessa ferramenta, verifique as informações abaixo.

| Método HTTP | Endpoint | Permissão |
|:----------:|:----------|:----------|
|🟡GET|/actuator/info|ADMIN🔐|

Exemplo de informações expostas:
- Nome e versão da aplicação
- Dados de build (artifact, versão, data)
- Metadados do Git (branch, commit, timestamp)

Essas informações são acessadas através do [pom.xml](pom.xml) e do repositório Git.

## 🗂️ Perfis de ambiente

O projeto possui perfis de ambientes (dev e prod) para simular comportamentos diferentes entre desenvolvimento e produção. 
No perfil [dev](src/main/resources/application-dev.yml), a aplicação faz uso de banco H2 em memória, logs SQL habilitados e schema criado automaticamente. O foco é mais voltado para desenvolvimento local.
No perfil [prod](src/main/resources/application-prod.yml), as configurações estão preparadas para banco externo (PostgreSQL), validação de schema e credenciais via variáveis de ambiente. O ambiente é pensado para simular execução em ambientes produtivos.

## 📌 Observação
Este projeto foi desenvolvido com foco em aprendizado prático, evolução técnica e aplicação de boas práticas no ecossistema Spring.