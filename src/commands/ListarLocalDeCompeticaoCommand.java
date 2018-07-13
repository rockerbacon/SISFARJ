package commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Associacao;
import database.DbConnection;
import database.Local;
import receivers.DiretorTecnico;
import receivers.Secretario;

public class ListarLocalDeCompeticaoCommand implements Command {

	private DiretorTecnico receiver;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection con;
	
	public ListarLocalDeCompeticaoCommand(HttpServletRequest request, HttpServletResponse response) {
		String errMsg = null;
		try {		
			
			this.con = DbConnection.connect();			
			this.receiver = new DiretorTecnico(con);
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
		List<Local> callback = null;		
		try {
			callback = this.receiver.listarLocalDeCompeticao();
			if (callback != null) {
				//credenciais = callback.substring(new String("SUCCESS ").length());
				for(int i=0;i<callback.size();i++){
				    System.out.println(callback.get(i));
				} 
				request.setAttribute("lista", callback);
				request.getRequestDispatcher("/listarLocaisDeCompeticao.jsp").forward(request, response);
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
