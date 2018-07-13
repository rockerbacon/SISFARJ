package commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DbConnection;
import receivers.DiretorTecnico;

public class IncluirLocaisCompeticaoCommand implements Command {
	
	private String nome_competicao;
	private String endereco_competicao;
	private int piscina_25_metros;
	private int piscina_50_metros;
	
	private DiretorTecnico receiver;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private Connection con;
	
	
	public IncluirLocaisCompeticaoCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		try {
			this.nome_competicao = request.getParameter("nomeCompeticao");
			this.endereco_competicao = request.getParameter("enderecoCompeticao");
			this.piscina_25_metros = Integer.parseInt(request.getParameter("piscina25metros"));//System.out.println(request.getParameter("piscina25metros"));
			this.piscina_50_metros = Integer.parseInt(request.getParameter("piscina50metros"));
			this.con = DbConnection.connect();
			this.receiver = new DiretorTecnico(con);
			this.request = request;
			this.response = response;
			
			//Tratamento de fluxo de exceção cagado
			if(nome_competicao.isEmpty()) {
				request.setAttribute("errorMsg", "O campo \"Nome\" não foi preenchido corretamente!");
				request.setAttribute("paginaRedirecionamento", "incluirLocaisCompeticao.jsp");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
			
			if(endereco_competicao.isEmpty()) {
				request.setAttribute("errorMsg", "O campo \"Endereço\" não foi preenchido corretamente!");
				request.setAttribute("paginaRedirecionamento", "incluirLocaisCompeticao.jsp");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
			
			if(request.getParameter("piscina25metros").isEmpty()) {
				request.setAttribute("errorMsg", "O campo \"Piscina 25 metros\" não foi preenchido corretamente!");
				request.setAttribute("paginaRedirecionamento", "incluirLocaisCompeticao.jsp");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
			
			if(request.getParameter("piscina50metros").isEmpty()) {
				request.setAttribute("errorMsg", "O campo \"Piscina 50 metros\" não foi preenchido corretamente!");
				request.setAttribute("paginaRedirecionamento", "incluirLocaisCompeticao.jsp");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
			
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
			} else if(callback.contains("Local já")){
				request.setAttribute("errorMsg", callback);
				request.setAttribute("paginaRedirecionamento", "incluirLocaisCompeticao.jsp");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				
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
