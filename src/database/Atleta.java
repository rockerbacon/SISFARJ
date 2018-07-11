package database;

import java.util.Date;

@Mapper.UseTables({Atleta.TABLE_NAME})
public class Atleta {
	
	public static final String TABLE_NAME = "ATLETA";
	
	@Mapper.PrimaryKey
	int atl_matricula;
	
	String atl_nome;
	
	String atl_categoria;
	
	int atl_num;
	
	long atl_indice;
	
	Date atl_oficio_data;
	
	Date atl_asso_data;

	Date atl_nasc_data;
	
	public Atleta () {}

	public Atleta(int atl_matricula, String atl_nome, String atl_categoria, int atl_num, long atl_indice,
			Date atl_oficio_data, Date atl_asso_data, Date atl_nasc_data) {
		super();
		this.atl_matricula = atl_matricula;
		this.atl_nome = atl_nome;
		this.atl_categoria = atl_categoria;
		this.atl_num = atl_num;
		this.atl_indice = atl_indice;
		this.atl_oficio_data = atl_oficio_data;
		this.atl_asso_data = atl_asso_data;
		this.atl_nasc_data = atl_nasc_data;
	}


	public int get_matricula() {
		return atl_matricula;
	}

	public String get_nome() {
		return atl_nome;
	}

	public String get_categoria() {
		return atl_categoria;
	}

	public int get_num() {
		return atl_num;
	}

	public long get_indice() {
		return atl_indice;
	}

	public Date get_oficio_data() {
		return atl_oficio_data;
	}

	public Date get_asso_data() {
		return atl_asso_data;
	}

	public Date get_nasc_data() {
		return atl_nasc_data;
	}

	public void set_matricula(int atl_matricula) {
		this.atl_matricula = atl_matricula;
	}
	
	
	
	
}
