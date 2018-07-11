package database;

@Mapper.UseTables({Usuario.TABLE_NAME})
public class Usuario {
	
	public static final String TABLE_NAME = "USUARIO";
	
	public static final byte ACESSO_SECRETARIO = 0;
	public static final byte ACESSO_TECNICO = 1;
	public static final byte ACESSO_DIRETOR = 2;
	
	@Mapper.PrimaryKey
	String usua_login;
	
	@Mapper.PrimaryKey
	byte usua_acesso;
	
	@Mapper.StringSize(size=32)
	String usua_senha;
	

	public Usuario(String usua_login, byte usua_acesso, String usua_senha) {
		super();
		this.usua_login = usua_login;
		this.usua_acesso = usua_acesso;
		this.usua_senha = usua_senha;
	}
	
	public Usuario () {}
	
	public byte get_acesso() {
		return usua_acesso;
	}

	public String get_senha() {
		return usua_senha;
	}

	
}
