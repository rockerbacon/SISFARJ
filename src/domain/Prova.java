package domain;

public class Prova {
	
	String prov_nome;
	
	String prov_categoria;
	
	short prov_distancia;
	
	char prov_nado;
	
	String class_nome;
	
	int clas_idade_min;
	
	int clas_idade_max;
	
	public Prova() {}


	public Prova(String prov_nome, String prov_categoria, short form_distancia, char form_nado, String class_nome,
			int clas_idade_min, int clas_idade_max) {
		super();
		this.prov_nome = prov_nome;
		this.prov_categoria = prov_categoria;
		this.prov_distancia = form_distancia;
		this.prov_nado = form_nado;
		this.class_nome = class_nome;
		this.clas_idade_min = clas_idade_min;
		this.clas_idade_max = clas_idade_max;
	}


	public String get_nome() {
		return prov_nome;
	}


	public String get_categoria() {
		return prov_categoria;
	}


	public short get_distancia() {
		return prov_distancia;
	}


	public char get_nado() {
		return prov_nado;
	}


	public String getClass_nome() {
		return class_nome;
	}


	public int getClas_idade_min() {
		return clas_idade_min;
	}


	public int getClas_idade_max() {
		return clas_idade_max;
	}

	public void set_nome(String prov_nome) {
		this.prov_nome = prov_nome;
	}


	public void set_categoria(String prov_categoria) {
		this.prov_categoria = prov_categoria;
	}


	public void set_distancia(short prov_distancia) {
		this.prov_distancia = prov_distancia;
	}


	public void set_nado(char prov_nado) {
		this.prov_nado = prov_nado;
	}


	public void setClass_nome(String class_nome) {
		this.class_nome = class_nome;
	}


	public void setClas_idade_min(int clas_idade_min) {
		this.clas_idade_min = clas_idade_min;
	}


	public void setClas_idade_max(int clas_idade_max) {
		this.clas_idade_max = clas_idade_max;
	}
	
	
}
