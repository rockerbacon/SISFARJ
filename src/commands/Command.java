package commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Command {
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public abstract void init();
	public abstract void execute();

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
}
