package receivers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import database.Associacao;
import database.Local;
import database.Mapper;

public class DiretorTecnico extends Secretario {

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
