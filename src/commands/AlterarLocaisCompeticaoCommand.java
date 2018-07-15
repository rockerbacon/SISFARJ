package commands;

import java.sql.SQLException;

import database.Mapper;
import database.LocalScript;

import domain.Local;

public class AlterarLocaisCompeticaoCommand implements Command {
	
	private Mapper mapper;
	private String loca_nome;
	private String loca_endereco;
	private int loca_piscinas;

	public AlterarLocaisCompeticaoCommand(Mapper mapper, String loca_nome, String loca_endereco, int loca_piscinas) {
		super();
		this.mapper = mapper;
		this.loca_nome = loca_nome;
		this.loca_endereco = loca_endereco;
		this.loca_piscinas = loca_piscinas;
	}

	@Override
	public String execute() {
		String callback = null;
		try {
			Local local = new Local(loca_nome, loca_endereco, (short)loca_piscinas);
			
			mapper.update(new LocalScript().mapFrom(local));
			callback = "SUCCESS Local alterado com sucesso";
		} catch (SQLException e) {
			e.printStackTrace();
			callback = "Erro inesperado no servidor";
		}
		return callback;
		
	}
	
	
	

}
