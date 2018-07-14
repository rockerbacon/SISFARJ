package database;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import domain.Associacao;

import java.sql.Connection;

@Mapper.UseTables({AssociacaoScript.TABLE_NAME})
public class AssociacaoScript implements Mapper.Script<Associacao> {

	public static final String TABLE_NAME = "ASSOCIACAO";
	
	@Mapper.PrimaryKey
	int asso_matricula;
	
	String asso_nome;
	
	String asso_sigla;
	
	String asso_endereco;
	
	int asso_telefone;
	
	int asso_oficio;
	
	Date asso_data;
	
	String asso_senha;
	
	int asso_comprovante_pagamento;
	
	public AssociacaoScript () {}
	
	public static List<Associacao> listar() throws SQLException, IOException {
		Connection con = DbConnection.connect();
		Mapper mapper = new Mapper(con);
		List<AssociacaoScript> listaDb = mapper.read(-1, AssociacaoScript.class);
		con.close();
		List<Associacao> lista = new ArrayList<Associacao>(listaDb.size());
		
		for (AssociacaoScript script : listaDb) {
			Associacao asso = new Associacao();
			script.mapTo(asso);
			lista.add(asso);
		}
		
		return lista;
	}
	
	@Override
	public Associacao mapTo(Associacao object) {
	
		if (object != null) {
			object.set_data(asso_data);
			object.set_endereco(asso_endereco);
			object.set_matricula(asso_matricula);
			object.set_nome(asso_nome);
			object.set_oficio(asso_oficio);
			object.set_sigla(asso_sigla);
			object.set_telefone(asso_telefone);
			object.set_senha(asso_senha);
			object.set_comprovante_pagamento(asso_comprovante_pagamento);
		} else {
			try {
				//Mock object
				SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
				object = new Associacao("mock", "moc", "caso de teste", 123456, 123456, dt.parse("01/01/2018"));
				object.set_matricula(0);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	
		return object;
	}
	
	@Override
	public AssociacaoScript mapFrom(Associacao object) {
		if (object != null) {
			asso_data = object.get_data();
			asso_endereco = object.get_endereco();
			asso_matricula = object.get_matricula();
			asso_nome = object.get_nome();
			asso_oficio = object.get_oficio();
			asso_sigla = object.get_sigla();
			asso_telefone = object.get_telefone();
			asso_senha = object.get_senha();
			asso_comprovante_pagamento = object.get_comprovante_pagamento();
		}
		return this;
	}
	
}
