# studyCryptographic

Projeto de estudo em **Spring Boot** focado em criptografia e proteção de dados sensíveis. A aplicação expõe uma API REST para cadastro de clientes (com senha protegida por *hash*) e para gerenciamento de dados sensíveis (documento do usuário e token de cartão de crédito), que são **criptografados em repouso** usando o [Jasypt](http://www.jasypt.org/).

## Tecnologias

- **Java 25**
- **Spring Boot 4.1.1**
  - Spring Web
  - Spring Data JPA
  - Spring Security
- **PostgreSQL** (banco de dados)
- **Jasypt** (`jasypt-spring-boot-starter`) — criptografia simétrica de campos sensíveis
- **Lombok** — redução de boilerplate (getters/setters/construtores)
- **Maven** (com Maven Wrapper)

## Estrutura do projeto

```
src/main/java/com/cryptographic/studyCryptographic
├── StudyCryptographicApplication.java   # Classe principal (bootstrap Spring Boot)
├── config/
│   └── SecurityConfig.java              # Configuração do Spring Security (BCrypt, filtros)
├── controllers/
│   ├── ClientController.java            # Endpoints de cadastro/autenticação de clientes
│   └── ControllerData.java              # Endpoints de dados sensíveis (CRUD)
├── dtos/
│   ├── AuthenticationDto.java
│   ├── ClientResponseDto.java
│   ├── RegisterDto.java
│   └── SensitiveDataDto.java
├── entities/
│   ├── client/
│   │   ├── AcessLevel.java              # Enum de nível de acesso (PREMIUM, COMMON)
│   │   └── ClientEntity.java            # Entidade Client (implementa UserDetails)
│   └── sensitiveData/
│       └── SensitiveDataEntity.java     # Entidade de dados sensíveis
├── repositories/
│   ├── ClientRepository.java
│   └── SensitiveDataRepository.java
└── services/
    ├── ClientService.java               # Regras de negócio de cadastro de clientes
    ├── EncryptionService.java           # Wrapper em torno do StringEncryptor (Jasypt)
    └── SensitiveDataService.java        # Regras de negócio de dados sensíveis (criptografa/descriptografa)
```

## Como funciona

- **Senhas de clientes** são armazenadas com hash `BCrypt` (via `PasswordEncoder`), nunca em texto puro.
- **Dados sensíveis** (`userDocument` e `creditCardToken`) são criptografados pelo `EncryptionService` antes de serem persistidos no banco, e descriptografados ao serem lidos, usando o `StringEncryptor` do Jasypt.
- A chave usada pelo Jasypt é configurada pela propriedade `jasypt.encryptor.password`, que por padrão lê a variável de ambiente `JASYPT_ENCRYPTOR_PASSWORD`.

## Endpoints da API

### Clientes — `/auth`

| Método | Rota            | Descrição                              |
|--------|-----------------|-----------------------------------------|
| POST   | `/auth/register` | Cadastra um novo cliente (login, senha, nível de acesso) |

### Dados sensíveis — `/sensitive-data`

| Método | Rota                          | Descrição                          |
|--------|-------------------------------|-------------------------------------|
| POST   | `/sensitive-data`              | Cria um novo registro de dado sensível |
| GET    | `/sensitive-data/{id}`         | Retorna um dado sensível pelo id (descriptografado) |
| PUT    | `/sensitive-data/{id}`         | Atualiza um dado sensível existente |
| DELETE | `/sensitive-data/{id}`         | Remove um dado sensível             |

> **Observação:** na configuração atual do `SecurityConfig`, todas as requisições estão liberadas (`permitAll`) e o CSRF está desabilitado — adequado para estudo/desenvolvimento local, mas deve ser revisto antes de qualquer uso em produção.

## Pré-requisitos

- JDK 25
- PostgreSQL em execução localmente (ou acessível pela rede)
- Maven (opcional — o projeto inclui o Maven Wrapper `mvnw` / `mvnw.cmd`)

## Configuração

As configurações da aplicação ficam em `src/main/resources/application.properties`:

```properties
spring.application.name=studyCryptographic

# --- Banco de dados (Postgres) ---
spring.datasource.url=jdbc:postgresql://localhost:5432/study_cryptographic_db
spring.datasource.username=studyCryptographic
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# --- Jasypt (usado pelo EncryptionService) ---
jasypt.encryptor.password=${JASYPT_ENCRYPTOR_PASSWORD:changeme}
```

Antes de rodar o projeto:

1. Crie o banco de dados `study_cryptographic_db` no PostgreSQL (ou ajuste a URL/usuário/senha conforme seu ambiente).
2. Defina a variável de ambiente `JASYPT_ENCRYPTOR_PASSWORD` com um valor seguro — **não deixe o valor padrão (`changeme`) em produção**.

## Como executar

Usando o Maven Wrapper:

```bash
# Linux/macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

Ou, definindo a variável de ambiente antes de subir a aplicação:

```bash
export JASYPT_ENCRYPTOR_PASSWORD=minha-chave-secreta
./mvnw spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`.

## Testes

```bash
./mvnw test
```

## Status

Projeto de estudo — em desenvolvimento, sem autenticação/autorização de fato aplicada nos endpoints ainda (todas as rotas estão públicas) e sem tratamento de exceções customizado (erros de negócio lançam `RuntimeException`).

## Fotos 
<img width="331" height="320" alt="Screenshot_20260911_165218" src="https://github.com/user-attachments/assets/d2c1b494-404f-44b7-99ae-295e430338a1" />
<img width="329" height="320" alt="Screenshot_20260911_165200" src="https://github.com/user-attachments/assets/1ecfda8d-eaa7-4e1c-a61b-457f1ad075e2" />
<img width="575" height="45" alt="Screenshot_20260911_164939" src="https://github.com/user-attachments/assets/bf00ed0d-b9d8-4aea-a873-660f1cda6b16" />
<img width="655" height="86" alt="Screenshot_20260911_164915" src="https://github.com/user-attachments/assets/43cde388-912a-467a-84a7-c949f4b90091" />



