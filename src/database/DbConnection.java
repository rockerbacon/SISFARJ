package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;
import org.apache.derby.jdbc.EmbeddedDriver;

@SuppressWarnings("unused")
public class DbConnection {
	public static final String user = "postgres";
	public static final String password = "db123";
	public static final String database = "db_sisfarj";
	
	public static Connection connect(String database, String user, String password) throws SQLException {
		return DriverManager.getConnection("jdbc:postgresql://localhost/"+database, user, password);
	}
	
	public static Connection connect() throws SQLException {
		return DbConnection.connect(DbConnection.database, DbConnection.user, DbConnection.password);
	}
	
	public static Connection mock() throws SQLException {
		return DriverManager.getConnection("jdbc:derby:memory:mockdb;create=true");
	}
}
