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
		private int atleta_matricula;
		private String atleta_nome;
		private String atleta_categoria;
		private int atleta_numero; 
		private long atleta_indice;
		private Date atleta_oficio_data;
		private Date atleta_associacao_data;
		private Date atleta_nascimento_data;
		private int matricula_associacao;
		private String comprovante_pagamento;
		
		
		Secretario receiver;
		
		
		public void AlterarCadastroAtletaCommand () {}

		@Override
		public void init() {
			String errMsg = null;
			try {
				SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

				
				con = DbConnection.connect();
				this.atleta_matricula = Integer.parseInt(getRequest().getParameter("atleta_matricula"));
				this.atleta_nome = getRequest().getParameter("atleta_nome");
				this.atleta_associacao_data = dt.parse(getRequest().getParameter("atleta_associacao_data"));
				this.atleta_numero = Integer.parseInt(getRequest().getParameter("atleta_numero"));				
				this.atleta_oficio_data = dt.parse(getRequest().getParameter("atleta_oficio_data"));
				this.atleta_categoria = getRequest().getParameter("atleta_categoria");
				this.atleta_indice = Long.parseLong(getRequest().getParameter("atleta_indice"));
				this.matricula_associacao = Integer.parseInt(getRequest().getParameter("matricula_associacao"));
				this.atleta_nascimento_data = dt.parse(getRequest().getParameter("atleta_nascimento_data"));
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
			if ( (callback = this.receiver.alterarCadastroAtleta( atleta_matricula, atleta_nome,  atleta_categoria,  atleta_numero,  atleta_indice,
					 atleta_oficio_data,  atleta_associacao_data, matricula_associacao,  atleta_nascimento_data,   comprovante_pagamento)).equals("SUCCESS") ) {
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