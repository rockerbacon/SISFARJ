package commands;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;

import java.io.IOException;
import database.DbConnection;
import receivers.DiretorTecnico;

public class AlterarLocaisCompeticaoCommand extends Command{
	
	private Connection con;
	private String loca_nome;
	private String loca_endereco;
	//private int piscina_25_metros;
	//private int piscina_50_metros;
	private int loca_piscinas;
	
	DiretorTecnico receiver;
	
	public void AlterarLocaiCompeticaoCommand() {}

	@Override
	public void init() {
		String errMsg = null;
		try {
			con = DbConnection.connect();
			this.loca_nome = getRequest().getParameter("loca_nome");
			this.loca_endereco = getRequest().getParameter("loca_endereco");
//			this.piscina_25_metros = Integer.parseInt(getRequest().getParameter("piscina25metros"));//System.out.println(getRequest().getParameter("piscina25metros"));
//			this.piscina_50_metros = Integer.parseInt(getRequest().getParameter("piscina50metros"));
			this.loca_piscinas = Integer.parseInt(getRequest().getParameter("piscinasDisponiveis"));
			
			this.receiver = new DiretorTecnico(con);
			
		}catch (NullPointerException e) {
			errMsg = "Campo não preenchido!";
		}catch (NumberFormatException e) {
			errMsg = "Passagem de caracteres em campo numerico";
		}catch (SQLException e) { 
			errMsg = "Nao foi possivel conectar a base de dados";
		}finally {
			try {
				if(errMsg != null) {
					getRequest().setAttribute("errMsg", errMsg);
					getRequest().setAttribute("paginaRedirecionamento", "alterarLocaisDeCompeticao.jsp");
					getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
				}
			}catch (ServletException|IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void execute() {
		String callback = null;
		String errMsg = null;
		try {
			if((callback = this.receiver.alterarLocalCompeticao(loca_nome, loca_endereco, loca_piscinas)).equals("SUCCESS")) {
				getRequest().setAttribute("successMsg", "Local alterado com sucesso");
				getRequest().getRequestDispatcher("/sucesso.jsp").forward(getRequest(), getResponse());
			}else {
				getRequest().setAttribute("errorMsg", callback);
				getRequest().setAttribute("paginaRedirecionamento", "alterarLocaisDeCompeticao.jsp");
				getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
			}
		}catch (NullPointerException e) {
			errMsg = "Campo não preenchido!";
		}catch  (ServletException|IOException e) {
			errMsg = "Erro inesperado no servidor";
		} finally {
			try {
				if (con != null) con.close();
				if (errMsg != null) {
					getRequest().setAttribute("errorMsg", errMsg);
					getRequest().setAttribute("paginaRedirecionamento", "alterarLocaisDeCompeticao.jsp");
					getRequest().getRequestDispatcher("/error.jsp").forward(getRequest(), getResponse());
				}
			} catch (SQLException|ServletException|IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	

}
