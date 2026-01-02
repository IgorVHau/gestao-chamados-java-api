<h1 align="center">Service Desk API</h1>

<p align="center">API REST para gerenciamento de chamados, desenvolvida com <b>Java 17</b> e <b>Spring Boot 3</b>, aplicando boas práticas de arquitetura, segurança e testes.</p>

<p align="center">
	<!--<img src="https://img.shields.io/badge/Java-17-red">-->
	<img src="https://img.shields.io/badge/Java-17-red?labelColor=blue"/>
	<img src="https://img.shields.io/badge/Spring_Boot-3.x-brightgreen">
</p>
<p align="center">
	<img src="https://img.shields.io/badge/Status-Em_desenvolvimento-yellow">
</p>

<br>

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

- **[Controller](src/main/java/service_desk_api/api/controller)** – exposição dos endpoints REST e validação de entrada
- **[Service](src/main/java/service_desk_api/api/service)** – regras de negócio e orquestração
- **[Repository](src/main/java/service_desk_api/api/repository)** – acesso a dados via JPA
- **[Model](src/main/java/service_desk_api/api/model) / [DTO](src/main/java/service_desk_api/api/dto)** – entidades de domínio e objetos de transporte
- **[Config](src/main/java/service_desk_api/api/config)** - configurações da aplicação (segurança, OpenAPI, filtros, beans)
- **[Exception Handler](src/main/java/service_desk_api/api/exception)** – tratamento centralizado de erros e respostas padronizadas

A segurança (JWT, autenticação e autorização) é tratada de forma transversal, principalmente nas camadas de configuração e serviço.

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

## 🔓 Autenticação e Segurança

A API utiliza autenticação baseada em **JWT (JSON Web Token)** para proteger seus endpoints, com filtro de segurança customizado e integração com Swagger para autorização via token.

Antes de realizar alguma operação na API, é necessário autenticar o usuário por meio de login e senha. Caso contrário, todas as operações serão bloqueadas.

⚠️ **Atenção**: as credenciais abaixo são fictícias e utilizadas apenas para fins de teste local.

| Usuário | E-mail | Senha | Perfil |
|:-------:|:-------:|:-------:|:-------:|
| Jorge | user@email.com | 654321 | USER |
| Fernando | admin@email.com | 123456 | ADMIN |

---------------------------------------------------------------
O fluxo de autenticação pode ser descrito da seguinte forma:

1. Realize uma requisição **POST** para `/auth/login`
2. Envie no body um JSON contendo os campos `"email"` e `"senha"` preenchendo os valores de acordo com as informações fornecidas na tabela acima
3. Após autenticação bem-sucedida, a API retornará um **token JWT**
4. Ao realizar uma chamada na API, utilize o token no header `Authorization` com o prefixo `Bearer`


🕐 O token possui tempo de expiração configurado para ser válido por 1 hora. Após esse período, é necessário realizar uma nova autenticação para obter um novo token.

## 📲 Endpoints principais

Abaixo estão as informações necessárias para a realização de cada requisição. Todos os endpoints abaixo são protegidos e exigem autenticação conforme descrito na seção Autenticação e Segurança.

🟡 ***Ler todos os chamados registrados***

- **URL:** `/chamados`
- **HTTP Method:** `GET`
- **Authorization:** `USER, ADMIN`

🟡 ***Ler o chamado correspondente ao id informado***

- **URL:** `/chamados/{id}`
- **HTTP Method:** `GET`
- **Authorization:** `USER, ADMIN`

🟢 ***Criar chamado para ser registrado no banco***

- **URL:** `/chamados`
- **HTTP Method:** `POST`
- **Authorization:** `ADMIN`
- **Content-Type:** `application/json`
- **Request body (exemplo):**
```json
	{
	"titulo": "TÍTULO",
	"descricao": "DESCRIÇÃO",
	"status": "ABERTO"
	}
```

🔵 ***Editar chamado correspondente ao id informado***
- **URL:** `/chamados/{id}`
- **HTTP Method:** `PUT`
- **Authorization:** `ADMIN`
- **Content-Type:** `application/json`
- **Request body (exemplo):**
```json
	{
	"titulo": "TÍTULO",
	"descricao": "DESCRIÇÃO",
	"status": "EM_ANDAMENTO"
	}
```

🔴 ***Remover chamado correspondente ao id informado***
- **URL:** `/chamados/{id}`
- **HTTP Method:** `DELETE`
- **Authorization:** `ADMIN`

> 📘 Para exemplos completos de requisições e respostas, utilize o Swagger UI disponível em `/swagger-ui/index.html`.

###### ⚠️ Regras e validações importantes
- Os campos `"titulo"`, `"descrição"` e `"status"` são obrigatórios nos métodos POST e PUT
- O campo `"status"` só aceita os valores: `"ABERTO"`, `"EM_ANDAMENTO"` e `"CONCLUIDO"`
- Chamados com status `"CONCLUIDO"` não podem ser atualizados



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