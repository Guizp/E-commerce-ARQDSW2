package edu.ifsp.loja.controllers.auth;

import java.io.IOException;

import edu.ifsp.loja.service.AuthService;
import edu.ifsp.loja.util.ViewHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cadastro")
public class CadastroController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("form", new CadastroForm());
		ViewHelper.forward("auth/cadastro.jsp", request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CadastroForm form = new CadastroForm();
		form.setNome(request.getParameter("nome"));
		form.setEmail(request.getParameter("email"));
		form.setSenha(request.getParameter("senha"));

		AuthService service = new AuthService();

		try {
			service.cadastrar(form);
		} catch (IllegalArgumentException e) {
			request.setAttribute("erro", e.getMessage());
			request.setAttribute("form", form);
			ViewHelper.forward("auth/cadastro.jsp", request, response);
			return;
		}

		request.setAttribute("sucesso", "Cadastro realizado com sucesso! Faça login para continuar.");
		ViewHelper.forward("auth/login.jsp", request, response);
	}
}
