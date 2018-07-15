package domain;

import java.util.Date;

public class Associacao {
	
	
	int asso_matricula;
	
	String asso_nome;
	
	String asso_sigla;
	
	String asso_endereco;
	
	int asso_telefone;
	
	int asso_oficio;
	
	Date asso_data;
	
	String asso_senha;
	
	public Associacao() {}
	
	public Associacao(String asso_nome, String asso_sigla, String asso_endereco, int asso_telefone, int asso_oficio, Date asso_data) {
		super();
		this.asso_nome = asso_nome;
		this.asso_sigla = asso_sigla;
		this.asso_endereco = asso_endereco;
		this.asso_telefone = asso_telefone;
		this.asso_oficio = asso_oficio;
		this.asso_data = asso_data;
	}


	public int get_matricula() {
		return asso_matricula;
	}


	public void set_matricula(int asso_matricula) {
		this.asso_matricula = asso_matricula;
	}


	public String get_nome() {
		return asso_nome;
	}


	public void set_nome(String asso_nome) {
		this.asso_nome = asso_nome;
	}


	public String get_sigla() {
		return asso_sigla;
	}


	public void set_sigla(String asso_sigla) {
		this.asso_sigla = asso_sigla;
	}


	public String get_endereco() {
		return asso_endereco;
	}


	public void set_endereco(String asso_endereco) {
		this.asso_endereco = asso_endereco;
	}


	public int get_telefone() {
		return asso_telefone;
	}


	public void set_telefone(int asso_telefone) {
		this.asso_telefone = asso_telefone;
	}


	public int get_oficio() {
		return asso_oficio;
	}


	public void set_oficio(int asso_oficio) {
		this.asso_oficio = asso_oficio;
	}


	public Date get_data() {
		return asso_data;
	}


	public void set_data(Date asso_data) {
		this.asso_data = asso_data;
	}

	public String get_senha() {
		return asso_senha;
	}

	public void set_senha(String asso_senha) {
		this.asso_senha = asso_senha;
	}
	
}
