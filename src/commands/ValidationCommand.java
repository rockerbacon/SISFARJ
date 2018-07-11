package commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import receivers.ValidationReceiver;

public class ValidationCommand implements Command {
	
	private String login;
	private String senha;
	private byte acessoNecessario;
	private ValidationReceiver receiver;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public ValidationCommand(HttpServletRequest request, HttpServletResponse response) {
		try {
			this.login = request.getParameter("login");
			this.senha = request.getParameter("senha");
			this.acessoNecessario = Byte.parseByte((String)request.getSession().getAttribute("accessLevel"));
			this.receiver = new ValidationReceiver();
			this.request = request;
			this.response = response;
		} catch (NumberFormatException e) {
			try {
				request.setAttribute("errorMsg", "Matricula deve conter somente numeros");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			} catch (IOException|ServletException e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void execute() {
		String callback;
		try {
			if ( (callback = this.receiver.validate(this.login, this.senha, this.acessoNecessario)).equals("SUCCESS") ) {
				String proxPag = (String)this.request.getSession().getAttribute("afterLogin");
				this.request.getRequestDispatcher(proxPag).forward(this.request, this.response);
			} else {
				this.request.setAttribute("errorMsg", callback);
				this.request.getRequestDispatcher("/error.jsp").forward(this.request, this.response);
			}
		} catch (IOException|ServletException e) {
			e.printStackTrace();
		}
	}
	
}
