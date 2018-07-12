package commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import receivers.DiretorTecnico;

public class IncluirLocaisCompeticaoCommand implements Command {
	
	private String nome_competicao;
	private String endereco_competicao;
	private int piscina_25_metros;
	private int piscina_50_metros;
	
	private DiretorTecnico receiver;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public IncluirLocaisCompeticaoCommand(HttpServletRequest request, HttpServletResponse response) {
		try {
			this.nome_competicao = request.getParameter("nomeCompeticao");
			this.endereco_competicao = request.getParameter("enderecoCompeticao");
			this.piscina_25_metros = Integer.parseInt(request.getParameter("piscina25metros"));//System.out.println(request.getParameter("piscina25metros"));
			this.piscina_50_metros = Integer.parseInt(request.getParameter("piscina50metros"));
			this.receiver = new DiretorTecnico();
			this.request = request;
			this.response = response;
		} catch (NumberFormatException e) {
			try {
				request.setAttribute("errorMsg", "Passagem de caracteres em campo numerico");
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
			callback = this.receiver.incluirLocalCompeticao(nome_competicao, endereco_competicao, piscina_25_metros, piscina_50_metros);
			if(callback.contains("SUCCESS")) {
				credenciais = callback.substring(new String("SUCCESS ").length());
				request.setAttribute("successMsg", nome_competicao+" criada com sucesso\n"+credenciais);
				request.getRequestDispatcher("/sucesso.jsp").forward(request, response);
			} else {
				request.setAttribute("errorMsg", callback);
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
		}catch  (ServletException|IOException e) {
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
