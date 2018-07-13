package commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DbConnection;
import receivers.ValidationReceiver;

public class ValidationCommand implements Command {
	
	private String login;
	private String senha;
	private byte acessoNecessario;
	private ValidationReceiver receiver;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection con;
	
	public ValidationCommand(HttpServletRequest request, HttpServletResponse response) {
		String errMsg = null;
		try {
			con = DbConnection.connect();
			this.login = request.getParameter("login");
			this.senha = request.getParameter("senha");
			this.acessoNecessario = Byte.parseByte((String)request.getSession().getAttribute("accessLevel"));
			this.receiver = new ValidationReceiver(con);
			this.request = request;
			this.response = response;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			errMsg = "Nao foi possivel conectar a base de dados";
		} finally {
			try {
				if (errMsg != null) {
					request.setAttribute("errorMsg", errMsg);
					request.getRequestDispatcher("/error.jsp").forward(request, response);
				}
			} catch (IOException|ServletException e) {
				e.printStackTrace();
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
		} finally {
			try {
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
