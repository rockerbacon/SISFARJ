package commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;

import database.DbConnection;
import receivers.Secretario;

public class alterarCadastroAtletaCommand extends Command{
		private Connection con;
		String atle_nome;
		String atle_categoria;
		int atle_numero; 
		long atle_indice;
		Date atle_oficio_data;
		Date atle_associacao_data;
		Date atle_nascimento_data;
		int asso_matricula;
		Secretario receiver;
		
		
		public void AlterarCadastroAtletaCommand () {}

		@Override
		public void init() {
			String errMsg = null;
			try {
				SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
				con = DbConnection.connect();
				
				this.atle_nome = getRequest().getParameter("atleta_nome");
				this.atle_categoria = getRequest().getParameter("atleta_categoria");
				this.atle_numero = Integer.parseInt(getRequest().getParameter("atleta_numero"));
				this.atle_indice = Long.parseLong(getRequest().getParameter("atleta_indice"));
				this.atle_oficio_data = dt.parse(getRequest().getParameter("data_oficio"));
				this.atle_associacao_data = dt.parse(getRequest().getParameter("data_associacao"));
				this.atle_nascimento_data = dt.parse(getRequest().getParameter("data_nascimento"));
				this.asso_matricula = Integer.parseInt(getRequest().getParameter("atleta_matricula"));
				
				this.receiver = new Secretario(con);
				
			} catch (NullPointerException e) {
				errMsg = "Campo nao preenchido";
			} catch (ParseException e) {
				errMsg = "Data invalida";
			} catch (NumberFormatException e) {
				errMsg = "Passagem de caracteres em campo numerico";
			} catch (SQLException e) { 
				errMsg = "Nao foi possivel conectar a base de dados";
			}finally {
				try {
					if (errMsg != null) {
						getRequest().setAttribute("errorMsg", errMsg);
						getRequest().setAttribute("paginaRedirecionamento", "alterarCadastroAtleta.jsp");
						getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
					}
				} catch (ServletException|IOException e) {
					e.printStackTrace();
				}
			}
			
		}

	@Override
	public void execute() {
		String callback = null;
		String errMsg = null;
		try {
			if ( (callback = this.receiver.alterarCadastroAtleta(atle_nome, atle_categoria, atle_numero, atle_indice, atle_oficio_data,
					atle_associacao_data, atle_nascimento_data, asso_matricula)).equals("SUCCESS") ) {
				getRequest().setAttribute("successMsg", "Atleta alterada com sucesso");
				getRequest().getRequestDispatcher("/sucesso.jsp").forward(getRequest(), getResponse());
			} else {
				getRequest().setAttribute("errorMsg", callback);
				getRequest().setAttribute("paginaRedirecionamento", "alterarCadastroAtleta.jsp");
				getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
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
					getRequest().setAttribute("paginaRedirecionamento", "alterarCadastroAtleta.jsp");
					getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
				}
			} catch (SQLException|ServletException|IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}