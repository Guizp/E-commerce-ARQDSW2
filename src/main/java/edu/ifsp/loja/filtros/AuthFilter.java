package edu.ifsp.loja.filtros;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Exige que o usuario esteja autenticado para acessar qualquer rota da
 * aplicacao, exceto as listadas em ROTAS_PUBLICAS. Deve ser o primeiro
 * filtro da cadeia (urlPatterns = /*).
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

	private static final String[] ROTAS_PUBLICAS = {
		"/login",
		"/cadastro",
		"/css/",
		"/uploads/"
	};

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String path = request.getRequestURI().substring(request.getContextPath().length());

		if (isRotaPublica(path)) {
			chain.doFilter(req, res);
			return;
		}

		HttpSession session = request.getSession(false);
		boolean logado = session != null && session.getAttribute("usuarioLogado") != null;

		if (!logado) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		chain.doFilter(req, res);
	}

	private boolean isRotaPublica(String path) {
		if (path.equals("/") || path.isEmpty()) {
			return false; // home exige login
		}
		for (String rota : ROTAS_PUBLICAS) {
			if (path.startsWith(rota)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void destroy() {
	}
}
