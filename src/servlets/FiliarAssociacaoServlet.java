package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import commands.Command;
import commands.FiliarAssociacaoCommand;
import commands.ValidationCommand;
import invokers.FiliarAssociacaoInv;
import invokers.LoginValidation;

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		Command filiarAssocCmd = new FiliarAssociacaoCommand(request, response);
		FiliarAssociacaoInv filiar = new FiliarAssociacaoInv(filiarAssocCmd);
		
		filiar.filiar();
	}

}
