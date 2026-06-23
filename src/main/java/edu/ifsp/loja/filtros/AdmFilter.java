package edu.ifsp.loja.filtros;

import java.io.IOException;

import edu.ifsp.loja.controllers.auth.UsuarioLogado;
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
 * Restringe rotas administrativas (cadastrar/editar/excluir produto,
 * CRUD de cliente) a usuarios com perfil "adm". Roda depois do AuthFilter,
 * portanto pode assumir que ja existe um usuarioLogado na sessao para
 * as rotas que protege.
 */
@WebFilter(urlPatterns = {
	"/produto/cadastrar",
	"/produto/editar",
	"/produto/excluir",
	"/cliente/*"
})
public class AdmFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		HttpSession session = request.getSession(false);
		UsuarioLogado usuario = session != null ? (UsuarioLogado) session.getAttribute("usuarioLogado") : null;

		if (usuario == null || !usuario.isAdm()) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso restrito ao administrador.");
			return;
		}

		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}
}
