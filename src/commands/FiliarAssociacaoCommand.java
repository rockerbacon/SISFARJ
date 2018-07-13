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

public class FiliarAssociacaoCommand extends Command {
	
	private int numero_oficio;
	private Date data_oficio;
	private String nome_associacao;
	private String sigla_associacao;
	private String endereco_associacao;
	private int tel_associacao;
	private int comprovante_pagamento;
	
	private Secretario receiver;
	private Connection con;
	
	@Override
	public void init() {
		String errMsg = null;
		try {
			
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
			String data;
			this.con = DbConnection.connect();
			this.numero_oficio = Integer.parseInt(getRequest().getParameter("nOficio"));
			data = getRequest().getParameter("dataOficio");
			this.data_oficio = dt.parse(data);
			this.nome_associacao = getRequest().getParameter("nomeAssoc");
			this.sigla_associacao = getRequest().getParameter("siglaAssoc");
			this.endereco_associacao = getRequest().getParameter("enderecoAssoc");
			this.tel_associacao = Integer.parseInt(getRequest().getParameter("telAssoc"));
			this.comprovante_pagamento = Integer.parseInt(getRequest().getParameter("numComprovantePag"));
			this.receiver = new Secretario(con);
			
		} catch (NullPointerException e) {
			errMsg = "Campo nao preenchido";
		} catch (NumberFormatException e) {
			errMsg = "Passagem de caracteres em campo numerico";
		} catch (ParseException e) {
			errMsg = "Data invalida";
		} catch (SQLException e) {
			errMsg = "Nao foi possivel conectar ao banco de dados";
		} finally {
			try {
				if (errMsg != null) {
					getRequest().setAttribute("errorMsg", errMsg);
					getRequest().setAttribute("paginaRedirecionamento", "filiarAssoc.jsp");
					getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
				}
			} catch (IOException|ServletException e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void execute() {
		String callback = "";
		String credenciais = "";
		String errMsg = null;
		try {
			callback = this.receiver.lancarFiliacao(numero_oficio, data_oficio, nome_associacao, sigla_associacao, endereco_associacao, tel_associacao, comprovante_pagamento);
			if (callback.contains("SUCCESS")) {
				credenciais = callback.substring(new String("SUCCESS ").length());
				getRequest().setAttribute("successMsg", nome_associacao+" criada com sucesso\n"+credenciais);
				getRequest().getRequestDispatcher("/sucesso.jsp").forward(getRequest(), getResponse());
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
					getRequest().setAttribute("paginaRedirecionamento", "filiarAssoc.jsp");
					getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
				}
			} catch (SQLException|ServletException|IOException e) {
				e.printStackTrace();
			}
		}

	}

}
