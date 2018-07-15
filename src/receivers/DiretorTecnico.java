package receivers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import database.Associacao;
import database.Local;
import database.Mapper;

import java.sql.SQLException;

import database.DbConnection;
import database.Local;
import database.Mapper;


public class DiretorTecnico extends Secretario {
	
	public String incluirLocalCompeticao (String nome_local, String endereco_local, int piscinas_disponiveis) {
		String callback = null;
		
		
		
		try {
			Mapper mapper = new Mapper(con);
			
			
//			if(piscina_25_metros == 1) {
//				Local local1 = new Local(nome_competicao , endereco_competicao, (short)25);
//				mapper.create(local1);
//				
//			}
//			if(piscina_50_metros == 1) {
//				Local local2 = new Local(nome_competicao , endereco_competicao, (short)50);
//				mapper.create(local2);
//			}
			
			Local local = new Local(nome_local, endereco_local, (short)piscinas_disponiveis);
			mapper.create(local);
			
			
			callback = "SUCCESS Local inserido";
			
		} catch (SQLException e) {
			
			if(e.getMessage().toUpperCase().contains("CONSTRAINT")) {
				callback = "Local já cadastrado!";
			}else {
				e.printStackTrace();
				callback = "Erro inesperado no servidor";
			}
			
			
		}
		
		return callback;	
	}
	
	public String alterarLocalCompeticao(String nome_local, String endereco_local, int piscinas_disponiveis) {
		String callback = null;
		try {
			
			Local local = new Local(nome_local, endereco_local, (short)piscinas_disponiveis);
			
			Mapper mapper = new Mapper(con);
			
			mapper.update(local);
			callback = "SUCCESS";
		} catch (SQLException e) {
			e.printStackTrace();
			callback = "Erro inesperado no servidor";
		}
		return callback;
	}
	

	public DiretorTecnico (Connection con) {
		super(con);
	}
	
}
