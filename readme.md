# MaryBankins

Aplicação bancária desenvolvida em **Kotlin** com **Spring Boot** para gestão de utilizadores, contas bancárias e transações financeiras.

## Funcionalidades

### Gestão de Utilizadores

- Criar utilizadores
- Consultar utilizadores
- Atualizar utilizadores
- Remover utilizadores

### Gestão de Contas Bancárias

- Criar contas bancárias
- Associar contas a utilizadores
- Consultar saldo
- Consultar informação de contas

### Gestão de Transações

- Depósitos
- Levantamentos
- Transferências entre contas
- Histórico de transações

---

## Stack Tecnológica

- Kotlin
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker
- Gradle

---

## Requisitos

- Java 21+
- Docker


---

## Inicializar a Base de Dados

A infraestrutura PostgreSQL encontra-se na pasta `docker`.

Para iniciar a base de dados:

```bash
cd docker
docker compose up 
```

Desenvolvido com ❤️ utilizando Kotlin e Spring Boot.