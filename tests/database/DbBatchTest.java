package database;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.*;

public class DbBatchTest {
	@Test
	public void setUpAndTearDown () {
		Connection con = null;
		try {
			con = DbConnection.mock();
			
			DbBatch.upDatabase(con);
			DbBatch.dropDatabase(con);
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} finally {
			try {
				if (con != null) con.close();				
			} catch (SQLException e) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}
	}
}
