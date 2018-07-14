package commands;

import java.sql.SQLException;
import java.util.List;

import database.Mapper;
import database.Mapper.Filter;
import database.AssociacaoScript;

import domain.Associacao;

public class IdentificarUsuarioCommand implements Command {
	
	private String login;
	private String senha;
	private Mapper mapper;
	
	public IdentificarUsuarioCommand (Mapper mapper, String login, String senha) {
		this.login = login;
		this.senha = senha;
		this.mapper = mapper;
	}

	@Override
	public String execute() {
		String callback = null;
		try {
			List<AssociacaoScript> usual = mapper.read(1, AssociacaoScript.class, new Filter("asso_nome", "=", login));
			
			if (!usual.isEmpty()) {
				Associacao usua = usual.get(0).mapTo(new Associacao());
				if (!usua.get_senha().equals(senha)) {
					callback = "Senha incorreta";
				} else {
					callback = "SUCCESS";
				}
			} else {
				callback = "Associacao \""+login+"\" nao cadastrada no sistema";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			callback = "Ocorreu um erro na base de dados";
		}
		return callback;
	}
	
}
