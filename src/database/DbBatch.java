package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class DbBatch {
	
	public static void upDatabase (Connection con) throws SQLException {
		Mapper mapper = new Mapper(con);
		Usuario secretario = new Usuario("admin", (byte)0, "admin");
			
		//ordem da criacao tem importancia
		mapper.create(Associacao.class);
		mapper.create(Usuario.class);
		mapper.create(Atleta.class);
		mapper.create(FormatoProva.class);
		mapper.create(Classe.class);
		mapper.create(Prova.class);
		mapper.create(Participacao.class);
		mapper.create(Local.class);
		mapper.create(Competicao.class);
		mapper.create(ProvaCompeticao.class);
		
		mapper.create(secretario);
	}
	
	public static void dropDatabase (Connection con) throws SQLException {
		Mapper mapper = new Mapper(con);
		
		//ordem da exclusao tem importancia
		mapper.delete(ProvaCompeticao.class);
		mapper.delete(Competicao.class);
		mapper.delete(Local.class);
		mapper.delete(Participacao.class);
		mapper.delete(Prova.class);
		mapper.delete(Classe.class);
		mapper.delete(FormatoProva.class);
		mapper.delete(Atleta.class);
		mapper.delete(Usuario.class);
		mapper.delete(Associacao.class);
		
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
