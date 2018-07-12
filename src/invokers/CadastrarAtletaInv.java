package invokers;

import commands.Command;

public class CadastrarAtletaInv {
	private Command cadastrar;
	
	public CadastrarAtletaInv(Command cadastrar) {
		this.cadastrar = cadastrar;
	}
	
	public void cadastrar () {
		this.cadastrar.execute();
	}
}
