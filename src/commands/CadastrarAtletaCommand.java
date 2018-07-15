package commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import database.AtletaScript;
import database.Mapper;
import domain.Atleta;

public class CadastrarAtletaCommand implements Command {
	
	private Mapper mapper;
	private int numero;
	private Date data_oficio;
	private String nome;
	private Date data_nascimento;
	private Date data_entrada;
	private int matricula_atleta;
	private String categoria;
	private int comprovante_pagamento;
	
	public CadastrarAtletaCommand(Mapper mapper, int numero, Date data_oficio, String nome, Date data_nascimento,
			Date data_entrada, int matricula_atleta, String categoria, int comprovante_pagamento) {
		super();
		this.mapper = mapper;
		this.numero = numero;
		this.data_oficio = data_oficio;
		this.nome = nome;
		this.data_nascimento = data_nascimento;
		this.data_entrada = data_entrada;
		this.matricula_atleta = matricula_atleta;
		this.categoria = categoria;
		this.comprovante_pagamento = comprovante_pagamento;
	}

	@Override
	public String execute() {
		String callback = null;
		long atle_indice = 0;
		Atleta atleta = new Atleta(nome, categoria, numero, atle_indice, data_oficio, data_entrada, data_nascimento, matricula_atleta);
		
		try {
			
			//recuperar maior matricula
			List<AtletaScript> max = mapper.read(-1, AtletaScript.class);
			ArrayList<Atleta> atleList = new ArrayList<Atleta>(max.size());
			for (AtletaScript script : max) {
				atleList.add(script.mapTo(new Atleta()));
			}
			
			int maxSeq = 0;
			if (atleList.size() != 0) {
				atleList.sort((a, b) ->  b.get_matricula()-a.get_matricula());
				maxSeq = atleList.get(0).get_matricula()+1;
			}
			atleta.set_matricula(maxSeq);
			
			mapper.create(new AtletaScript(this.comprovante_pagamento).mapFrom(atleta));
			
			callback = "SUCCESS Atleta cadastrado com sucesso<br>Matricula: "+atleta.get_matricula();
			
		} catch (SQLException e) {
			e.printStackTrace();
			callback = "Erro inesperado no servidor";
		}
		
		return callback;

	}

}
