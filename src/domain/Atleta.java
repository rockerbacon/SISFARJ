package domain;

import java.util.Date;

public class Atleta {
	
	
	
	int atle_matricula;
	
	String atle_nome;
	
	String atle_categoria;
	
	int atle_numero;
	
	long atle_indice;
	
	Date atle_oficio_data;
	
	Date atle_associacao_data;

	Date atle_nascimento_data;
	int atle_comprovante_pagamento;
	
	int asso_matricula;
	
	public Atleta () {}


	public Atleta( String atle_nome, String atle_categoria, int atle_numero, long atle_indice,
			Date atle_oficio_data, Date atle_associacao_data, Date atle_nascimento_data, int asso_matricula, int atle_comprovante_pagamento) {
		super();
		this.atle_matricula = atle_matricula;
		this.atle_nome = atle_nome;
		this.atle_categoria = atle_categoria;
		this.atle_numero = atle_numero;
		this.atle_indice = atle_indice;
		this.atle_oficio_data = atle_oficio_data;
		this.atle_associacao_data = atle_associacao_data;
		this.atle_nascimento_data = atle_nascimento_data;
		this.asso_matricula = asso_matricula;
		this.atle_comprovante_pagamento = atle_comprovante_pagamento;
	}


	public int getAtle_comprovante_pagamento() {
		return atle_comprovante_pagamento;
	}


	public void setAtle_comprovante_pagamento(int atle_comprovante_pagamento) {
		this.atle_comprovante_pagamento = atle_comprovante_pagamento;
	}


	public int get_matricula() {
		return atle_matricula;
	}


	public void set_matricula(int atle_matricula) {
		this.atle_matricula = atle_matricula;
	}


	public String get_nome() {
		return atle_nome;
	}


	public void set_nome(String atle_nome) {
		this.atle_nome = atle_nome;
	}


	public String get_categoria() {
		return atle_categoria;
	}


	public void set_categoria(String atle_categoria) {
		this.atle_categoria = atle_categoria;
	}


	public int get_numero() {
		return atle_numero;
	}


	public void set_numero(int atle_numero) {
		this.atle_numero = atle_numero;
	}


	public long get_indice() {
		return atle_indice;
	}


	public void set_indice(long atle_indice) {
		this.atle_indice = atle_indice;
	}


	public Date get_oficio_data() {
		return atle_oficio_data;
	}


	public void set_oficio_data(Date atle_oficio_data) {
		this.atle_oficio_data = atle_oficio_data;
	}


	public Date get_associacao_data() {
		return atle_associacao_data;
	}


	public void set_associacao_data(Date atle_associacao_data) {
		this.atle_associacao_data = atle_associacao_data;
	}


	public Date get_nascimento_data() {
		return atle_nascimento_data;
	}


	public void set_nascimento_data(Date atle_nascimento_data) {
		this.atle_nascimento_data = atle_nascimento_data;
	}


	public int get_asso_matricula() {
		return asso_matricula;
	}


	public void set_asso_matricula(int asso_matricula) {
		this.asso_matricula = asso_matricula;
	}
	
	
	
	
}
