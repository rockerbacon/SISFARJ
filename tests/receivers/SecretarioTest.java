package receivers;

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
	
	/*
	@Test
	public void cadastrarAtleta () {
		try {
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
			Associacao asso = new Associacao("test2", "tst", "rua 9", 123456, 123, dt.parse("01/01/2018"));
			asso.set_matricula(123);
			Mapper mapper = new MapperMocker(1);
			
			mapper.create(new AssociacaoScript().mapFrom(asso));
			asso = mapper.read(1, AssociacaoScript.class, new Filter("asso_nome", "=", "test")).get(0).mapTo(new Associacao());
			
			Atleta atle = new Atleta("teste", "m", 0, 0, dt.parse("01/01/2005"), dt.parse("01/01/2005"), dt.parse("01/01/2005"), asso.get_matricula());
			
			scr.cadastrarAtleta(atle.get_numero(), atle.get_oficio_data(), atle.get_nome(), atle.get_nascimento_data(), atle.get_associacao_data(), atle.get_asso_matricula(), atle.get_categoria(), 0);
			
			List<AtletaScript> atletas = mapper.read(-1, AtletaScript.class, new Filter("atle_nome", "=", "teste"));
			Assert.assertNotEquals(0, atletas.size());
			if (!atletas.isEmpty()) {
				Atleta entry = atletas.get(0).mapTo(new Atleta());
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
	*/
	
}
