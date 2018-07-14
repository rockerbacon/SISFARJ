package commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;

import database.AssociacaoScript;
import database.DbConnection;
import database.Mapper;
import domain.Associacao;

public class FiliarAssociacaoCommand implements Command {
	
	private Mapper mapper;
	private int numero_oficio;
	private Date data_oficio;
	private String nome_associacao;
	private String sigla_associacao;
	private String endereco_associacao;
	private int tel_associacao;
	private int comprovante_pagamento;
	
	
	public FiliarAssociacaoCommand(Mapper mapper, int numero_oficio, Date data_oficio, String nome_associacao, String sigla_associacao,
			String endereco_associacao, int tel_associacao, int comprovante_pagamento) {
		super();
		this.mapper = mapper;
		this.numero_oficio = numero_oficio;
		this.data_oficio = data_oficio;
		this.nome_associacao = nome_associacao;
		this.sigla_associacao = sigla_associacao;
		this.endereco_associacao = endereco_associacao;
		this.tel_associacao = tel_associacao;
		this.comprovante_pagamento = comprovante_pagamento;
	}

	@Override
	public String execute() {
		String callback = null;
		Associacao assoc = new Associacao(nome_associacao, sigla_associacao, endereco_associacao, tel_associacao, numero_oficio, data_oficio);
		assoc.set_comprovante_pagamento(comprovante_pagamento);
		
		try {
			
			List<Associacao> max = AssociacaoScript.listar();
			int maxSeq = 0;
			if (max.size() != 0) {
				max.sort((a, b) ->  b.get_matricula()-a.get_matricula());
				maxSeq = max.get(0).get_matricula()+1;
			}
			
			assoc.set_matricula(maxSeq);
			assoc.set_senha(String.format("%04d", new Random().nextInt(9999)));
			
			mapper.create(new AssociacaoScript().mapFrom(assoc));
			
			callback = "SUCCESS Associacao filiada com sucesso<br>Senha: "+assoc.get_senha();
			
		} catch (SQLException|IOException e) {
			e.printStackTrace();
			callback = "Erro inesperado no servidor";
		}
		
		
		return callback;

	}

}
