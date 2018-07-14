package receivers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import domain.Associacao;
import domain.Atleta;

import database.AssociacaoScript;
import database.AtletaScript;
import database.Mapper;
import database.Usuario;

import java.io.IOException;

public class Secretario extends Pessoa {
	
	protected Connection con;
	
	public Secretario (Connection con) {
		this.setCon(con);
	}
	
	public String lancarFiliacao (int numeroOficio, Date data, String nomeAssociacao, String siglaAssociacao, String enderecoAssociacao, int telefone, int comprovantePagto) {
		String callback = null;
		Associacao assoc = new Associacao(nomeAssociacao, siglaAssociacao, enderecoAssociacao, telefone, numeroOficio, data);
		Usuario tecnico = new Usuario("tecnico_"+siglaAssociacao, (byte)1, String.format("%04d", new java.util.Random().nextInt(9999)));
		
		try {
			Mapper mapper = new Mapper(getCon());
			
			List<Associacao> max = AssociacaoScript.listar();
			int maxSeq = 0;
			if (max.size() != 0) {
				max.sort((a, b) ->  b.get_matricula()-a.get_matricula());
				maxSeq = max.get(0).get_matricula()+1;
			}
			
			assoc.set_matricula(maxSeq);
			
			mapper.create(new AssociacaoScript().mapFrom(assoc));
			mapper.create(tecnico);
			
			callback = "SUCCESS Usuario do tecnico: "+tecnico.get_login()+"\nSenha: "+tecnico.get_senha();
			
		} catch (SQLException|IOException e) {
			e.printStackTrace();
			callback = "Erro inesperado no servidor";
		}
		
		
		return callback;
	}
	
	public String cadastrarAtleta (int numero, Date data_oficio, String nome, Date data_nascimento, Date data_entrada, 
			int matricula_atleta, String categoria, int comprovante_pagamento) {
		String callback = null;
		long atle_indice = 0;
		Atleta atleta = new Atleta(nome, categoria, numero, atle_indice, data_oficio, data_entrada, data_nascimento, matricula_atleta);
		//Usuario tecnico = new Usuario("tecnico_"+siglaAssociacao, (byte)1, String.format("%04d", new java.util.Random().nextInt(9999)));
		
		try {
			Mapper mapper = new Mapper(getCon());
			
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
			
			mapper.create(new AtletaScript().mapFrom(atleta));
			//mapper.create(tecnico);
			
			callback = "SUCCESS Matricula atleta: "+atleta.get_matricula();
			
		} catch (SQLException e) {
			e.printStackTrace();
			callback = "Erro inesperado no servidor";
		}
		
		return callback;
	}
	
	public String alterarFiliacaoAssociacao(int matricula, String nome, String sigla, String endereco, int telefone, int oficio, Date data) {
		String callback = null;
		try {
			Associacao asso = new Associacao(nome, sigla, endereco, telefone, oficio, data);
			asso.set_matricula(matricula);
			Mapper mapper = new Mapper(con);
			
			mapper.update(new AssociacaoScript().mapFrom(asso));
			callback = "SUCCESS";
		} catch (SQLException e) {
			e.printStackTrace();
			callback = "Erro inesperado no servidor";
		}
		return callback;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}
	
}
