package receivers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import database.Associacao;
import database.Atleta;
import database.DbConnection;
import database.Mapper;
import database.Usuario;

public class Secretario extends Pessoa {
	
	private Connection con;
	
	public Secretario (Connection con) {
		this.con = con;
	}
	
	public String lancarFiliacao (int numeroOficio, Date data, String nomeAssociacao, String siglaAssociacao, String enderecoAssociacao, int telefone, int comprovantePagto) {
		String callback = null;
		Associacao assoc = new Associacao(nomeAssociacao, siglaAssociacao, enderecoAssociacao, telefone, numeroOficio, data);
		Usuario tecnico = new Usuario("tecnico_"+siglaAssociacao, (byte)1, String.format("%04d", new java.util.Random().nextInt(9999)));
		
		try {
			Mapper mapper = new Mapper(con);
			
			List<Associacao> max = mapper.read(-1, Associacao.class);
			int maxSeq = 0;
			if (max.size() != 0) {
				max.sort((a, b) ->  b.get_matricula()-a.get_matricula());
				maxSeq = max.get(0).get_matricula()+1;
			}
			
			assoc.set_matricula(maxSeq);
			
			mapper.create(assoc);
			mapper.create(tecnico);
			
			callback = "SUCCESS Usuario do tecnico: "+tecnico.get_login()+"\nSenha: "+tecnico.get_senha();
			
		} catch (SQLException e) {
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
		System.out.println(nome);
		System.out.println(categoria);
		System.out.println(numero);
		System.out.println(atle_indice);
		System.out.println("data-of: " + data_oficio);
		System.out.println("data-e: " + data_entrada);
		System.out.println("data-n: " + data_nascimento);
		System.out.println(matricula_atleta);
		//Usuario tecnico = new Usuario("tecnico_"+siglaAssociacao, (byte)1, String.format("%04d", new java.util.Random().nextInt(9999)));
		
		try {
			Connection con = DbConnection.connect();
			Mapper mapper = new Mapper(con);
			
			List<Atleta> max = mapper.read(-1, Atleta.class);
			int maxSeq = 0;
			if (max.size() != 0) {
				max.sort((a, b) ->  b.get_matricula()-a.get_matricula());
				maxSeq = max.get(0).get_matricula()+1;
			}
			
			atleta.set_matricula(maxSeq);
			
			mapper.create(atleta);
			//mapper.create(tecnico);
			
			callback = "SUCCESS Matricula atleta: "+atleta.get_matricula();
			
		} catch (SQLException e) {
			e.printStackTrace();
			callback = "Erro inesperado no servidor";
		}
		
		
		return callback;
	}
	
}
