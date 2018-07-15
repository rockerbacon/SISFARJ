package commands;

import java.sql.SQLException;

import java.util.Date;

import database.AssociacaoScript;
import database.Mapper;
import domain.Associacao;

public class AlterarFiliacaoCommand implements Command {

	Mapper mapper;
	int asso_matricula;
	String asso_nome;
	String asso_sigla;
	String asso_endereco;
	int asso_telefone;
	int asso_oficio;
	Date asso_data;
	String asso_senha;

	public AlterarFiliacaoCommand(Mapper mapper, int asso_matricula, String asso_nome, String asso_sigla,
			String asso_endereco, int asso_telefone, int asso_oficio, Date asso_data, String asso_senha) {
		super();
		this.mapper = mapper;
		this.asso_matricula = asso_matricula;
		this.asso_nome = asso_nome;
		this.asso_sigla = asso_sigla;
		this.asso_endereco = asso_endereco;
		this.asso_telefone = asso_telefone;
		this.asso_oficio = asso_oficio;
		this.asso_data = asso_data;
		this.asso_senha = asso_senha;
	}

	@Override
	public String execute() {
		String callback = null;
		try {
			Associacao asso = new Associacao(this.asso_nome, this.asso_sigla, this.asso_endereco, this.asso_telefone, this.asso_oficio, this.asso_data);
			asso.set_matricula(this.asso_matricula);
			asso.set_senha(this.asso_senha);
			AssociacaoScript mapperScript = new AssociacaoScript().mapFrom(asso);
			
			mapper.update(mapperScript, mapperScript);
			callback = "SUCCESS Associacao alterada com sucesso";
		} catch (SQLException e) {
			e.printStackTrace();
			callback = "Erro inesperado no servidor";
		}
		return callback;
		
	}

}
