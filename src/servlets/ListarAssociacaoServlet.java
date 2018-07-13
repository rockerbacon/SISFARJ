package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import commands.Command;
import commands.ListarAssociacaoCommand;
import commands.ValidationCommand;
import invokers.ListarAssociacaoInv;
import invokers.LoginValidation;

/**
 * Servlet implementation class FiliarAssociacaoServlet
 */
@WebServlet("/ListarAssociacaoServlet")
public class ListarAssociacaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarAssociacaoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		Command ListarAssocCmd = new ListarAssociacaoCommand(request, response);
		ListarAssociacaoInv listar = new ListarAssociacaoInv(ListarAssocCmd);
	
		
		listar.listar();
	}

}
