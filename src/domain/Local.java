package domain;

import database.Mapper;

public class Local {
	String loca_nome;
	
	String loca_endereco;
	
	short loca_tam_pisc;
	
	public Local() {}

	public Local(String loca_nome, String loca_endereco, short loca_tam_pisc) {
		super();
		this.loca_nome = loca_nome;
		this.loca_endereco = loca_endereco;
		this.loca_tam_pisc = loca_tam_pisc;
	}

	public String get_nome() {
		return loca_nome;
	}

	public void set_nome(String loca_nome) {
		this.loca_nome = loca_nome;
	}

	public String get_endereco() {
		return loca_endereco;
	}

	public void set_endereco(String loca_endereco) {
		this.loca_endereco = loca_endereco;
	}

	public short get_tam_pisc() {
		return loca_tam_pisc;
	}

	public void set_tam_pisc(short loca_tam_pisc) {
		this.loca_tam_pisc = loca_tam_pisc;
	}
	
	
}
