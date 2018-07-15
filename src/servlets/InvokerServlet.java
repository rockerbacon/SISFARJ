package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import commands.*;
import database.DbConnection;
import database.Mapper;

/**
 * Servlet implementation class InvokerServlet
 */
@WebServlet("/InvokerServlet")
public class InvokerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvokerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ParameterCritic pc = new ParameterCritic(request);
		Command cmd = null;
		Connection con = null;
		String errMsg = null;
		String cmdName = request.getParameter("command");
		String callback = null;
		try {
			if (cmdName == null || cmdName.isEmpty()) {
				errMsg = "Command nao especificado";
			} else {
				con = DbConnection.connect();
				Mapper mapper = new Mapper(con);
				switch (cmdName.toUpperCase()) {
					case "FILIAR ASSOCIACAO":
					{
						int numeroOficio = pc.getInt("nOficio");
						Date dataOficio = pc.getDate("dataOficio", "dd/MM/yyyy");
						String nomeAssociacao = pc.getString("nomeAssoc");
						String siglaAssociacao = pc.getString("siglaAssoc");
						String endereco = pc.getString("enderecoAssoc");
						int tel = pc.getInt("telAssoc");
						int comprovantePagamento = pc.getInt("numComprovantePag");
						
						cmd = new FiliarAssociacaoCommand(mapper, numeroOficio, dataOficio, nomeAssociacao, siglaAssociacao, endereco, tel, comprovantePagamento);
					}
					break;
					case "IDENTIFICAR USUARIO":
					{
						String login = pc.getString("login");
						String senha = pc.getString("senha");
						cmd = new IdentificarUsuarioCommand(mapper, login, senha);
						
						callback = cmd.execute();
						if (callback.contains("SUCCESS")) {
							request.getRequestDispatcher((String)request.getSession().getAttribute("afterLogin")).forward(request, response);
						} else {
							errMsg = callback;
						}
					}
					break;
					case "ALTERAR FILIACAO DE ASSOCIACAO":
					{
						int matricula = pc.getInt("asso_matricula");
						String nome = pc.getString("asso_nome");
						String sigla = pc.getString("asso_sigla");
						String endereco = pc.getString("asso_endereco");
						int tel = pc.getInt("asso_telefone");
						int oficio = pc.getInt("asso_oficio");
						Date data = pc.getDate("asso_data", "dd/MM/yyyy");
						String senha = pc.getString("asso_senha");
						
						cmd = new AlterarFiliacaoCommand(mapper, matricula, nome, sigla, endereco, tel, oficio, data, senha);
					}
						
					break;
					case "CADASTRAR ATLETA":
					{
						int numero = pc.getInt("numero");;
						Date data_oficio = pc.getDate("data_oficio", "dd/MM/yyyy");
						String nome = pc.getString("nome");
						Date data_nascimento = pc.getDate("data_nascimento", "dd/MM/yyyy");
						Date data_entrada = pc.getDate("data_entrada", "dd/MM/yyyy");
						int matricula_atleta = pc.getInt("matricula_atleta");
						String categoria = pc.getString("categoria");
						int comprovante_pagamento = pc.getInt("comprovante_pagamento");
						
						cmd = new CadastrarAtletaCommand(mapper, numero, data_oficio, nome, data_nascimento, data_entrada, matricula_atleta, categoria, comprovante_pagamento);
					}
					break;
					case "ALTERAR CADASTRO ATLETA":
					{
						int atleta_numero = pc.getInt("atleta_numero");;
						Date atleta_oficio_data = pc.getDate("atleta_oficio_data", "dd/MM/yyyy");
						String atleta_nome = pc.getString("atleta_nome");
						Date atleta_nascimento_data = pc.getDate("atleta_nascimento_data", "dd/MM/yyyy");
						Date atleta_associacao_data = pc.getDate("atleta_associacao_data", "dd/MM/yyyy");
						int atleta_matricula = pc.getInt("atleta_matricula");
						String atleta_categoria = pc.getString("atleta_categoria");
						int comprovante_pagamento = pc.getInt("comprovante_pagamento");
						int atleta_indice = pc.getInt("atleta_indice");
						int matricula_associacao = pc.getInt("matricula_associacao");
						
						cmd = new alterarCadastroAtletaCommand (mapper, atleta_matricula, atleta_nome, atleta_categoria, atleta_numero, (long) atleta_indice, 
								atleta_oficio_data, atleta_associacao_data, atleta_nascimento_data, matricula_associacao, comprovante_pagamento);
					}
					break;
					case "INCLUIR LOCAIS DE COMPETICAO":
					{
						String nome_local = pc.getString("loca_nome");
						String endereco_local = pc.getString("loca_endereco");
						short loca_piscinas = (short)pc.getInt("piscinasDisponiveis");
						
						cmd = new IncluirLocaisCompeticaoCommand(mapper, nome_local, endereco_local, loca_piscinas);
					}
					break;
					case "ALTERAR LOCAIS DE COMPETICAO":
					{
						String loca_nome = pc.getString("loca_nome");
						String loca_endereco = pc.getString("loca_endereco");
						short loca_piscinas = (short)pc.getInt("piscinasDisponiveis");
						
						cmd = new AlterarLocaisCompeticaoCommand(mapper, loca_nome, loca_endereco, loca_piscinas);
					}
					break;
					default:
						errMsg = "Nao foi possivel encontrar command "+cmdName;
				}
			}
			
			if (callback == null) {
				callback = cmd.execute();
				if (callback.contains("SUCCESS")) {
					request.setAttribute("successMsg", callback.substring("SUCCESS ".length()));
					request.getRequestDispatcher("sucesso.jsp").forward(request, response);
				} else {
					errMsg = callback;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			errMsg = "Erro ao conectar ao banco de dados";
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			errMsg = e.getMessage();
		} finally {
			try {
				if (con != null) con.close();
				if (errMsg != null) {
					request.setAttribute("errorMsg", errMsg);
					request.setAttribute("paginaRedirecionamento", request.getParameter("paginaRedirecionamento"));
					request.getRequestDispatcher("error.jsp").forward(request, response);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
