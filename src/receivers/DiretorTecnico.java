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
	
	public String incluirLocalCompeticao (String nome_competicao, String endereco_competicao, int piscina_25_metros, int piscina_50_metros) {
		String callback = null;
		
		
		
		try {
			Mapper mapper = new Mapper(con);
			
			
			if(piscina_25_metros == 1) {
				Local local1 = new Local(nome_competicao , endereco_competicao, (short)25);
				mapper.create(local1);
				
			}else if(piscina_50_metros == 1) {
				Local local2 = new Local(nome_competicao , endereco_competicao, (short)50);
				mapper.create(local2);
			}
			
			
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
	

	public DiretorTecnico (Connection con) {
		super(con);
	}
	
	public List<Local> listarLocalDeCompeticao() {
		List<Local> lista = null;	
		
		try {
			Mapper mapper = new Mapper(getCon());
			lista = mapper.read(-1, Local.class);
			for(int i=0;i<lista.size();i++){
			    System.out.println(lista.get(i).get_nome());
			} 
			
			return lista;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
}
