package database;

@Mapper.UseTables({Local.TABLE_NAME})
public class Local {
	public static final String TABLE_NAME = "LOCAL";
	
	@Mapper.PrimaryKey
	String loca_nome;
	
	String loca_endereco;
	
	short loca_tam_pisc;
	
	public Local() {}

	public Local(String local_nome, String local_endereco, short local_tam_pisc) {
		super();
		this.loca_nome = local_nome;
		this.loca_endereco = local_endereco;
		this.loca_tam_pisc = local_tam_pisc;
	}

	public String get_nome() {
		return loca_nome;
	}

	public String get_endereco() {
		return loca_endereco;
	}

	public short get_tam_pisc() {
		return loca_tam_pisc;
	}

	
}
