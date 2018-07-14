package commands;

import java.util.Date;
import database.Mapper;


public interface Command {
	
	
	static void assertArgumentCount (Object[] args, int expectedCount) throws IllegalArgumentException {
		if (args.length != expectedCount) {
			throw new IllegalArgumentException("Not enough arguments for command");
		}
	}
	/**
	 * Retorna command especificado
	 * @param name: nome do caso de uso do command (case insensitive e sem ponto final)
	 * @param args: argumentos necessarios para executar command
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Command getByName(String name, Object... args) throws IllegalArgumentException {
		int argc = -1;
		Command cmd = null;
		switch (name.toUpperCase()) {
			case "FILIAR ASSOCIACAO": 
				assertArgumentCount(args, 8);
				cmd = new FiliarAssociacaoCommand((Mapper)args[++argc], (int)args[++argc], (Date)args[++argc], (String)args[++argc], (String)args[++argc], (String)args[++argc], (int)args[++argc], (int)args[++argc]);
			break;
			case "ALTERAR FILIACAO":
				assertArgumentCount(args, 8);
				cmd =  new AlterarFiliacaoCommand((int)args[++argc], (String)args[++argc], (String)args[++argc], (String)args[++argc], (int)args[++argc], (int)args[++argc], (Date)args[++argc]);
			break;
			case "IDENTIFICAR USUARIO":
				assertArgumentCount(args, 3);
				cmd = new IdentificarUsuarioCommand((Mapper)args[++argc], (String)args[++argc], (String)args[++argc]);
			break;
			default:
				throw new IllegalArgumentException("Command "+name+" does not exist");
		}
		return cmd;
	}
	
	public String execute();
	
}
