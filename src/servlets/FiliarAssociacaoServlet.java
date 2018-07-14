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

import commands.Command;
import database.DbConnection;
import database.Mapper;

/**
 * Servlet implementation class FiliarAssociacaoServlet
 */
@WebServlet("/FiliarAssociacaoServlet")
public class FiliarAssociacaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FiliarAssociacaoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		String errMsg = null;
		try {
			con = DbConnection.connect();
			ParameterCritic pc = new ParameterCritic(request);
			
			int numeroOficio = pc.getInt("nOficio");
			Date dataOficio = pc.getDate("dataOficio", "dd/MM/yyyy");
			String nomeAssociacao = pc.getString("nomeAssoc");
			String siglaAssociacao = pc.getString("siglaAssoc");
			String endereco = pc.getString("enderecoAssoc");
			int tel = pc.getInt("telAssoc");
			int comprovantePagamento = pc.getInt("numComprovantePag");
			
			Command cmd = Command.getByName("Filiar Associacao", new Mapper(con), numeroOficio, dataOficio, nomeAssociacao, siglaAssociacao, endereco, tel, comprovantePagamento);
			
			String callback = cmd.execute();
			
			if (callback.contains("SUCCESS")) {
				request.setAttribute("successMsg", callback.substring("SUCCESS ".length()));
				request.getRequestDispatcher("/sucesso.jsp").forward(request, response);
			} else {
				errMsg = callback;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			errMsg = "Erro ao acessar base de dados";
		} catch (IllegalArgumentException e) {
			errMsg = e.getMessage();
		} finally {
			try {
				if (con != null) con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			if (errMsg != null) {
				request.setAttribute("errorMsg", errMsg);
				request.setAttribute("paginaRedirecionamento", "filiarAssoc.jsp");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
