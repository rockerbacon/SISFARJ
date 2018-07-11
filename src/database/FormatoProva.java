package database;

@Mapper.UseTables({Local.TABLE_NAME})
public class FormatoProva {
	public static final String TABLE_NAME = "FORMATO_PROVA";
	
	@Mapper.PrimaryKey
	String fmt_prova_distancia;
	
	@Mapper.PrimaryKey
	String fmt_prova_nado;

	public FormatoProva() {}
	
	public FormatoProva(String fmt_prova_distancia, String fmt_prova_nado) {
		super();
		this.fmt_prova_distancia = fmt_prova_distancia;
		this.fmt_prova_nado = fmt_prova_nado;
	}

	public String get_distancia() {
		return fmt_prova_distancia;
	}

	public String get_nado() {
		return fmt_prova_nado;
	}
	
	
	
	
}
