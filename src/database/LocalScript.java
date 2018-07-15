package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import domain.Local;


@Mapper.UseTables({LocalScript.TABLE_NAME})
public class LocalScript implements Mapper.Script<Local>, MapperMocker.Script {
	public static final String TABLE_NAME = "LOCAL";
	
	@Mapper.PrimaryKey
	String loca_nome;
	
	String loca_endereco;
	
	short loca_tam_pisc;
	
	public LocalScript() {}

	public LocalScript(String local_nome, String local_endereco, short local_tam_pisc) {
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
	
	public static List<LocalScript> listar() throws IllegalArgumentException, SQLException {
		Connection con = DbConnection.connect();
		Mapper mapper = new Mapper(con);
		
		List<LocalScript> lista = mapper.read(-1, LocalScript.class);
		con.close();
		
		return lista;
	}
	
	@Override
	public Local mapTo (Local object) {
		object.set_endereco(loca_endereco);
		object.set_nome(loca_nome);
		object.set_tam_pisc(loca_tam_pisc);
		return object;
	}
	
	@Override
	public LocalScript mapFrom (Local object ) {
		this.loca_endereco = object.get_endereco();
		this.loca_nome = object.get_nome();
		this.loca_tam_pisc = object.get_tam_pisc();
		return this;
	}
	
	@Override
	public Object mock() {
		return new LocalScript("mock", "mock", (short)75);
	}

	
}
