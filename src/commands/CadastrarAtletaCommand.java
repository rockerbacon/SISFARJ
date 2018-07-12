package commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DbConnection;
import receivers.Secretario;
import receivers.ValidationReceiver;

public class CadastrarAtletaCommand implements Command {
	
	private int numero;
	private Date data_oficio;
	private String nome;
	private Date data_nascimento;
	private Date data_entrada;
	private int matricula_atleta;
	private String categoria;
	private int comprovante_pagamento;
	
	private Secretario receiver;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public CadastrarAtletaCommand(HttpServletRequest request, HttpServletResponse response) {
		Connection con = null;
		String errMsg = null;
		try {
			
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
			String dataOf;
			String dataNasc;
			String dataEntrada;
			
			con = DbConnection.connect();
			this.numero = Integer.parseInt(request.getParameter("numero"));
			//System.out.println(this.numero);
			dataOf = request.getParameter("data_oficio");
			this.data_oficio = dt.parse(dataOf);
			this.nome = request.getParameter("nome");
			dataNasc = request.getParameter("data_nascimento");
			this.data_nascimento = dt.parse(dataNasc);
			dataEntrada = request.getParameter("data_entrada");
			this.data_entrada = dt.parse(dataEntrada);
			this.matricula_atleta = Integer.parseInt(request.getParameter("matricula_atleta"));
			//System.out.println(this.matricula_atleta);
			this.categoria = request.getParameter("categoria");
			this.comprovante_pagamento = Integer.parseInt(request.getParameter("comprovante_pagamento"));
			//this.comprovante_pagamento = 10;
			//System.out.println(this.comprovante_pagamento);
			this.receiver = new Secretario(con);
			this.request = request;
			this.response = response;
			
		} catch (NumberFormatException e) {
			errMsg = "Passagem de caracteres em campo numerico";
		} catch (ParseException e) {
			errMsg = "Data invalida";
		} catch (SQLException e) {
			errMsg = "Nao foi possivel conectar ao database";
		
		} finally {
			try {
				if (con != null) con.close();
				if (errMsg != null) {
					request.setAttribute("errorMsg", errMsg);
					request.getRequestDispatcher("/error.jsp").forward(request, response);
				}
			} catch (SQLException|ServletException|IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void execute() {
		String callback = "";
		String credenciais = "";
		try {
			callback = this.receiver.cadastrarAtleta(numero, data_oficio, nome, data_nascimento, data_entrada, matricula_atleta, categoria, comprovante_pagamento);
			if (callback.contains("SUCCESS")) {
				credenciais = callback.substring(new String("SUCCESS ").length());
				request.setAttribute("successMsg", nome+" criada com sucesso\n"+credenciais);
				request.getRequestDispatcher("/sucesso.jsp").forward(request, response);
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
		}

	}

}
