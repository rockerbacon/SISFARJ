package receivers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import database.DbBatch;
import database.DbConnection;
import database.Local;
import database.Mapper;
import database.Mapper.Filter;

public class DiretorTecnicoTest {
	
	private static Connection con;

	@BeforeClass
	public static void setUp() {
		try {
			con = DbConnection.mock();
			DbBatch.upDatabase(con);
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void incluirLocalCompeticao () {
		try {
			
			Mapper mapper = new Mapper(con);
			
			DiretorTecnico dir = new DiretorTecnico(con);
			
			Local local = new Local("test883","enderecoTeste",(short)25);
			
			dir.incluirLocalCompeticao(local.get_nome(),local.get_endereco(),1,0);
			
			
			List<Local> locais = mapper.read(-1, Local.class, new Filter("loca_nome", "=", local.get_nome()));
			Assert.assertNotEquals(0, locais.size());
			
			if(!locais.isEmpty()) {
				Local entry = locais.get(0);
				Assert.assertEquals(local.get_endereco(), entry.get_endereco());
				Assert.assertEquals(local.get_nome(), entry.get_nome());
				Assert.assertEquals(local.get_tam_pisc() , entry.get_tam_pisc());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@AfterClass
	public static void tearDown() {
		try {
			if (con != null) {
				DbBatch.dropDatabase(con);
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
