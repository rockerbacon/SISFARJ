package database;

import java.util.Date;

@Mapper.UseTables({Participacao.TABLE_NAME})
public class Participacao {
	public static final String TABLE_NAME = "PARTICIPACAO";
	
	@Mapper.PrimaryKey
	@Mapper.ForeignKey(references=AtletaScript.TABLE_NAME)
	int atle_matricula;
	
	@Mapper.PrimaryKey
	@Mapper.ForeignKey(references=Prova.TABLE_NAME)
	String prov_nome;

	Date part_tempo;
	
	Date part_recorde;
	
	byte part_colocacao;
	
	public Participacao() {}
	
	
}
