package invokers;

import commands.Command;

public class ListarAssociacaoInv {
	private Command listar;
	
	public ListarAssociacaoInv(Command listar) {
		this.listar = listar;
	}
	
	public void listar () {
		this.listar.execute();
	}
}
