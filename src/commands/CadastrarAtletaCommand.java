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

public class CadastrarAtletaCommand extends Command {
	
	private int numero;
	private Date data_oficio;
	private String nome;
	private Date data_nascimento;
	private Date data_entrada;
	private int matricula_atleta;
	private String categoria;
	private int comprovante_pagamento;
	
	private Secretario receiver;
	private Connection con;
	
	@Override
	public void init() {
		String errMsg = null;
		try {
			
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
			String dataOf;
			String dataNasc;
			String dataEntrada;
			
			con = DbConnection.connect();
			this.numero = Integer.parseInt(getRequest().getParameter("numero"));
			//System.out.println(this.numero);
			dataOf = getRequest().getParameter("data_oficio");
			this.data_oficio = dt.parse(dataOf);
			this.nome = getRequest().getParameter("nome");
			dataNasc = getRequest().getParameter("data_nascimento");
			this.data_nascimento = dt.parse(dataNasc);
			dataEntrada = getRequest().getParameter("data_entrada");
			this.data_entrada = dt.parse(dataEntrada);
			this.matricula_atleta = Integer.parseInt(getRequest().getParameter("matricula_atleta"));
			//System.out.println(this.matricula_atleta);
			this.categoria = getRequest().getParameter("categoria");
			this.comprovante_pagamento = Integer.parseInt(getRequest().getParameter("comprovante_pagamento"));
			//this.comprovante_pagamento = 10;
			//System.out.println(this.comprovante_pagamento);
			this.receiver = new Secretario(con);
			
		}catch (NullPointerException e) {
			errMsg = "Campo nao preenchido";
		} catch (NumberFormatException e) {
			errMsg = "Passagem de caracteres em campo numerico";
		} catch (ParseException e) {
			errMsg = "Data invalida";
		} catch (SQLException e) {
			errMsg = "Nao foi possivel conectar ao database";
		} finally {
			try {
				if (errMsg != null) {
					getRequest().setAttribute("errorMsg", errMsg);
					getRequest().setAttribute("paginaRedirecionamento", "cadastroAtleta.jsp");
					getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
				}
			} catch (ServletException|IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void execute() {
		String callback = "";
		String credenciais = "";
		String errMsg = null;
		try {
			callback = this.receiver.cadastrarAtleta(numero, data_oficio, nome, data_nascimento, data_entrada, matricula_atleta, categoria, comprovante_pagamento);
			if (callback.contains("SUCCESS")) {
				credenciais = callback.substring(new String("SUCCESS ").length());
				getRequest().setAttribute("successMsg", nome+" criada com sucesso\n"+credenciais);
				getRequest().getRequestDispatcher("/sucesso.jsp").forward(getRequest(), getResponse());
			} else {
				getRequest().setAttribute("errorMsg", callback);
				getRequest().setAttribute("paginaRedirecionamento", "cadastroAtleta.jsp");
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
					getRequest().setAttribute("paginaRedirecionamento", "cadastroAtleta.jsp");
					getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
				}
			} catch (SQLException|ServletException|IOException e) {
				e.printStackTrace();
			}
		}

	}

}
