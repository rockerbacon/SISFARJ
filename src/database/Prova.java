package database;

@Mapper.UseTables({Prova.TABLE_NAME})
public class Prova {
	
	public static final String TABLE_NAME = "PROVA";
	
	@Mapper.PrimaryKey
	String prova_nome;
	
	String prova_categoria;
	
	public Prova() {}
	
	public Prova(String prova_nome, String prova_categoria) {
		super();
		this.prova_nome = prova_nome;
		this.prova_categoria = prova_categoria;
	}

	public String get_nome() {
		return prova_nome;
	}

	public String get_categoria() {
		return prova_categoria;
	}
	
	
	
	
}
