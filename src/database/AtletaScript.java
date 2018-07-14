package database;

import java.util.Date;

import database.Mapper.Script;
import domain.Atleta;

@Mapper.UseTables({AtletaScript.TABLE_NAME})
public class AtletaScript implements Mapper.Script<Atleta> {
	
	public static final String TABLE_NAME = "ATLETA";
	
	@Mapper.PrimaryKey
	int atle_matricula;
	
	String atle_nome;
	
	String atle_categoria;
	
	int atle_numero;
	
	long atle_indice;
	
	Date atle_oficio_data;
	
	Date atle_associacao_data;

	Date atle_nascimento_data;
	
	@Mapper.ForeignKey(references=AssociacaoScript.TABLE_NAME)
	int asso_matricula;
	
	public AtletaScript () {}
	

	@Override
	public Atleta mapTo(Atleta object) {
		object.set_asso_matricula(asso_matricula);
		object.set_associacao_data(atle_associacao_data);
		object.set_categoria(atle_categoria);
		object.set_indice(atle_indice);
		object.set_matricula(atle_matricula);
		object.set_nascimento_data(atle_nascimento_data);
		object.set_nome(atle_nome);
		object.set_numero(atle_numero);
		object.set_oficio_data(atle_oficio_data);
		
		return object;
	}

	@Override
	public Script<Atleta> mapFrom(Atleta object) {
		this.asso_matricula = object.get_asso_matricula();
		this.atle_associacao_data = object.get_associacao_data();
		this.atle_categoria = object.get_categoria();
		this.atle_indice = object.get_indice();
		this.atle_matricula = object.get_matricula();
		this.atle_nascimento_data = object.get_nascimento_data();
		this.atle_nome = object.get_nome();
		this.atle_numero = object.get_numero();
		this.atle_oficio_data = object.get_oficio_data();
		return this;
	}
	
}
