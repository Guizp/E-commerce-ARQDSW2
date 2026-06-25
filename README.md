# 🛒 E-commerce IFSP

Sistema web de comércio eletrônico desenvolvido em Java para gerenciamento de produtos, clientes, carrinho de compras e pedidos. O projeto segue uma arquitetura em camadas (Controllers, Services, DAO e Model), utilizando Servlets para o processamento das requisições e JSP para a interface web.

## 📋 Sobre o Projeto

O objetivo deste sistema é simular uma loja virtual completa, permitindo que clientes realizem cadastros, naveguem pelo catálogo de produtos, adicionem itens ao carrinho e finalizem pedidos. Além disso, administradores possuem acesso a funcionalidades de gerenciamento de produtos e acompanhamento dos pedidos realizados.

## 🚀 Funcionalidades

### 👤 Autenticação e Usuários
- Cadastro de clientes
- Login e logout
- Controle de sessão
- Diferenciação de perfis (Cliente e Administrador)
- Controle de acesso por filtros

### 📦 Produtos
- Cadastro de produtos
- Edição de produtos
- Exclusão de produtos
- Busca de produtos
- Visualização do catálogo
- Controle de estoque
- Upload de imagens dos produtos

### 🛒 Carrinho de Compras
- Adicionar produtos ao carrinho
- Atualizar quantidade de itens
- Remover itens do carrinho
- Visualização do carrinho em tempo real

### 📑 Pedidos
- Finalização de compra (Checkout)
- Histórico de pedidos do cliente
- Visualização detalhada dos pedidos
- Consulta de todos os pedidos (Administrador)

### 🔍 Clientes
- Busca de clientes
- Gerenciamento de informações dos usuários

## 🏗️ Arquitetura do Projeto

O projeto está organizado em camadas para facilitar manutenção e escalabilidade.

```text
src/
├── controllers/
│   ├── auth/
│   ├── carrinho/
│   ├── cliente/
│   ├── pedido/
│   └── produto/
│
├── filtros/
│
├── modelo/
│
├── persistencia/
│
├── service/
│
└── util/
```
## 👥 Equipe

Projeto desenvolvido por 3 integrantes como trabalho final da disciplina **Desenvolvimento de Software para Web** – ADS, IFSP.

**Professor:** Marcelo Criscuolo

- Bruno Ferreira da Costa
- Guilherme Gilson Simonetti
- Igor Ralha Guerreiro Gomes
