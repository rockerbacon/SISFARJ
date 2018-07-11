package database;

@Mapper.UseTables({Local.TABLE_NAME})
public class Classe {
	public static final String TABLE_NAME = "CLASSE";
	
	@Mapper.PrimaryKey
	String classe_nome;
	
	int classe_idade_min;
	
	int classe_idade_max;

	public Classe() {}
	
	public Classe(String classe_nome, int classe_idade_min, int classe_idade_max) {
		super();
		this.classe_nome = classe_nome;
		this.classe_idade_min = classe_idade_min;
		this.classe_idade_max = classe_idade_max;
	}

	public String get_nome() {
		return classe_nome;
	}

	public int get_idade_min() {
		return classe_idade_min;
	}

	public int get_idade_max() {
		return classe_idade_max;
	}
	
	
	
	
	

}
