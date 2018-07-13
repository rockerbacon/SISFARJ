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
			Connection con = DbConnection.connect();
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
		//String callback = null;
		List<Local> lista = null;
//		Associacao assoc = new Associacao(nomeAssociacao, siglaAssociacao, enderecoAssociacao, telefone, numeroOficio, data);
//		Usuario tecnico = new Usuario("tecnico_"+siglaAssociacao, (byte)1, String.format("%04d", new java.util.Random().nextInt(9999)));
//		
		
		try {
			Mapper mapper = new Mapper(getCon());
			lista = mapper.read(-1, Local.class);
			for(int i=0;i<lista.size();i++){
			    System.out.println(lista.get(i).get_nome());
			} 
			
//			List<Associacao> max = mapper.read(-1, Associacao.class);
//			int maxSeq = 0;
//			if (max.size() != 0) {
//				max.sort((a, b) ->  b.get_matricula()-a.get_matricula());
//				maxSeq = max.get(0).get_matricula()+1;
//			}
//			
//			assoc.set_matricula(maxSeq);
//			
//			mapper.create(assoc);
//			mapper.create(tecnico);
			
//			callback = "SUCCESS Usuario do tecnico: "+tecnico.get_login()+"\nSenha: "+tecnico.get_senha();
			return lista;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//callback = "Erro inesperado no servidor";
		}
		return lista;
		
		
		//return lista;
	}
	
}
