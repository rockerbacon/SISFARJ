package receivers;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;

import database.Associacao;
import database.DbBatch;
import database.DbConnection;
import database.Local;
import database.Mapper;
import database.Usuario;
import database.Mapper.Filter;

public class DiretorTest {
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
	
	public void listarLocalDeCompeticao() {
		try {
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
			DiretorTecnico drt = new DiretorTecnico(con);
			Local local = new Local("teste1", "eteste1", (short) 25);
			List<Local> enviados;
			Mapper mapper = new Mapper(con);
			
			mapper.create(local);
			
			enviados = mapper.read(-1, Local.class);
			
			List<Local> recebidos = drt.listarLocalDeCompeticao();
			
			Assert.assertNotEquals(0, enviados.size());
			if (!enviados.isEmpty()) {
				Local entry = enviados.get(0);
				Assert.assertEquals(local.get_nome(), entry.get_nome());
				Assert.assertEquals(local.get_endereco(), entry.get_endereco());
				Assert.assertEquals(local.get_tam_pisc(), entry.get_tam_pisc());
			}
			
			usuarios = mapper.read(-1, Usuario.class, new Filter("usua_acesso", "=", (byte)1));
			Assert.assertEquals(1, usuarios.size()-userCount);
			
		} catch (ParseException|SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
		
	}	
}


