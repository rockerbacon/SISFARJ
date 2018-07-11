package database;

import java.util.Date;

@Mapper.UseTables({ProvaCompeticao.TABLE_NAME})
public class ProvaCompeticao {

	public static final String TABLE_NAME = "PROVA_COMPETICAO";
	
	@Mapper.PrimaryKey
	@Mapper.ForeignKey(references=Competicao.TABLE_NAME)
	Date comp_data;
	
	@Mapper.PrimaryKey
	@Mapper.ForeignKey(references=Competicao.TABLE_NAME)
	String loca_nome;
	
	@Mapper.PrimaryKey
	@Mapper.ForeignKey(references=Prova.TABLE_NAME)
	String prov_nome;
	
	public ProvaCompeticao () {}

	public ProvaCompeticao(Date comp_data, String loca_nome, String prov_nome) {
		super();
		this.comp_data = comp_data;
		this.loca_nome = loca_nome;
		this.prov_nome = prov_nome;
	}


	
	public Date get_comp_data() {
		return comp_data;
	}
	
	public String get_loca_nome() {
		return loca_nome;
	}

	public String get_prov_nome() {
		return prov_nome;
	}
	
}
