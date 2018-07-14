package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import java.text.ParseException;

import java.text.SimpleDateFormat;

import domain.Associacao;

public class DbBatch {
	
	public static void upDatabase (Connection con) {
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		Mapper mapper = new Mapper(con);
		Associacao sisfarj = null;
		try {
			sisfarj = new Associacao("admin", "SISFARJ", "Rural", 0, 0, dt.parse("14/07/2018"));
			sisfarj.set_matricula(0);
			sisfarj.set_senha("admin");
		} catch (ParseException e) {
			e.printStackTrace();
		}
			
		//ordem da criacao tem importancia
		try {
			mapper.create(AssociacaoScript.class);
			if (sisfarj != null) mapper.create(new AssociacaoScript().mapFrom(sisfarj));
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
