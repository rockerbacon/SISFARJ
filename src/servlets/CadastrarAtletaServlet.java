package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import commands.Command;
import commands.CadastrarAtletaCommand;
import commands.ValidationCommand;
import invokers.CadastrarAtletaInv;
import invokers.LoginValidation;

/**
 * Servlet implementation class CadastrarAtletaServlet
 */
@WebServlet("/CadastrarAtletaServlet")
public class CadastrarAtletaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CadastrarAtletaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		Command cadastrarAtletaCmd = new CadastrarAtletaCommand(request, response);
		CadastrarAtletaInv cadastrar = new CadastrarAtletaInv(cadastrarAtletaCmd);
		
		cadastrar.cadastrar();
	}

}