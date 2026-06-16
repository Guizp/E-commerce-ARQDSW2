<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Loja Web2</title>
	
	<style>
		/* Configurações Globais (Acessibilidade e Reset) */
		:root {
			--primary-color: #2563eb; /* Azul moderno, bom contraste */
			--primary-hover: #1d4ed8;
			--bg-color: #f8fafc;
			--text-color: #1e293b;
			--card-bg: #ffffff;
		}
		* {
			box-sizing: border-box;
			margin: 0;
			padding: 0;
		}
		body {
			font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
			background-color: var(--bg-color);
			color: var(--text-color);
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			min-height: 100vh;
			padding: 20px;
		}
		h1 {
			font-size: 2.5rem;
			margin-bottom: 2rem;
			color: var(--text-color);
			text-align: center;
		}
		/* Menu Intuitivo (Grid) */
		.menu-container {
			list-style: none;
			display: grid;
			grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
			gap: 20px;
			width: 100%;
			max-width: 600px;
		}
		/* Estilização dos Links como Cards */
		.menu-item a {
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			padding: 30px 20px;
			background-color: var(--card-bg);
			color: var(--primary-color);
			text-decoration: none;
			font-size: 1.25rem;
			font-weight: 600;
			border: 2px solid #e2e8f0;
			border-radius: 12px;
			box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
			transition: all 0.2s ease-in-out;
			text-align: center;
		}
		/* Feedback Visual (Mouse e Teclado) */
		.menu-item a:hover, 
		.menu-item a:focus {
			background-color: var(--primary-color);
			color: #ffffff;
			border-color: var(--primary-color);
			transform: translateY(-4px);
			box-shadow: 0 10px 15px -3px rgba(37, 99, 233, 0.3);
			outline: none;
		}
		/* Ícones em emoji para manter leve e intuitivo */
		.menu-item a::before {
			font-size: 2rem;
			margin-bottom: 10px;
		}
		
		.btn-clientes::before { content: "👥"; }
		.btn-produtos::before { content: "📦"; }
		/* Responsividade para telas pequenas */
		@media (max-width: 480px) {
			h1 { font-size: 2rem; }
			.menu-item a { padding: 20px; font-size: 1.1rem; }
		}
	</style>
</head>
<body>
	<h1>Loja Web2</h1>
	
	<main style="width: 100%; display: flex; justify-content: center;">
		<ul class="menu-container">
			<li class="menu-item">
				<a href="${pageContext.request.contextPath}/cliente/buscar" class="btn-clientes">
					Buscar Clientes
				</a>
			</li>
			<li class="menu-item">
				<a href="${pageContext.request.contextPath}/produto/buscar" class="btn-produtos">
					Buscar Produtos
				</a>
			</li>
		</ul>
	</main>
</body>
</html>