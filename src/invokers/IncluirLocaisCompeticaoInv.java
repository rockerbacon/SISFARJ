package invokers;

import commands.Command;

public class IncluirLocaisCompeticaoInv {
	private Command incluirLocal;
	
	public IncluirLocaisCompeticaoInv(Command incluirLocal) {
		this.incluirLocal = incluirLocal;
	}
	
	public void incluirLocal() {
		this.incluirLocal.execute();
	}

}
