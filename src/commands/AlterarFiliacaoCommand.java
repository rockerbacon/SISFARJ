package commands;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.sql.SQLException;

import java.util.Date;

import javax.servlet.ServletException;

import database.DbConnection;
import receivers.Secretario;

public class AlterarFiliacaoCommand implements Command {

	int asso_matricula;
	String asso_nome;
	String asso_sigla;
	String asso_endereco;
	int asso_telefone;
	int asso_oficio;
	Date asso_data;
	
	
	

	public AlterarFiliacaoCommand(int asso_matricula, String asso_nome, String asso_sigla,
			String asso_endereco, int asso_telefone, int asso_oficio, Date asso_data) {
		super();
		this.asso_matricula = asso_matricula;
		this.asso_nome = asso_nome;
		this.asso_sigla = asso_sigla;
		this.asso_endereco = asso_endereco;
		this.asso_telefone = asso_telefone;
		this.asso_oficio = asso_oficio;
		this.asso_data = asso_data;
	}

	@Override
	public void execute() {
		String callback = null;
		String errMsg = null;
		try {
			if ( (callback = this.receiver.alterarFiliacaoAssociacao(asso_matricula, asso_nome, asso_sigla, asso_endereco, asso_telefone, asso_oficio, asso_data)).equals("SUCCESS") ) {
				getRequest().setAttribute("successMsg", "Associacao alterada com sucesso");
				getRequest().getRequestDispatcher("/sucesso.jsp").forward(getRequest(), getResponse());
			} else {
				getRequest().setAttribute("errorMsg", callback);
				getRequest().setAttribute("paginaRedirecionamento", "alterarFiliacaoAssociacao.jsp");
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
					getRequest().setAttribute("paginaRedirecionamento", "alterarFiliacaoAssociacao.jsp");
					getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
				}
			} catch (SQLException|ServletException|IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
