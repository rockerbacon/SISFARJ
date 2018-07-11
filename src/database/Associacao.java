package database;

import java.util.Date;

@Mapper.UseTables({Associacao.TABLE_NAME})
public class Associacao {

	public static final String TABLE_NAME = "ASSOCIACAO";
	
	@Mapper.PrimaryKey
	int asso_matricula;
	
	@Mapper.StringSize(size=32)
	String asso_nome;
	
	@Mapper.StringSize(fixed=true, size=3)
	String asso_sigla;
	
	@Mapper.StringSize(size=64)
	String asso_endereco;
	
	int asso_telefone;
	
	Date asso_oficio;
	
	Date asso_data;
	
	@Mapper.StringSize(size=16)
	String asso_senha;
	
	public Associacao () {}

	public Associacao(String asso_nome, String asso_sigla, String asso_endereco, int asso_telefone, Date asso_oficio,
			Date asso_data, String asso_senha) {
		super();
		this.asso_nome = asso_nome;
		this.asso_sigla = asso_sigla;
		this.asso_endereco = asso_endereco;
		this.asso_telefone = asso_telefone;
		this.asso_oficio = asso_oficio;
		this.asso_data = asso_data;
		this.asso_senha = asso_senha;
	}

	public int get_matricula() {
		return asso_matricula;
	}

	public String get_nome() {
		return asso_nome;
	}

	public String get_sigla() {
		return asso_sigla;
	}

	public String get_endereco() {
		return asso_endereco;
	}

	public int get_telefone() {
		return asso_telefone;
	}

	public Date get_oficio() {
		return asso_oficio;
	}

	public Date get_data() {
		return asso_data;
	}

	public String get_senha() {
		return asso_senha;
	}
	
	public void set_matricula(int matricula) {
		this.asso_matricula = matricula;
	}
	
}
