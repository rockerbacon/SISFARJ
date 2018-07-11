package receivers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import database.Associacao;
import database.DbConnection;
import database.Mapper;
import database.Usuario;

public class Secretario extends Pessoa {
	
	public String lancarFiliacao (int numeroOficio, Date data, String nomeAssociacao, String siglaAssociacao, String enderecoAssociacao, int telefone, int comprovantePagto) {
		String callback = null;
		Associacao assoc = new Associacao(nomeAssociacao, siglaAssociacao, enderecoAssociacao, telefone, numeroOficio, data);
		Usuario tecnico = new Usuario("tecnico_"+siglaAssociacao, (byte)1, String.format("%04d", new java.util.Random().nextInt(9999)));
		
		try {
			Connection con = DbConnection.connect();
			Mapper mapper = new Mapper(con);
			
			List<Associacao> max = mapper.read(-1, Associacao.class);
			int maxSeq = 0;
			if (max.size() != 0) {
				max.sort((a, b) ->  b.get_matricula()-a.get_matricula());
				maxSeq = max.get(0).get_matricula()+1;
			}
			
			assoc.set_matricula(maxSeq);
			
			mapper.create(assoc);
			mapper.create(tecnico);
			
			callback = "SUCCESS Usuario do tecnico: "+tecnico.get_login()+"\nSenha: "+tecnico.get_senha();
			
		} catch (SQLException e) {
			e.printStackTrace();
			callback = "Erro inesperado no servidor";
		}
		
		
		return callback;
	}
	
}
