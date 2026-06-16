# E-Commerce — (Desenvolvimento de Software para Web)

Sistema de e-commerce de loja única desenvolvido em Java, como projeto final da disciplina de Desenvolvimento de Software para Web (ADS — IFSP Campus Araraquara).

---

## Tecnologias utilizadas

- **Java EE / Jakarta EE** (Servlets, JSP)
- **Apache Tomcat 10.1.44**
- **MySQL** + **MySQL Workbench**
- **JDBC** com pool de conexões via **JNDI (DataSource)**
- **Apache NetBeans IDE 28**
- **HTML5 / CSS3** (layout próprio, sem frameworks externos)

---

## Arquitetura

O projeto aplica o princípio de **Separação de Interesses (SoC)** com arquitetura em 3 camadas, seguindo os padrões **Page Controller**, **Service Layer**, **DAO** e **DTO**:

```
┌─────────────────────────────────────┐
│           VIEW (JSP)                │
│  páginas em WEB-INF/pages/          │
├─────────────────────────────────────┤
│        CONTROLLERS (Servlets)       │
│  edu.ifsp.loja.controllers.*        │
│  Form → Service → forward/redirect  │
├─────────────────────────────────────┤
│         SERVICE LAYER               │
│  edu.ifsp.loja.service.*            │
│  Regras de negócio, monta DTOs      │
├─────────────────────────────────────┤
│       PERSISTÊNCIA (DAO + JDBC)     │
│  edu.ifsp.loja.persistencia.*       │
│  SQL via PreparedStatement           │
├─────────────────────────────────────┤
│         MODELO (Entidades)          │
│  edu.ifsp.loja.modelo.*             │
└─────────────────────────────────────┘
```

---

## Estrutura de pacotes

```
src/main/java/edu/ifsp/loja/
│
├── modelo/
│   ├── Produto.java
│   └── Cliente.java
│
├── persistencia/
│   ├── DatabaseConnector.java   ← pool JNDI
│   ├── DataAccessException.java
│   ├── ProdutoDAO.java
│   └── ClienteDAO.java
│
├── service/
│   ├── ProdutoService.java
│   └── ClienteService.java
│
├── controllers/
│   ├── produto/
│   │   ├── BuscarProdutoController.java
│   │   ├── CadastrarProdutoController.java
│   │   ├── EditarProdutoController.java
│   │   ├── ExcluirProdutoController.java
│   │   ├── BuscarProdutoForm.java
│   │   ├── ProdutoForm.java
│   │   └── ProdutoDTO.java
│   └── cliente/
│       ├── BuscarClienteController.java
│       ├── CadastrarClienteController.java
│       ├── EditarClienteController.java
│       ├── ExcluirClienteController.java
│       ├── BuscarClienteForm.java
│       ├── ClienteForm.java
│       └── ClienteDTO.java
│
└── util/
    ├── StringUtil.java
    ├── ViewHelper.java
    └── FileUploadUtil.java

web/
├── index.jsp                        ← página inicial
├── css/
│   └── style.css                    ← estilo global
├── uploads/
│   └── produtos/                    ← fotos enviadas (gerado em runtime)
└── WEB-INF/
    ├── web.xml
    ├── includes/
    │   ├── header.jspf              ← cabeçalho compartilhado
    │   └── footer.jspf              ← rodapé compartilhado
    └── pages/
        ├── produto/
        │   ├── buscarProduto.jsp
        │   └── formProduto.jsp
        └── cliente/
            ├── buscarCliente.jsp
            └── formCliente.jsp

sql/
├── 01-database.sql                  ← criação do banco e tabelas
└── 02-add-foto-produto.sql          ← migration: coluna foto
```

---

## Configuração e instalação

### Pré-requisitos

- JDK 25
- Apache Tomcat 10.1.44
- MySQL 8+ (porta padrão 3306)
- MySQL Workbench
- Apache NetBeans IDE

### 1. Banco de dados

No MySQL Workbench, execute os scripts na ordem:

```sql
-- 1. Criação do banco e tabelas
source sql/01-database.sql

-- 2. Migration: adicionar coluna foto em produto
source sql/02-add-foto-produto.sql
```

### 2. Driver MySQL no Tomcat

Copie o arquivo `mysql-connector-j-*.jar` para dentro da pasta `lib/` do Tomcat:

```
apache-tomcat-10.1.44/lib/mysql-connector-j-9.6.0.jar
```

### 3. Pool de conexões (JNDI)

O arquivo `web/META-INF/context.xml` já está configurado. Ajuste usuário, senha e porta conforme sua instalação local:

```xml
<Resource name="jdbc/loja"
          auth="Container"
          type="javax.sql.DataSource"
          driverClassName="com.mysql.cj.jdbc.Driver"
          url="jdbc:mysql://localhost:3306/loja"
          username="root"
          password="root"
          maxTotal="20"
          maxIdle="10"
          maxWaitMillis="-1"/>
```

### 4. Executar o projeto

No NetBeans:
1. Clique com botão direito no projeto → **Clean and Build**
2. **Run** (ou F6)
3. Acesse: `http://localhost:8080/E-commerce/`

---

## Funcionalidades implementadas

### Produtos
- Busca por descrição e faixa de preço, com **paginação** (requisito ✓)
- Cadastrar produto com **upload de foto** (requisito ✓)
- Editar produto (substitui foto se uma nova for enviada)
- Excluir produto (remove o arquivo de foto do disco automaticamente)

### Clientes
- Busca por nome com filtro de status (ativo/inativo)
- Cadastrar, editar e excluir clientes

---

## Requisitos atendidos até o momento

| Requisito                      | Status | Onde |
|---|---|---|
| Separação de Interesses (SoC)  |  ✅   | Arquitetura em camadas (controller / service / DAO / modelo / view) |
| Persistência em banco de dados |  ✅   | MySQL via JDBC com `PreparedStatement` |
| Pool de conexões               |  ✅   | `DatabaseConnector` via JNDI (`DataSource`) configurado no Tomcat |
| Arquitetura em camadas         |  ✅   | Page Controller, Service Layer, DAO, DTO |
| Paginação de resultados        |  ✅   | Busca de produtos (`LIMIT ?, ?` com controles de navegação) |
| Upload de arquivos (foto)      |  ✅   | Foto do produto salva em disco via `FileUploadUtil` |
| Gerenciamento de transações    |  🔲   | Pendente — módulo de Pedido/Checkout |

---

## Próximos passos

- [ ] Carrinho de compras (sessão HTTP)
- [ ] Módulo de Pedido/Checkout com gerenciamento de transação JDBC (`commit`/`rollback`)
- [ ] Histórico de pedidos por cliente com paginação

---

## Equipe

Projeto desenvolvido por 3 integrantes como trabalho final da disciplina **Desenvolvimento de Software para Web ** — ADS, IFSP Campus Araraquara, 1º semestre de 2026.

Professor: Marcelo Criscuolo
Bruno Ferreira da Costa
GUILHERME Gilson Simonetti
Igor Ralha Guerreiro Gomes
