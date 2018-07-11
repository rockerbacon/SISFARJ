package commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import receivers.ValidationReceiver;

public class ValidationCommand implements Command {
	
	private int matricula;
	private String senha;
	private ValidationReceiver receiver;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public ValidationCommand(HttpServletRequest request, HttpServletResponse response) {
		try {
			this.matricula = Integer.parseInt(request.getParameter("matricula"));
			this.senha = request.getParameter("senha");
			this.receiver = new ValidationReceiver();
			this.request = request;
			this.response = response;
		} catch (NumberFormatException e) {
			try {
				request.setAttribute("errorMsg", "Matricula deve conter somente numeros");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			} catch (IOException|ServletException e2) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void execute() {
		try {
			if (this.receiver.validate(this.matricula, this.senha)) {
				String proxPag = (String)this.request.getSession().getAttribute("afterLogin");
				this.request.getRequestDispatcher(proxPag).forward(this.request, this.response);
			} else {
				this.request.setAttribute("errorMsg", "Matricula ou senha invalidos");
				this.request.getRequestDispatcher("/error.jsp").forward(this.request, this.response);
			}
		} catch (IOException|ServletException e) {
			e.printStackTrace();
		}
	}
	
}
