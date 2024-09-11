# Librys

### Descrição

O sistema web Librys é um gerenciador de biblioteca onde o usuário pode cadastrar livros, clientes e funcionários e fazer o gerenciamento do fluxo de locação, renovação e devolução.

### Índice

- [Visão Geral](#visão-geral)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Como Usar](#como-usar)

## Visão Geral

Fazendo uma análise do diagrama de arquitetura a seguir, podemos observar o fluxo do sistema começando pela requisição feita no front-end pelo usuário. Esse pedido bate no controller, que é a porta de entrada da nossa API. Antes de realizar a requisição, é feita uma validação sobre o tipo de usuário que está realizando essa demanda. Caso o usuário esteja autorizado, e dependendo do tipo de usuário, poderá ser realizado ações contempladas no service, onde está toda a regra de negócio da aplicação. Essa requisição é processada e bate no Repository, que é a interface que faz a intermediação entre o model, que são nossas entidades, com o nosso banco de dados. Feito isso, é retornado uma resposta para aquela determinada requisição. 
![Diagrama em branco](https://github.com/user-attachments/assets/e0396985-b188-44a3-a654-5341179c887d)


## Funcionalidades

Liste as principais funcionalidades do projeto:
- CRUD Funcionários
- CRUD Livros
- CRUD Clientes
- Login
- Envio de e-mail

## Tecnologias Utilizadas

As principais tecnologias ou bibliotecas usadas no projeto:

- [Java/Spring]([https://linkparadocumentacao](https://spring.io/projects/spring-framework)) c Spring Boot, Spring Security, Spring Web Service
- [MySql](https://dev.mysql.com/doc/)
- [SendGrid](https://sendgrid.com/en-us) - Plataforma de envio de e-mail
- [Docker](https://docs.docker.com/)

## Pré-requisitos

Instruções sobre como preparar o ambiente de desenvolvimento. Exemplo:

- [Java](https://docs.oracle.com/en/java/javase/17/) versão 17+
- [Docker](https://docs.docker.com/)

## Instalação

Passos para clonar e configurar o projeto localmente:

```bash
# Clone o repositório
git clone https://github.com/LibrysSystem/librys-api.git

# Instale as dependências
mvn install

# Rodar o docker
docker compose up librys-mysql

## Como usar
O primeiro acesso deve ser com o usuário padrão. Feito o login com o usuário padrão, será retornado um token que deverá ser usado em todas as requisições.
