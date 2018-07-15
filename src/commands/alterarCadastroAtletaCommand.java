package commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;

import database.AtletaScript;
import database.DbConnection;
import database.LocalScript;
import database.Mapper;
import domain.Atleta;
import domain.Local;

public class alterarCadastroAtletaCommand implements Command{
		private Mapper mapper;
		private int atleta_matricula;
		private String atleta_nome;
		private String atleta_categoria;
		private int atleta_numero; 
		private long atleta_indice;
		private Date atleta_oficio_data;
		private Date atleta_associacao_data;
		private Date atleta_nascimento_data;
		private int matricula_associacao;
		private int comprovante_pagamento;
		
				
		
		public  alterarCadastroAtletaCommand (Mapper mapper, int atleta_matricula, String atleta_nome, String atleta_categoria, int atleta_numero, long atleta_indice, 
				Date atleta_oficio_data, Date atleta_associacao_data, Date atleta_nascimento_data, int matricula_associacao, int comprovante_pagamento) {
			
			super();
			this.mapper = mapper;
			this.atleta_matricula = atleta_matricula;
			this.atleta_nome = atleta_nome;
			this.atleta_categoria = atleta_categoria;
			this.atleta_numero = atleta_numero;
			this.atleta_indice = atleta_indice;
			this.atleta_oficio_data = atleta_oficio_data;
			this.atleta_associacao_data = atleta_associacao_data;
			this.atleta_nascimento_data = atleta_nascimento_data;
			this.matricula_associacao = matricula_associacao;
			this.comprovante_pagamento = comprovante_pagamento;
			
		}
		
		@Override
		public String execute() {
			String callback = null;
			try {
				Atleta atleta = new Atleta( atleta_matricula, atleta_nome, atleta_categoria, atleta_numero, atleta_indice,
						atleta_oficio_data, atleta_associacao_data, atleta_nascimento_data, matricula_associacao, comprovante_pagamento);
				
				mapper.update(new AtletaScript().mapFrom(atleta));
				
				callback = "SUCCESS Atleta alterado";
				
			} catch (SQLException e) {
				
				if(e.getMessage().toUpperCase().contains("CONSTRAINT")) {
					callback = "Atleta já cadastrado!";
				}else {
					e.printStackTrace();
					callback = "Erro inesperado no servidor";
				}
				
				
			}
			
			return callback;
			
		}


	

}