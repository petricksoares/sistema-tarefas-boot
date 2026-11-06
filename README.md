# Sistema de Tarefas - API REST com Spring Boot

Projeto desenvolvido para a disciplina de **Desenvolvimento Back-End com Java**, do curso de Engenharia de Software do Centro Universitário Santo Agostinho (UNIFSA).

O objetivo do trabalho é criar uma primeira API REST com Spring Boot, aplicando Java e Orientação a Objetos e organizando a aplicação em camadas (Controller, Service e Repository).

## Sobre o projeto

O tema escolhido foi um **sistema de tarefas**. A API permite cadastrar tarefas e consultar todas as tarefas cadastradas. Os dados ficam guardados em uma lista em memória, ou seja, são perdidos quando a aplicação é reiniciada. O trabalho permite essa opção (sem banco de dados).

## Tecnologias utilizadas

- Java 21 ou superior (desenvolvido com o JDK 25)
- Spring Boot 4.1.2
- Spring Web
- Maven (com Maven Wrapper)
- VS Code
- Postman (para testar as requisições)

## Estrutura do projeto

```
src/main/java/com/example/demo
|
|-- DemoApplication.java
|
|-- controller
|   |-- TarefaController.java
|
|-- service
|   |-- TarefaService.java
|
|-- repository
|   |-- TarefaRepository.java
|
|-- model
    |-- Tarefa.java
```

## Como as camadas se comunicam

```
Cliente (Postman / navegador)
        |
        |  requisição HTTP
        v
  TarefaController   -> recebe a requisição e devolve a resposta
        |
        v
  TarefaService      -> concentra as regras e operações
        |
        v
  TarefaRepository   -> guarda e busca os dados (List<Tarefa>)
```

### Responsabilidade de cada classe

| Classe | Responsabilidade |
|---|---|
| `Tarefa` | Classe de domínio. Representa uma tarefa, com id, título, descrição e se está concluída. |
| `TarefaRepository` | Guarda as tarefas em uma lista em memória e gera o id de cada tarefa nova. |
| `TarefaService` | Fica entre o Controller e o Repository. Valida que o título é obrigatório antes de cadastrar. |
| `TarefaController` | Recebe as requisições HTTP, chama o Service e devolve o resultado em JSON. |
| `DemoApplication` | Classe principal, que inicia a aplicação Spring Boot. |

## Como executar

### Pré-requisitos

- JDK 21 ou superior instalado (confira com `java -version`)

### Passo a passo

1. Clone o repositório:

```
git clone https://github.com/petricksoares/sistema-tarefas-boot.git
```

2. Entre na pasta do projeto (a que contém o `pom.xml`):

```
cd sistema-tarefas-boot
```

3. Execute a aplicação:

```
.\mvnw spring-boot:run
```

No Linux ou macOS, use `./mvnw spring-boot:run`.

4. Quando aparecer `Started DemoApplication`, a API estará disponível em:

```
http://localhost:8080
```

Para parar a aplicação, use `Ctrl + C` no terminal.

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| GET | `/tarefas` | Lista todas as tarefas cadastradas |
| POST | `/tarefas` | Cadastra uma nova tarefa |

### Cadastrar uma tarefa

**Requisição:** `POST http://localhost:8080/tarefas`

Corpo (JSON):

```json
{
    "titulo": "Estudar Spring Boot",
    "descricao": "Fazer o trabalho da disciplina",
    "concluida": false
}
```

**Resposta:**

```json
{
    "id": 1,
    "titulo": "Estudar Spring Boot",
    "descricao": "Fazer o trabalho da disciplina",
    "concluida": false
}
```

### Listar as tarefas

**Requisição:** `GET http://localhost:8080/tarefas`

**Resposta:**

```json
[
    {
        "id": 1,
        "titulo": "Estudar Spring Boot",
        "descricao": "Fazer o trabalho da disciplina",
        "concluida": false
    }
]
```

Se nenhuma tarefa tiver sido cadastrada, a resposta é uma lista vazia: `[]`.

### Regra de validação

O campo `titulo` é obrigatório. Se a requisição for enviada sem título (ou com o título vazio), a tarefa não é cadastrada.

## Como testar

Os endpoints foram testados com o Postman:

1. O **GET** também pode ser testado direto no navegador, acessando `http://localhost:8080/tarefas`.
2. Para o **POST**, crie uma requisição no Postman, escolha o método POST, informe a URL `http://localhost:8080/tarefas`, abra a aba **Body**, marque **raw**, escolha o formato **JSON** e cole o corpo de exemplo.
3. Depois de cadastrar, faça um GET para conferir se a tarefa aparece na lista.

## Conceitos estudados

**O que é Spring Boot?**
É um framework Java que facilita a criação de aplicações. Ele já traz configurações prontas e um servidor embutido (Tomcat), então não é preciso configurar tudo manualmente. Com ele, é possível ter uma API rodando com poucas classes.

**Como criar um projeto Spring Boot?**
Pelo Spring Initializr, que pode ser usado no site start.spring.io ou pela extensão do VS Code. Nele se escolhem o Maven, a linguagem Java, a versão do Spring Boot e as dependências (neste projeto, apenas o Spring Web). Ele gera a estrutura inicial com o `pom.xml` e a classe principal.

**O que é uma API REST?**
É uma forma de duas aplicações se comunicarem pela internet usando o protocolo HTTP. Cada recurso (neste caso, as tarefas) tem um endereço, e as operações são feitas com os métodos HTTP, como GET para consultar e POST para cadastrar. Os dados normalmente trafegam em formato JSON.

**O que é um endpoint?**
É o endereço de uma API que executa uma ação. Por exemplo, `GET /tarefas` é um endpoint que lista as tarefas, e `POST /tarefas` é outro, que cadastra uma tarefa.

**O que é `@RestController`?**
É a anotação que marca uma classe como controller de uma API REST. Ela indica ao Spring que a classe recebe requisições HTTP e que o retorno dos métodos deve ser convertido para JSON.

**O que é `@RequestMapping`?**
Define o caminho base de um controller. Em `@RequestMapping("/tarefas")`, todos os endpoints da classe começam com `/tarefas`.

**O que é `@Service`?**
Marca a classe como parte da camada de serviço, onde ficam as regras e operações da aplicação. O Spring cria e gerencia o objeto dessa classe, e ele pode ser injetado onde for necessário.

**O que é `@Repository`?**
Marca a classe como parte da camada de acesso aos dados. Neste projeto, ela guarda as tarefas em uma lista em memória.

**Como usar `@GetMapping`?**
Liga um método do controller às requisições HTTP do tipo GET. No projeto, o método `listar()` usa `@GetMapping` para devolver todas as tarefas.

**Como usar `@PostMapping`?**
Liga um método às requisições do tipo POST. No projeto, o método `cadastrar()` usa `@PostMapping` para criar uma nova tarefa.

**Como enviar dados em uma requisição POST?**
Os dados são enviados no corpo (body) da requisição, em formato JSON. No controller, a anotação `@RequestBody` converte esse JSON em um objeto `Tarefa`, que é repassado ao Service.

**Como organizar as classes do projeto?**
Separando por responsabilidade em pacotes: `model` para as classes de domínio, `repository` para o armazenamento dos dados, `service` para as regras e `controller` para os endpoints. Assim cada camada tem uma função e fica mais fácil entender e dar manutenção.

## Possíveis melhorias

- Adicionar banco de dados (H2 ou PostgreSQL) com Spring Data JPA
- Criar endpoints para buscar por id, atualizar e excluir tarefas
- Criar um endpoint para marcar uma tarefa como concluída
- Adicionar testes automatizados

## Autor

Petrick Soares
Engenharia de Software - UNIFSA
GitHub: [petricksoares](https://github.com/petricksoares)
