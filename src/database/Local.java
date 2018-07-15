package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@Mapper.UseTables({Local.TABLE_NAME})
public class Local {
	public static final String TABLE_NAME = "LOCAL";
	
	@Mapper.PrimaryKey
	String loca_nome;
	
	String loca_endereco;
	
	//@Mapper.PrimaryKey
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
	
	public static List<Local> listar() throws IllegalArgumentException, SQLException {
		Connection con = DbConnection.connect();
		Mapper mapper = new Mapper(con);
		
		List<Local> lista = mapper.read(-1, Local.class);
		con.close();
		
		return lista;
	}

	
}
