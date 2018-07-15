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
		private int atle_matricula;
		private String atle_nome;
		private String atle_categoria;
		private int atle_numero; 
		private long atleta_indice;
		private Date atle_oficio_data;
		private Date atle_associacao_data;
		private Date dataNasc;
		private int asso_matricula;
		private String comprovante_pagamento;
		
		
		Secretario receiver;
		
		
		public void AlterarCadastroAtletaCommand () {}

		@Override
		public void init() {
			String errMsg = null;
			try {
				SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
				String dataAss;
				String dataOf;
				String dataNasc;
				
				con = DbConnection.connect();
				this.atle_matricula = Integer.parseInt(getRequest().getParameter("atle_matricula"));
				this.atle_nome = getRequest().getParameter("nome");
				dataAss = getRequest().getParameter("atle_associacao_data");
				this.atle_associacao_data = dt.parse(dataAss);
				this.atle_numero = Integer.parseInt(getRequest().getParameter("numero"));				
				dataOf = getRequest().getParameter("atle_associacao_data");
				this.atle_oficio_data = dt.parse(dataOf);
				this.atle_nome = getRequest().getParameter("atle_categoria");
				this.atle_nome = getRequest().getParameter("atleta_indice");
				this.atle_nome = getRequest().getParameter("atleta_indice");
				dataNasc = getRequest().getParameter("data_nascimento");
				this.dataNasc = dt.parse(dataNasc);
				this.comprovante_pagamento = getRequest().getParameter("comprovante_pagamento");
				
			
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
			if ( (callback = this.receiver.alterarCadastroAtleta(atle_matricula, atle_nome, atle_categoria, atle_numero, atleta_indice, atle_oficio_data,
					atle_associacao_data, asso_matricula, dataNasc, comprovante_pagamento)).equals("SUCCESS") ) {
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