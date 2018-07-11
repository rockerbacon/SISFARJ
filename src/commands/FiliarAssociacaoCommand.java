package commands;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import receivers.Secretario;
import receivers.ValidationReceiver;

public class FiliarAssociacaoCommand implements Command {
	
	private int numero_oficio;
	private Date data_oficio;
	private String nome_associacao;
	private String sigla_associacao;
	private String endereco_associacao;
	private int tel_associacao;
	private int comprovante_pagamento;
	
	private Secretario receiver;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public FiliarAssociacaoCommand(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
			String data;
			this.numero_oficio = Integer.parseInt(request.getParameter("nOficio"));
			data = request.getParameter("dataOficio");
			this.data_oficio = dt.parse(data);
			this.nome_associacao = request.getParameter("nomeAssoc");
			this.sigla_associacao = request.getParameter("siglaAssoc");
			this.endereco_associacao = request.getParameter("enderecoAssoc");
			this.tel_associacao = Integer.parseInt(request.getParameter("telAssoc"));
			this.comprovante_pagamento = Integer.parseInt(request.getParameter("numComprovantePag"));
			this.receiver = new Secretario();
			this.request = request;
			this.response = response;
			
		} catch (NumberFormatException e) {
			try {
				request.setAttribute("errorMsg", "Passagem de caracteres em campo numerico");
				request.getRequestDispatcher("/error.jsp").forward(request, response);

			} catch (IOException|ServletException e2) {
				e2.printStackTrace();
			}
		} catch (ParseException e) {
			try {
				request.setAttribute("errorMsg", "Data invalida");
				request.getRequestDispatcher("/error.jsp").forward(request, response);

			} catch (IOException|ServletException e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void execute() {
		String callback = "";
		String credenciais = "";
		try {
			callback = this.receiver.lancarFiliacao(numero_oficio, data_oficio, nome_associacao, sigla_associacao, endereco_associacao, tel_associacao, comprovante_pagamento);
			if (callback.contains("SUCCESS")) {
				credenciais = callback.substring(new String("SUCCESS ").length());
				request.setAttribute("successMsg", nome_associacao+" criada com sucesso\n"+credenciais);
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
