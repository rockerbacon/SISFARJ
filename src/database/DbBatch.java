package database;

import java.sql.Connection;
import java.sql.SQLException;

public class DbBatch {
	
	public static void upDatabase (Connection con) throws SQLException {
		Mapper mapper = new Mapper(con);
		
		//ordem da criacao tem importancia
		mapper.create(Associacao.class);
		mapper.create(Atleta.class);
		mapper.create(FormatoProva.class);
		mapper.create(Classe.class);
		mapper.create(Prova.class);
		mapper.create(Participacao.class);
		mapper.create(Local.class);
		mapper.create(Competicao.class);
		mapper.create(ProvaCompeticao.class);
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
		mapper.delete(Associacao.class);
		
	}
	
}
