package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import database.Classe;
import database.Competicao;

public class DbBatch {
	
	public static void upDatabase (Connection con) {
		Mapper mapper = new Mapper(con);
		Usuario secretario = new Usuario("admin", (byte)0, "admin");
			
		//ordem da criacao tem importancia
		try {
			mapper.create(Associacao.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.create(Usuario.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.create(Atleta.class);
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
		
		Classe mirim = new Classe("Mirim", 7, 8);
		Classe mirim2 = new Classe("Mirim I/II", 9, 10);
		Classe petiz = new Classe("Petiz I-II", 11, 12);
		Classe infantil = new Classe("Infantil I-II", 13, 14);
		Classe juvenil = new Classe("Juvenil I-II", 15, 16);
		Classe junior = new Classe("Junior I-II", 17, 19);
		Classe senior = new Classe("Sênior", 20, 1000);
		Classe premaster = new Classe("Pré Master", 20, 24);
		Classe master25_29 = new Classe("Master 25, 29", 25, 29);
		Classe master30_34 = new Classe("Master 30, 34", 30, 34);
		Classe master35_39 = new Classe("Master 35, 39", 35, 39);
		Classe master40_44 = new Classe("Master 40, 44", 40, 44);
		Classe master45_49 = new Classe("Master 45, 49", 45, 49);
		Classe master50_54 = new Classe("Master 50, 54", 50, 54);
		Classe master55_59 = new Classe("Master 55, 59", 55, 59);
		Classe master60_64 = new Classe("Master 60, 64", 60, 64);
		Classe master65_69 = new Classe("Master 65, 69", 65, 69);
		Classe master70_74 = new Classe("Master 70, 74", 70, 74);
		Classe master75_79 = new Classe("Master 75, 79", 75, 79);
		Classe master80_84 = new Classe("Master 80, 84", 80, 84);
		Classe master85_89 = new Classe("Master 85, 89", 85, 89);
		Classe master90_94 = new Classe("Master 90, 94", 90, 94);
		try {
			mapper.create(mirim);
			mapper.create(mirim2);
			mapper.create(petiz);
			mapper.create(infantil);
			mapper.create(juvenil);
			mapper.create(junior);
			mapper.create(senior);
			mapper.create(premaster);
			mapper.create(master25_29);
			mapper.create(master30_34);
			mapper.create(master35_39);
			mapper.create(master40_44);
			mapper.create(master45_49);
			mapper.create(master50_54);
			mapper.create(master55_59);
			mapper.create(master60_64);
			mapper.create(master65_69);
			mapper.create(master70_74);
			mapper.create(master75_79);
			mapper.create(master80_84);
			mapper.create(master85_89);
			mapper.create(master90_94);
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
			mapper.delete(Atleta.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.delete(Usuario.class);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			mapper.delete(Associacao.class);
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
