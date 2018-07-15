package receivers;

import database.MapperMocker;
import database.AssociacaoScript;

import java.sql.SQLException;

import org.junit.*;

import commands.IdentificarUsuarioCommand;

public class IdentificarUsuarioTest {
	
	@Test
	public void validate() throws SQLException { //SQLException faz parte da interface do mapper real, porem, nunca e lancada por estar sendo usado um mocker
		MapperMocker mapper = new MapperMocker(1);
		AssociacaoScript asso = (AssociacaoScript)new AssociacaoScript().mock();	//recuperar mock object para evitar utilizacao de literais
		String loginMockado = asso.getAsso_nome();
		String senhaMockada = asso.getAsso_senha();
		IdentificarUsuarioCommand cmd = new IdentificarUsuarioCommand(mapper, loginMockado, senhaMockada);
		String callback = cmd.execute();
		if (!callback.contains("SUCCESS")) {
			Assert.fail("validation unsuccesful");
		}
	}
	
	@Test
	public void rejectLogin () throws SQLException {
		MapperMocker mapper = new MapperMocker(0);	//valor 0 indica que mapper nao retornara valores, simulando nao encontrar um login no banco
		AssociacaoScript asso = (AssociacaoScript)new AssociacaoScript().mock();	//recuperar mock object para evitar utilizacao de literais
		String loginMockado = asso.getAsso_nome();
		String senhaMockada = asso.getAsso_senha();
		IdentificarUsuarioCommand cmd = new IdentificarUsuarioCommand(mapper, loginMockado, senhaMockada);
		String callback = cmd.execute();
		if (!callback.equals(IdentificarUsuarioCommand.ERR_LOGIN_INCORRETO)) {
			Assert.fail("login not rejected");
		}
	}
	
	@Test
	public void rejectPassword () {
		MapperMocker mapper = new MapperMocker(1);
		AssociacaoScript asso = (AssociacaoScript)new AssociacaoScript().mock();	//recuperar mock object para evitar utilizacao de literais
		String loginMockado = asso.getAsso_nome();
		String senhaMockada = asso.getAsso_senha();
		IdentificarUsuarioCommand cmd = new IdentificarUsuarioCommand(mapper, loginMockado, senhaMockada+" errada");
		String callback = cmd.execute();
		if (!callback.equals(IdentificarUsuarioCommand.ERR_SENHA_INCORRETA)) {
			Assert.fail("password not rejected");
		}
	}
	
}
