package database;

import java.util.Date;

@Mapper.UseTables({CompeticaoScript.TABLE_NAME})
public class CompeticaoScript {
	public static final String TABLE_NAME = "COMPETICAO";
	
	@Mapper.PrimaryKey
	Date comp_data;
	
	@Mapper.PrimaryKey
	@Mapper.ForeignKey(references=LocalScript.TABLE_NAME)
	String loca_nome;
	
	short loca_tam_pisc;
	
	String comp_nome;
	
	public CompeticaoScript() {}

	public CompeticaoScript(Date comp_data, String loca_nome, String comp_nome) {
		super();
		this.comp_data = comp_data;
		this.loca_nome = loca_nome;
		this.comp_nome = comp_nome;
	}

	public Date get_data() {
		return comp_data;
	}

	public String get_loca_nome() {
		return loca_nome;
	}

	public String get_nome() {
		return comp_nome;
	}
	
	public short get_loca_tam_pisc() {
		return loca_tam_pisc;
	}

	
	
	

}
