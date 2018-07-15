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
public class AssociacaoScript implements Mapper.Script<Associacao>, MapperMocker.Script {

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
	
	//suprime diference entre camada de dados e dominio
	public AssociacaoScript (int comprovante_pagamento) {
		this.asso_comprovante_pagamento = comprovante_pagamento;
	}
	
	public static String getTableName() {
		return TABLE_NAME;
	}

	public int getAsso_matricula() {
		return asso_matricula;
	}

	public String getAsso_nome() {
		return asso_nome;
	}

	public String getAsso_sigla() {
		return asso_sigla;
	}

	public String getAsso_endereco() {
		return asso_endereco;
	}

	public int getAsso_telefone() {
		return asso_telefone;
	}

	public int getAsso_oficio() {
		return asso_oficio;
	}

	public Date getAsso_data() {
		return asso_data;
	}

	public String getAsso_senha() {
		return asso_senha;
	}

	public int getAsso_comprovante_pagamento() {
		return asso_comprovante_pagamento;
	}

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
	public Object mock () {
		AssociacaoScript object = null;
		try {
			//Mock object
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
			object = new AssociacaoScript().mapFrom(new Associacao("mock", "mck", "caso de teste", 123456, 123456, dt.parse("01/01/2018")));
			object.asso_matricula = 0;
			object.asso_senha = "password";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	@Override
	public Associacao mapTo(Associacao object) {

		object.set_data(asso_data);
		object.set_endereco(asso_endereco);
		object.set_matricula(asso_matricula);
		object.set_nome(asso_nome);
		object.set_oficio(asso_oficio);
		object.set_sigla(asso_sigla);
		object.set_telefone(asso_telefone);
		object.set_senha(asso_senha);
	
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
		}
		return this;
	}
	
}
