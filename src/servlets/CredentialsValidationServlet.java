package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import commands.Command;
import database.DbConnection;
import database.Mapper;
import receivers.ValidationReceiver;

/**
 * Servlet implementation class CredentialsValidationServlet
 */
@WebServlet("/CredentialsValidationServlet")
public class CredentialsValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CredentialsValidationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		String errMsg = null;
		String callback;
		try {
			ParameterCritic pc = new ParameterCritic(request);
			con = DbConnection.connect();
			String login = pc.getString("login");
			String senha = pc.getString("senha");
			
			Command cmd = Command.getByName("Identificar Usuario", new Mapper(con), login, senha);
			
			callback = cmd.execute();
			if (callback.contains("SUCCESS")) {
				request.getRequestDispatcher((String)request.getSession().getAttribute("afterLogin")).forward(request, response);
			} else {
				errMsg = callback;
			}
		} catch (SQLException e) {
			errMsg = "Nao foi possivel conectar a base de dados";
		} finally {
			try {
				if (con != null) con.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (errMsg != null) {
					request.setAttribute("errorMsg", errMsg);
					request.setAttribute("paginaRedirecionamento", "login.jsp");
					request.getRequestDispatcher("/error.jsp").forward(request, response);
				}
			} catch (IOException|ServletException e) {
				e.printStackTrace();
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
