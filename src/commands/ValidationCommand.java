package commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;

import database.DbConnection;
import receivers.ValidationReceiver;

public class ValidationCommand extends Command {
	
	private String login;
	private String senha;
	private byte acessoNecessario;
	private ValidationReceiver receiver;
	private Connection con;
	
	@Override
	public void init () {
		String errMsg = null;
		try {
			con = DbConnection.connect();
			this.login = getRequest().getParameter("login");
			this.senha = getRequest().getParameter("senha");
			this.acessoNecessario = Byte.parseByte((String)getRequest().getSession().getAttribute("accessLevel"));
			this.receiver = new ValidationReceiver(con);
		} catch (NullPointerException e) {
			errMsg = "Campo nao preenchido";
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			errMsg = "Nao foi possivel conectar a base de dados";
		} finally {
			try {
				if (errMsg != null) {
					getRequest().setAttribute("errorMsg", errMsg);
					getRequest().setAttribute("paginaRedirecionamento", "login.jsp");
					getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
				}
			} catch (IOException|ServletException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void execute() {
		String callback = null;
		String errMsg = null;
		try {
			if ( (callback = this.receiver.validate(this.login, this.senha, this.acessoNecessario)).equals("SUCCESS") ) {
				String proxPag = (String)getRequest().getSession().getAttribute("afterLogin");
				getRequest().getRequestDispatcher(proxPag).forward(getRequest(), getResponse());
			} else {
				errMsg = callback;
			}
		} catch (NullPointerException e) {
			errMsg = "Campo nao preenchido";
		} catch  (ServletException|IOException e) {
			errMsg = "Erro inesperado no servidor";
		} finally {
			try {
				if (con != null) con.close();
				if (errMsg != null) {
					getRequest().setAttribute("errorMsg", errMsg);
					getRequest().setAttribute("paginaRedirecionamento", "login.jsp");
					getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
				}
			} catch (SQLException|ServletException|IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
