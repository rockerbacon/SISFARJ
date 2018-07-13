package receivers;

import database.Associacao;
import database.Atleta;
import database.DbConnection;
import database.Mapper;
import database.Mapper.Filter;
import database.Usuario;
import database.DbBatch;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Connection;

import org.junit.*;

import java.text.ParseException;

public class SecretarioTest {
	
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
	public void lancarFiliacao () {
		try {
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
			Secretario scr = new Secretario(con);
			Associacao asso = new Associacao("test", "tst", "rua 9", 123456, 123, dt.parse("01/01/2018"));
			List<Associacao> lancados;
			List<Usuario> usuarios;
			Mapper mapper = new Mapper(con);
			
			usuarios = mapper.read(-1, Usuario.class, new Filter("usua_acesso", "=", (byte)1));
			int userCount = usuarios.size();
			
			scr.lancarFiliacao(asso.get_oficio(), asso.get_data(), asso.get_nome(), asso.get_sigla(), asso.get_endereco(), asso.get_telefone(), 0);
			
			lancados = mapper.read(-1, Associacao.class, new Filter("asso_nome", "=", "test"));
			Assert.assertNotEquals(0, lancados.size());
			if (!lancados.isEmpty()) {
				Associacao entry = lancados.get(0);
				Assert.assertEquals(asso.get_endereco(), entry.get_endereco());
				Assert.assertEquals(asso.get_nome(), entry.get_nome());
				Assert.assertEquals(asso.get_sigla(), entry.get_sigla());
				Assert.assertEquals(asso.get_data(), entry.get_data());
				Assert.assertEquals(asso.get_oficio(), entry.get_oficio());
				Assert.assertEquals(asso.get_telefone(), entry.get_telefone());;
			}
			
			usuarios = mapper.read(-1, Usuario.class, new Filter("usua_acesso", "=", (byte)1));
			Assert.assertEquals(1, usuarios.size()-userCount);
			
		} catch (ParseException|SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
		
	}
	
	@Test
	public void cadastrarAtleta () {
		try {
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
			Associacao asso = new Associacao("test2", "tst", "rua 9", 123456, 123, dt.parse("01/01/2018"));
			asso.set_matricula(123);
			Mapper mapper = new Mapper(con);
			Secretario scr = new Secretario(con);
			
			mapper.create(asso);
			asso = mapper.read(1, Associacao.class, new Filter("asso_nome", "=", "test")).get(0);
			
			Atleta atle = new Atleta("teste", "m", 0, 0, dt.parse("01/01/2005"), dt.parse("01/01/2005"), dt.parse("01/01/2005"), asso.get_matricula());
			
			scr.cadastrarAtleta(atle.get_numero(), atle.get_oficio_data(), atle.get_nome(), atle.get_nascimento_data(), atle.get_associacao_data(), atle.get_asso_matricula(), atle.get_categoria(), 0);
			
			List<Atleta> atletas = mapper.read(-1, Atleta.class, new Filter("atle_nome", "=", "teste"));
			Assert.assertNotEquals(0, atletas.size());
			if (!atletas.isEmpty()) {
				Atleta entry = atletas.get(0);
				Assert.assertEquals(atle.get_categoria(), entry.get_categoria());
				Assert.assertEquals(atle.get_indice(), entry.get_indice());
				Assert.assertEquals(atle.get_nome(), entry.get_nome());
				Assert.assertEquals(atle.get_asso_matricula(), entry.get_asso_matricula());
				Assert.assertEquals(atle.get_associacao_data(), entry.get_associacao_data());
				Assert.assertEquals(atle.get_nascimento_data(), entry.get_nascimento_data());
				Assert.assertEquals(atle.get_numero(), entry.get_numero());
				Assert.assertEquals(atle.get_oficio_data(), entry.get_oficio_data());
			}
			
		} catch (ParseException|SQLException e) {
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
