package database;

@Mapper.UseTables({ClasseScript.TABLE_NAME})
public class ClasseScript {
	public static final String TABLE_NAME = "CLASSE";
	
	@Mapper.PrimaryKey
	String clas_nome;
	
	int clas_idade_min;
	
	int clas_idade_max;

	public ClasseScript(String clas_nome, int clas_idade_min, int clas_idade_max) {
		this.clas_nome = clas_nome;
		this.clas_idade_min = clas_idade_min;
		this.clas_idade_max = clas_idade_max;
	}


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
