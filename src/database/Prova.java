package database;

@Mapper.UseTables({Prova.TABLE_NAME})
public class Prova {
	
	public static final String TABLE_NAME = "PROVA";
	
	@Mapper.PrimaryKey
	String prov_nome;
	
	String prov_categoria;
	
	@Mapper.ForeignKey(references=FormatoProva.TABLE_NAME)
	short form_distancia;
	@Mapper.ForeignKey(references=FormatoProva.TABLE_NAME)
	char form_nado;
	
	public Prova() {}
	

	public static String getTableName() {
		return TABLE_NAME;
	}




	public Prova(String prov_nome, String prov_categoria, short form_distancia, char form_nado) {
		super();
		this.prov_nome = prov_nome;
		this.prov_categoria = prov_categoria;
		this.form_distancia = form_distancia;
		this.form_nado = form_nado;
	}


	public String get_nome() {
		return prov_nome;
	}

	public String get_categoria() {
		return prov_categoria;
	}
	
	public short get_distancia() {
		return form_distancia;
	}
	
	
	public char get_nado() {
		return form_nado;
	}
	
}
