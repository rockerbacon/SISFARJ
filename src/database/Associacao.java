package database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import java.sql.Connection;

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
	
	int asso_oficio;
	
	Date asso_data;
	
	public Associacao () {}
	
	public static List<Associacao> listar() throws SQLException, IOException {
		Connection con = DbConnection.connect();
		Mapper mapper = new Mapper(con);
		List<Associacao> lista = mapper.read(-1, Associacao.class);
		con.close();
		return lista;
	}

	public Associacao(String asso_nome, String asso_sigla, String asso_endereco, int asso_telefone, int asso_oficio, Date asso_data) {
		super();
		this.asso_nome = asso_nome;
		this.asso_sigla = asso_sigla;
		this.asso_endereco = asso_endereco;
		this.asso_telefone = asso_telefone;
		this.asso_oficio = asso_oficio;
		this.asso_data = asso_data;
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

	public int get_oficio() {
		return asso_oficio;
	}

	public Date get_data() {
		return asso_data;
	}
	
	public void set_matricula(int matricula) {
		this.asso_matricula = matricula;
	}
	
}
