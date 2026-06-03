🛒 E-Commerce
📌 Descrição

Este projeto consiste em um sistema de e-commerce desenvolvido em Java, permitindo o cadastro e gerenciamento de produtos, clientes e pedidos. O objetivo é aplicar conceitos de desenvolvimento de software, banco de dados e arquitetura de sistemas.

🎯 Funcionalidades
Cadastro de produtos
Listagem de produtos
Cadastro de clientes
Realização de pedidos
Upload de fotos dos produtos
Consulta de pedidos
Paginação na listagem de produtos
Requisitos Implementados


1. Separação de Interesses (SoC)

O sistema foi dividido em diferentes partes, cada uma com uma responsabilidade específica:

Interface do usuário
Regras de negócio
Acesso ao banco de dados

Essa separação torna o código mais organizado e fácil de manter.


2. Persistência em Banco de Dados

As informações de produtos, clientes e pedidos são armazenadas em um banco de dados, garantindo que os dados permaneçam salvos mesmo após o encerramento da aplicação.


3. Pool de Conexões

O sistema utiliza um pool de conexões para reutilizar conexões com o banco de dados, melhorando o desempenho e reduzindo o tempo necessário para acessar os dados.


4. Arquitetura em Camadas

A aplicação segue uma arquitetura em camadas:

Interface
   ↓
Serviços
   ↓
Banco de Dados

Cada camada possui uma função específica, facilitando a organização do projeto.


5. Gerenciamento de Transações

Operações importantes, como a realização de pedidos, utilizam transações para garantir a integridade dos dados.

Exemplo:

Atualizar estoque
Registrar pedido

Se alguma etapa falhar, todas as alterações são canceladas.


6. Paginação de Resultados

A listagem de produtos é exibida em páginas, evitando carregar todos os registros de uma vez.

Exemplo:

Página 1 → Produtos 1 a 10
Página 2 → Produtos 11 a 20
Página 3 → Produtos 21 a 30


7. Upload de Arquivos (Foto)

O sistema permite que uma imagem seja enviada durante o cadastro de um produto.

Exemplo:

Nome do produto
Descrição
Preço
Foto do produto

As imagens ficam armazenadas para serem exibidas aos clientes.
