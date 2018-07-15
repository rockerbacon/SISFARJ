package commands;

import java.sql.SQLException;

import database.LocalScript;
import database.Mapper;

import domain.Local;

public class IncluirLocaisCompeticaoCommand implements Command {
	
	private Mapper mapper;
	private String nome_local;
	private String endereco_local;
	private int loca_piscinas;
	


	public IncluirLocaisCompeticaoCommand(Mapper mapper, String nome_local, String endereco_local, int loca_piscinas) {
		super();
		this.mapper = mapper;
		this.nome_local = nome_local;
		this.endereco_local = endereco_local;
		this.loca_piscinas = loca_piscinas;
	}



	@Override
	public String execute() {
		String callback = null;
		try {
			Local local = new Local(nome_local, endereco_local, (short)loca_piscinas);
			
			mapper.create(new LocalScript().mapFrom(local));
			
			callback = "SUCCESS Local inserido";
			
		} catch (SQLException e) {
			
			if(e.getMessage().toUpperCase().contains("CONSTRAINT")) {
				callback = "Local já cadastrado!";
			}else {
				e.printStackTrace();
				callback = "Erro inesperado no servidor";
			}
			
			
		}
		
		return callback;
		
	}

}
