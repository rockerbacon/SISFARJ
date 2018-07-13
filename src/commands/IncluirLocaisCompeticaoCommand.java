package commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DbConnection;
import receivers.DiretorTecnico;

public class IncluirLocaisCompeticaoCommand extends Command {
	
	private String nome_competicao;
	private String endereco_competicao;
	private int piscina_25_metros;
	private int piscina_50_metros;
	
	private DiretorTecnico receiver;
	
	private Connection con;
	
	
	public IncluirLocaisCompeticaoCommand() {}
	
	@Override
	public void init() {
		String errMsg = null;
		try {
			this.nome_competicao = getRequest().getParameter("nomeCompeticao");
			this.endereco_competicao = getRequest().getParameter("enderecoCompeticao");
			this.piscina_25_metros = Integer.parseInt(getRequest().getParameter("piscina25metros"));//System.out.println(getRequest().getParameter("piscina25metros"));
			this.piscina_50_metros = Integer.parseInt(getRequest().getParameter("piscina50metros"));
			this.con = DbConnection.connect();
			this.receiver = new DiretorTecnico(con);
			
			//Tratamento de fluxo de exceção cagado
			if(nome_competicao.isEmpty()) {
				errMsg = "O campo \"Nome\" não foi preenchido corretamente!";
			}
			
			if(endereco_competicao.isEmpty()) {
				errMsg = "O campo \"Endereço\" não foi preenchido corretamente!";
			} else if (getRequest().getParameter("piscina25metros").isEmpty()) {
				errMsg = "O campo \"Piscina 25 metros\" não foi preenchido corretamente!";
			} else if (getRequest().getParameter("piscina50metros").isEmpty()) {
				errMsg = "O campo \"Piscina 50 metros\" não foi preenchido corretamente!";
			}
			
		} catch (SQLException e) {
			errMsg = "Nao foi possivel conectar a base de dados";
		} catch (NumberFormatException e) {
			errMsg = "Passagem de caracteres em campo numerico";
		} finally {
			try {
				if (errMsg != null) {
					getRequest().setAttribute("errorMsg", errMsg);
					getRequest().setAttribute("paginaRedirecionamento", "incluirLocaisCompeticao.jsp");
					getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
				}
			} catch (IOException|ServletException e) {
				e.printStackTrace();
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
				getRequest().setAttribute("successMsg", nome_competicao+" criada com sucesso\n"+credenciais);
				getRequest().getRequestDispatcher("/sucesso.jsp").forward(getRequest(), getResponse());
			} else {
				getRequest().setAttribute("errorMsg", callback);
				getRequest().setAttribute("paginaRedirecionamento", "incluirLocaisCompeticao.jsp");
				getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
			}
		} catch  (ServletException|IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
