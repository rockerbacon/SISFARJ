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
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			errMsg = "Nao foi possivel conectar a base de dados";
		} finally {
			try {
				if (errMsg != null) {
					getRequest().setAttribute("errorMsg", errMsg);
					getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
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
				String proxPag = (String)this.getRequest().getSession().getAttribute("afterLogin");
				this.getRequest().getRequestDispatcher(proxPag).forward(this.getRequest(), this.getResponse());
			} else {
				this.getRequest().setAttribute("errorMsg", callback);
				this.getRequest().getRequestDispatcher("/error.jsp").forward(this.getRequest(), this.getResponse());
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
