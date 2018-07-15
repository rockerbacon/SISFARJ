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
						int numeroOficio = pc.getInt("nOficio");
						Date dataOficio = pc.getDate("dataOficio", "dd/MM/yyyy");
						String nomeAssociacao = pc.getString("nomeAssoc");
						String siglaAssociacao = pc.getString("siglaAssoc");
						String endereco = pc.getString("enderecoAssoc");
						int tel = pc.getInt("telAssoc");
						int comprovantePagamento = pc.getInt("numComprovantePag");
						
						cmd = new FiliarAssociacaoCommand(mapper, numeroOficio, dataOficio, nomeAssociacao, siglaAssociacao, endereco, tel, comprovantePagamento);
					break;
					case "ALTERAR FILIACAO":
						//cmd =  new AlterarFiliacaoCommand((int)args[++argc], (String)args[++argc], (String)args[++argc], (String)args[++argc], (int)args[++argc], (int)args[++argc], (Date)args[++argc]);
					break;
					case "IDENTIFICAR USUARIO":
						String login = pc.getString("login");
						String senha = pc.getString("senha");
						cmd = new IdentificarUsuarioCommand(mapper, login, senha);
						
						callback = cmd.execute();
						if (callback.contains("SUCCESS")) {
							request.getRequestDispatcher((String)request.getSession().getAttribute("afterLogin")).forward(request, response);
						} else {
							errMsg = callback;
						}
					break;
					default:
						errMsg = "Nao foi possivel encontrar command "+cmdName;
						throw new IllegalArgumentException("Command "+cmdName+" does not exist");
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
			errMsg = "Erro ao conectar ao banco de dados";
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
