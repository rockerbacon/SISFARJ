package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import commands.*;

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
		String errMsg = null;
		String commandName = "commands."+request.getParameter("command");
		try {
			Command cmd = (Command)Class.forName(commandName).newInstance();
			
			cmd.setRequest(request);
			cmd.setResponse(response);
			cmd.init();
			
			cmd.execute();
		} catch (ClassNotFoundException e) {
			errMsg = "Command "+commandName+" not found";
		} catch (InstantiationException|IllegalAccessException e) {
			errMsg = "Command "+commandName+" does not have an accessible default constructor";
		} finally {
			if (errMsg != null) {
				request.setAttribute("errorMsg", errMsg);
				request.getRequestDispatcher("/error.jsp").forward(request, response);
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
