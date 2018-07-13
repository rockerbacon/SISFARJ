package commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import receivers.DiretorTecnico;

public class CriarCompeticaoCommand extends Command{
	
	private String nome_competicao;
	private Date data_competicao;
	private String local_competicao;
	private String tipo_piscina;
	private String nome_prova;
	private String classes;
	private String categorias;
	
	private DiretorTecnico receiver;
	
	public CriarCompeticaoCommand() {}
	
	@Override
	public void init() {
		try {
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
			String data;
			data = getRequest().getParameter("dataCompeticao");
			this.data_competicao = dt.parse(data);
		}catch (ParseException e){
			
		}
		
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	
}
