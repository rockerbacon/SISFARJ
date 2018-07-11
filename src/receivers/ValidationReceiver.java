package receivers;

import java.sql.Connection;
import java.sql.SQLException;

import database.DbConnection;
import database.Mapper;
import database.Mapper.Filter;
import database.Associacao;
import java.util.List;

public class ValidationReceiver {

	public boolean validate (int matricula, String senha) {
		boolean validated = false;
		try {
			Connection con = DbConnection.connect();
			Mapper mapper = new Mapper(con);
			
			List<Associacao> assol = mapper.read(1, Associacao.class, new Filter("asso_matricula", "=", matricula));
			
			if (!assol.isEmpty()) {
				Associacao asso = assol.get(0);
				if (asso.get_senha().equals(senha)) {
					validated = true;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return validated;
	}
	
}
