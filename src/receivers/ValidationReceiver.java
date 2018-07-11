package receivers;

import java.sql.Connection;
import java.sql.SQLException;

import database.DbConnection;
import database.Mapper;
import database.Mapper.Filter;
import database.Usuario;
import java.util.List;

public class ValidationReceiver {

	public String validate (String login, String senha, byte acessoNecessario) {
		String callback = null;
		try {
			Connection con = DbConnection.connect();
			Mapper mapper = new Mapper(con);
			
		
			List<Usuario> usual = mapper.read(1, Usuario.class, new Filter("usua_login", "=", login));
			
			if (!usual.isEmpty()) {
				Usuario usua = usual.get(0);
				if (usua.get_acesso() != acessoNecessario) {
					callback = "Usuario nao possui acesso necessario";
				} else if (!usua.get_senha().equals(senha)) {
					callback = "Senha incorreta";
				} else {
					callback = "SUCCESS";
				}
			} else {
				callback = "Usuario nao cadastrado no sistema";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			callback = "Ocorreu um erro inesperado no servidor";
		}
		return callback;
	}
	
}
