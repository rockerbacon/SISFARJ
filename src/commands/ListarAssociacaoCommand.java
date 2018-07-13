package commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Associacao;
import database.DbConnection;
import receivers.Secretario;

public class ListarAssociacaoCommand implements Command {
	
	private Secretario receiver;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection con;
	
	public ListarAssociacaoCommand(HttpServletRequest request, HttpServletResponse response) {
		String errMsg = null;
		try {		
			
			this.con = DbConnection.connect();			
			this.receiver = new Secretario(con);
			this.request = request;
			this.response = response;
			
		}catch (SQLException e) {
			errMsg = "Nao foi possivel conectar ao banco de dados";
		} finally {
			try {
				if (errMsg != null) {
					request.setAttribute("errorMsg", errMsg);
					request.getRequestDispatcher("/error.jsp").forward(request, response);
				}
			} catch (IOException|ServletException e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void execute() {
		List<Associacao> callback = null;		
		try {
			callback = this.receiver.listarAssociacao();
			if (callback != null) {
				//credenciais = callback.substring(new String("SUCCESS ").length());
				for(int i=0;i<callback.size();i++){
				    System.out.println(callback.get(i));
				} 
				request.setAttribute("lista", callback);
				request.getRequestDispatcher("/listarAssociacao.jsp").forward(request, response);
			} else {
				request.setAttribute("errorMsg", callback);
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				
			}
		} catch  (ServletException|IOException e) {
			e.printStackTrace();
			try {
				request.setAttribute("errorMsg", "Erro inesperado no servidor");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			} catch (ServletException|IOException e2) {
				e.printStackTrace();
			}
		} finally {
			try {
				if (this.con != null) this.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
