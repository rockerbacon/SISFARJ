package database;

import java.util.Date;

@Mapper.UseTables({Participacao.TABLE_NAME})
public class Participacao {
	public static final String TABLE_NAME = "PARTICIPACAO";

	
	Date tempo;
	
	Date recorde;
	
	int colocacao;
	
	public Participacao() {}
	
	public Participacao(Date tempo, Date recorde, int colocacao) {
		super();
		this.tempo = tempo;
		this.recorde = recorde;
		this.colocacao = colocacao;
	}

	public Date getTempo() {
		return tempo;
	}

	public void setTempo(Date tempo) {
		this.tempo = tempo;
	}

	public Date getRecorde() {
		return recorde;
	}

	public void setRecorde(Date recorde) {
		this.recorde = recorde;
	}

	public int getColocacao() {
		return colocacao;
	}

	public void setColocacao(int colocacao) {
		this.colocacao = colocacao;
	}
	
	
}
