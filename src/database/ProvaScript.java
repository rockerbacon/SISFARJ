package database;

@Mapper.UseTables({ProvaScript.TABLE_NAME})
public class ProvaScript {
	
	public static final String TABLE_NAME = "PROVA";
	
	@Mapper.PrimaryKey
	String prov_nome;
	
	String prov_categoria;
	
	short prov_distancia;
	
	char prov_nado;
	
	public ProvaScript() {}

	public ProvaScript(String prov_nome, String prov_categoria, short form_distancia, char form_nado) {
		super();
		this.prov_nome = prov_nome;
		this.prov_categoria = prov_categoria;
		this.prov_distancia = form_distancia;
		this.prov_nado = form_nado;
	}


	public String get_nome() {
		return prov_nome;
	}

	public String get_categoria() {
		return prov_categoria;
	}
	
	public short get_distancia() {
		return prov_distancia;
	}
	
	
	public char get_nado() {
		return prov_nado;
	}
	
}
