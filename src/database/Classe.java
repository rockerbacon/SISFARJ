package database;

@Mapper.UseTables({Classe.TABLE_NAME})
public class Classe {
	public static final String TABLE_NAME = "CLASSE";
	
	@Mapper.PrimaryKey
	String clas_nome;
	
	int clas_idade_min;
	
	int clas_idade_max;

	public Classe() {}


	public String get_nome() {
		return clas_nome;
	}

	public int get_idade_min() {
		return clas_idade_min;
	}

	public int get_idade_max() {
		return clas_idade_max;
	}
	
	
	

}
