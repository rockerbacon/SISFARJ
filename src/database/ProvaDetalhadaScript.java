package database;

import database.Mapper.Script;
import domain.Prova;

@Mapper.UseTables({ProvaScript.TABLE_NAME, ClasseScript.TABLE_NAME})
public class ProvaDetalhadaScript implements Mapper.Script<Prova>, MapperMocker.Script {
	
	String prov_nome;
	
	String prov_categoria;
	
	short prov_distancia;
	
	char prov_nado;
	
	String class_nome;
	
	int class_idade_min;
	
	int class_idade_max;
	
	public ProvaDetalhadaScript(String prov_nome, String prov_categoria, short prov_distancia, char prov_nado,
			String class_nome, int class_idade_min, int class_idade_max) {
		super();
		this.prov_nome = prov_nome;
		this.prov_categoria = prov_categoria;
		this.prov_distancia = prov_distancia;
		this.prov_nado = prov_nado;
		this.class_nome = class_nome;
		this.class_idade_min = class_idade_min;
		this.class_idade_max = class_idade_max;
	}

	public ProvaDetalhadaScript() {}

	@Override
	public ProvaDetalhadaScript mapFrom(Prova object) {
		this.prov_nome = object.get_nome();
		this.prov_categoria = object.get_categoria();
		this.prov_distancia = object.get_distancia();
		this.prov_nado = object.get_nado();
		this.class_nome = object.getClass_nome();
		this.class_idade_max = object.getClas_idade_max();
		this.class_idade_min = object.getClas_idade_min();
		return this;
	}

	@Override
	public Prova mapTo(Prova object) {
		object.set_nome(this.prov_nome);
		object.set_categoria(this.prov_categoria);
		object.set_distancia(prov_distancia);
		object.set_nado(prov_nado);
		object.setClas_idade_max(class_idade_max);
		object.setClas_idade_min(class_idade_min);
		return object;
	}

	@Override
	public Object mock() {
		ProvaDetalhadaScript mockObj = new ProvaDetalhadaScript("mock", "m", (short)50, 'L', "mock", 0, 100);
		
		return mockObj;
	}

	
	
	
}
