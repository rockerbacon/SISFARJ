package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import commands.Command;
import commands.IncluirLocaisCompeticaoCommand;
import invokers.IncluirLocaisCompeticaoInv;

/**
 * Servlet implementation class IncluirLocaisCompeticaoServlet
 */
@WebServlet("/IncluirLocaisCompeticaoServlet")
public class IncluirLocaisCompeticaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IncluirLocaisCompeticaoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		Command incluirLocaisCompeticaoCmd = null;
		try {
			incluirLocaisCompeticaoCmd = new IncluirLocaisCompeticaoCommand(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IncluirLocaisCompeticaoInv incluirLocal = new IncluirLocaisCompeticaoInv(incluirLocaisCompeticaoCmd);
		
		incluirLocal.incluirLocal();
	}

}
