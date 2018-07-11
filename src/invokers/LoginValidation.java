package invokers;

import commands.Command;

public class LoginValidation {

	private Command validationCmd;
	
	public LoginValidation (Command validationCmd) {
		this.validationCmd = validationCmd;
	}
	
	public void validate () {
		this.validationCmd.execute();
	}
	
}
