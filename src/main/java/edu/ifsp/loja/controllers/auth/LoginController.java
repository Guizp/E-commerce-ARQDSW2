package edu.ifsp.loja.controllers.auth;

import java.io.IOException;

import edu.ifsp.loja.service.AuthService;
import edu.ifsp.loja.util.ViewHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// se ja estiver logado, manda direto para a home
		if (request.getSession().getAttribute("usuarioLogado") != null) {
			response.sendRedirect(request.getContextPath() + "/");
			return;
		}
		ViewHelper.forward("auth/login.jsp", request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");

		AuthService service = new AuthService();
		UsuarioLogado usuario = service.autenticar(email, senha);

		if (usuario == null) {
			request.setAttribute("erro", "E-mail ou senha inválidos.");
			request.setAttribute("emailDigitado", email);
			ViewHelper.forward("auth/login.jsp", request, response);
			return;
		}

		HttpSession session = request.getSession();
		session.setAttribute("usuarioLogado", usuario);

		String destino = usuario.isAdm() ? "/produto/buscar" : "/produto/catalogo";
		response.sendRedirect(request.getContextPath() + destino);
	}
}
