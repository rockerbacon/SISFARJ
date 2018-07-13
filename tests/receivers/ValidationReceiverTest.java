package receivers;

import database.DbConnection;
import database.DbBatch;

import java.sql.SQLException;
import java.sql.Connection;

import org.junit.*;

public class ValidationReceiverTest {
	
	private static Connection con;
	
	@BeforeClass
	public static void setUp() {
		try {
			con = DbConnection.mock();
			DbBatch.upDatabase(con);
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void validate() {
		ValidationReceiver validator = new ValidationReceiver(con);
		String callback = validator.validate("admin", "admin", (byte)0);
		if (!callback.contains("SUCCESS")) {
			Assert.fail("validation unsuccesful");
		}
	}
	
	@Test
	public void denyAccess () {
		ValidationReceiver validator = new ValidationReceiver(con);
		String callback = validator.validate("admin", "admin", (byte)1);
		if (callback.contains("SUCCESS")) {
			Assert.fail("validation allowed illegal access");
		}
	}
	
	@Test
	public void rejectLogin () {
		ValidationReceiver validator = new ValidationReceiver(con);
		String callback = validator.validate("random", "admin", (byte)0);
		if (callback.contains("SUCCESS")) {
			Assert.fail("validation didn't reject invalid login");
		}
	}
	
	@Test
	public void rejectPassword () {
		ValidationReceiver validator = new ValidationReceiver(con);
		String callback = validator.validate("admin", "random", (byte)0);
		if (callback.contains("SUCCESS")) {
			Assert.fail("validation didn't reject invalid password");
		}
	}
	
	@AfterClass
	public static void tearDown() {
		try {
			if (con != null) {
				DbBatch.dropDatabase(con);
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
