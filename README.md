# 📝 To-Do List (Versão 2) 

API RESTful desenvolvida em **Spring Boot** para gerenciamento de tarefas.  
Permite criar, listar, atualizar e excluir tarefas, além de controlar o status de cada atividade e sugerir tarefas com base no contexto e nível de energia do usuário.

---

## Tecnologias  

- Java 17+  
- Spring Boot 3.5.6  
- Spring Web  
- Spring Data JPA  
- MySQL  
- Validation  
- Lombok  
- Swagger / OpenAPI  
- Actuator  

---

## Estrutura do Projeto  

src/main/java/com/seuprojeto/
├── controller # Endpoints da API
├── service # Regras de negócio
├── repository # Acesso ao banco (JPA)
├── model/entity # Entidades (Task, User, etc.)
└── dto # Objetos de transferência de dados

---

## 🚀 Funcionalidades

- Criar, listar, atualizar e deletar tarefas
- Filtrar tarefas por contexto (Ex: "Em casa", "No escritório")
- Sugerir a melhor tarefa com base no **nível de energia do usuário** e contexto atual
- Padronização das respostas usando DTOs
- Documentação automática com Swagger UI

---

## 📦 Como executar

1. Clone o repositório:
```bash
git clone https://github.com/PriscyllaRocha/to-do-list-api.git 
```
2. Configure o MySQL no arquivo application.properties:
```
properties
spring.datasource.url=jdbc:mysql://localhost:3306/todolist
spring.datasource.username=root
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
```
3. Rode a aplicação:
```
bash
Copiar código
mvn spring-boot:run
```
4. Acesse a documentação Swagger:
```
bash
Copiar código
http://localhost:8080/swagger-ui.html
```
---

## Desenvolvido por:

Priscylla Rocha

## Licença:

Este é um projeto para fins acadêmicos.  
Está licenciado sob a [Licença MIT](https://opensource.org/licenses/MIT).

