package unity;

import domain.Atleta;

import database.AssociacaoScript;
import database.AtletaScript;
import database.DbConnection;
import database.Mapper;
import database.Mapper.Filter;
import database.MapperMocker;
import database.DbBatch;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Connection;

import domain.Associacao;

import org.junit.*;

import commands.CadastrarAtletaCommand;
import commands.FiliarAssociacaoCommand;

import java.text.ParseException;

public class SecretarioTest {
	
	@Test
	public void lancarFiliacao () {
		try {
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
			Associacao asso = new Associacao("test", "tst", "rua 9", 123456, 123, dt.parse("01/01/2018"));
			Mapper mapper = new MapperMocker(1);
			
			String callback = new FiliarAssociacaoCommand(mapper, asso.get_oficio(), asso.get_data(), asso.get_nome(), asso.get_sigla(), asso.get_endereco(), asso.get_telefone(), 0).execute();
			
			if (!callback.contains("SUCCESS")) {
				Assert.fail(callback);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
	}
	
	@Test
	public void cadastrarAtleta () {
		try {
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
			Atleta atle = new Atleta("teste", "m", 0, 0, dt.parse("01/01/2018"), dt.parse("01/01/2018"), dt.parse("01/01/2018"), 0, 0);
			Mapper mapper = new MapperMocker(1);
			
			String callback = new CadastrarAtletaCommand(mapper, atle.get_numero(), atle.get_oficio_data(), atle.get_nome(), atle.get_nascimento_data(), atle.get_associacao_data(), atle.get_asso_matricula(), atle.get_categoria(), 0).execute();
			
			if (!callback.contains("SUCCESS")) {
				Assert.fail(callback);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
}
