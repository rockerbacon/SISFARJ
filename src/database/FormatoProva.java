package database;

@Mapper.UseTables({FormatoProva.TABLE_NAME})
public class FormatoProva {
	public static final String TABLE_NAME = "FORMATO_PROVA";
	
	@Mapper.PrimaryKey
	short form_distancia;
	
	@Mapper.PrimaryKey
	char form_nado;

	public FormatoProva() {}
	
	public FormatoProva(short fmt_prova_distancia, char fmt_prova_nado) {
		super();
		this.form_distancia = fmt_prova_distancia;
		this.form_nado = fmt_prova_nado;
	}

	public short get_distancia() {
		return form_distancia;
	}

	public char get_nado() {
		return form_nado;
	}
	
	
	
	
}
