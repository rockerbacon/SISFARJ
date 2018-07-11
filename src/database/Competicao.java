package database;

import java.util.Date;

@Mapper.UseTables({Competicao.TABLE_NAME})
public class Competicao {
	public static final String TABLE_NAME = "COMPETICAO";
	
	@Mapper.PrimaryKey
	Date competicao_data;
	
	String competicao_nome;
	
	public Competicao() {}

	public Competicao(Date competicao_data, String competicao_nome) {
		super();
		this.competicao_data = competicao_data;
		this.competicao_nome = competicao_nome;
	}

	public Date get_data() {
		return competicao_data;
	}

	public String get_nome() {
		return competicao_nome;
	}
	
	

}
