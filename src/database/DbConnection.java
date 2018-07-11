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
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection("jdbc:postgresql://localhost/"+database, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Connection connect() throws SQLException {
		return DbConnection.connect(DbConnection.database, DbConnection.user, DbConnection.password);
	}
	
	public static Connection mock() throws SQLException {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			return DriverManager.getConnection("jdbc:derby:memory:mockdb;create=true");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
