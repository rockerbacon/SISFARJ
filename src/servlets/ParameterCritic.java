package servlets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class ParameterCritic {
	
	HttpServletRequest request;
	
	public ParameterCritic (HttpServletRequest request) {
		this.request = request;
	}
	
	public String getString (String id) throws IllegalArgumentException{
		String str = request.getParameter(id);
		if (str == null || str.isEmpty()) {
			throw new IllegalArgumentException("Todos os campos devem estar preenchidos");
		}
		return str;
	}

	public int getInt (String id) throws IllegalArgumentException {
		String str = getString(id);
		
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Numero invalido passado em campo");
		}
	}
	
	public Date getDate(String id, String dateFormat) {
		SimpleDateFormat dt = new SimpleDateFormat(dateFormat);
		String str = getString(id);
		try {
			return dt.parse(str);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Data invalida passada em campo");
		}
	}
	
	
}
