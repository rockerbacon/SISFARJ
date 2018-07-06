package database;

import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.*;

public class DbConnectionTests {
	@Test(expected=SQLException.class)
	public void wrongDatabase () throws SQLException {
		Connection connection = DbConnection.connect("random", DbConnection.user, DbConnection.password);
	}
	
	@Test(expected=SQLException.class)
	public void wrongCredentials () throws SQLException {
		Connection connection = DbConnection.connect(DbConnection.database, "random", "random");
	}
	
	@Test
	public void connection () {
		try {
			Connection connection = DbConnection.connect();
		} catch (SQLException e) {
			Assert.fail("Connection unsuccesful");
		}
	}
}
