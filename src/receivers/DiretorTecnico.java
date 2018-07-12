package receivers;

import java.sql.Connection;
import java.sql.SQLException;

import database.DbConnection;
import database.Local;
import database.Mapper;

public class DiretorTecnico extends Secretario {
	
	public String incluirLocalCompeticao (String nome_competicao, String endereco_competicao, int piscina_25_metros, int piscina_50_metros) {
		String callback = null;
		Local local1 =  new Local(nome_competicao , endereco_competicao, (short)25);
		Local local2 =  new Local(nome_competicao , endereco_competicao, (short)50);
		//***TEM DE TRATAR CORRETAMENTE A INSERÇÃO DOS LOCAIS
		
//		if(piscina_25_metros == 1) {
//			local1 = new Local(nome_competicao , endereco_competicao, (short)25);
//		}else if(piscina_50_metros == 1) {
//			local2 = new Local(nome_competicao , endereco_competicao, (short)50);
//		}
		
		try {
			Connection con = DbConnection.connect();
			Mapper mapper = new Mapper(con);
			
			mapper.create(local1);
			//mapper.create(local2);
			
			callback = "SUCCESS Local inserido";
			
		} catch (SQLException e) {
			e.printStackTrace();
			callback = "Erro inesperado no servidor";
		}
		
		return callback;	
	}
	

}
