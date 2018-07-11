package invokers;

import commands.Command;

public class FiliarAssociacaoInv {
	private Command filiar;
	
	public FiliarAssociacaoInv(Command filiar) {
		this.filiar = filiar;
	}
	
	public void filiar () {
		this.filiar.execute();
	}
}
