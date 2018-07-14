package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class DbBatch {
	
	public static void upDatabase (Connection con) {
		Mapper mapper = new Mapper(con);
		Usuario secretario = new Usuario("admin", (byte)0, "admin");
			
		//ordem da criacao tem importancia
		try {
			mapper.create(AssociacaoScript.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.create(Usuario.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.create(AtletaScript.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.create(FormatoProva.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.create(Classe.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.create(Prova.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.create(Participacao.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.create(Local.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.create(Competicao.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.create(ProvaCompeticao.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.create(secretario);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void dropDatabase (Connection con) throws SQLException {
		Mapper mapper = new Mapper(con);
		
		//ordem da exclusao tem importancia
		try {
			mapper.delete(ProvaCompeticao.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.delete(Competicao.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.delete(Local.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.delete(Participacao.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.delete(Prova.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.delete(Classe.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.delete(FormatoProva.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.delete(AtletaScript.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.delete(Usuario.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.delete(AssociacaoScript.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		Connection con = null;
		
		System.out.print(">");
		String cmd = scn.nextLine();
		scn.close();
		try {
			con = DbConnection.connect();
			switch (cmd.toUpperCase()) {
				case "UPDATABASE":
					upDatabase(con);
				break;
				case "DROPDATABASE":
					dropDatabase(con);
				break;
				default:
					System.out.println("Commando nao reconhecido");
			}
			System.out.println("Command executado com exito");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
