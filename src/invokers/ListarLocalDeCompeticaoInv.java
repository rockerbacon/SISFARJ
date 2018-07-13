package invokers;

import commands.Command;

public class ListarLocalDeCompeticaoInv {
	private Command listar;
	
	public ListarLocalDeCompeticaoInv(Command listar) {
		this.listar = listar;
	}
	
	public void listar () {
		this.listar.execute();
	

	}
}
