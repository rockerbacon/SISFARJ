package unity;

import org.junit.*;

import commands.AlterarLocaisCompeticaoCommand;
import commands.IncluirLocaisCompeticaoCommand;
import database.Mapper;
import database.MapperMocker;
import domain.Local;

public class DiretorTecnicoTeste {
	
	@Test
	public void incluirLocalCompeticao() {
		try {
			Local local = new Local("TesteLocal",  "endTeste", (short) 25);
			Mapper mapper = new MapperMocker(1);
			
			String callback = new IncluirLocaisCompeticaoCommand( mapper, local.get_nome(), local.get_endereco(), (int) local.get_tam_pisc()).execute();
			
			if(!callback.contains("SUCCESS")) {
				Assert.fail(callback);
			}
		}catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void alterarLocalCompeticao() {
		try {
			Local local = new Local("TesteLocal3543", "TesteAlteracaoLocal", (short) 50);
			Mapper mapper = new MapperMocker(1);
			
			String callback = new AlterarLocaisCompeticaoCommand(mapper, local.get_nome(),local.get_endereco(),(int) local.get_tam_pisc()).execute();
			if(!callback.contains("SUCCESS")) {
				Assert.fail(callback);
			}
		}catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

}
