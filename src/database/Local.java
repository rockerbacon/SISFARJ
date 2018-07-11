package database;

@Mapper.UseTables({Local.TABLE_NAME})
public class Local {
	public static final String TABLE_NAME = "LOCAL";
	
	@Mapper.PrimaryKey
	String local_nome;
	
	String local_endereco;
	
	String local_tam_pisc;
	
	public Local() {}

	public Local(String local_nome, String local_endereco, String local_tam_pisc) {
		super();
		this.local_nome = local_nome;
		this.local_endereco = local_endereco;
		this.local_tam_pisc = local_tam_pisc;
	}

	public String getLocal_nome() {
		return local_nome;
	}

	public String getLocal_endereco() {
		return local_endereco;
	}

	public String getLocal_tam_pisc() {
		return local_tam_pisc;
	}

	
}
