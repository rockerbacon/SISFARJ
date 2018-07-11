package database;

import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.*;

import database.Mapper.Filter;

public class MapperMockerCRUDTest {
	
	@Mapper.UseTables({TestTableForeign.TNAME})
	private static class TestTableForeign {
		
		public static final String TNAME = "TEST_TABLE_FOREIGN";
		
		
		@Mapper.PrimaryKey
		int tableCreationForeignId;
		
		int value;
		
		public TestTableForeign() {}
		
		public TestTableForeign(int id, int value) {
			this.tableCreationForeignId = id;
			this.value = value;
		}
		
		public int getTableCreationForeignId() {
			return this.tableCreationForeignId;
		}
		
		public int getValue () {
			return this.value;
		}
	}

	@Mapper.UseTables({TestTable.TNAME})
	private static class TestTable {
		
		public static final String TNAME = "TEST_TABLE";
		
		
		@Mapper.PrimaryKey
		int tableCreationid1;
		@Mapper.PrimaryKey
		short tableCreationid2;
		
		@Mapper.StringSize(fixed=true, size=10)
		String tableCreationFixedStr;
		
		@Mapper.StringSize(size=15)
		String tableCreationVariableStr;
		
		String tableCreationUndefStr;
		
		@Mapper.ForeignKey(references=TestTableForeign.TNAME)
		int tableCreationForeignId;
		
		private TestTable() {}
		
		public TestTable (int tableCreationid1, short tableCreationid2, String tableCreationFixedStr, String tableCreationVariableStr, String tableCreationUndefStr, int tableCreationForeignId) {
			this.tableCreationid1 = tableCreationid1;
			this.tableCreationid2 = tableCreationid2;
			this.tableCreationFixedStr = tableCreationFixedStr;
			this.tableCreationVariableStr = tableCreationVariableStr;
			this.tableCreationUndefStr = tableCreationUndefStr;
			this.tableCreationForeignId = tableCreationForeignId;
		}
		
	}
	
	
	@Mapper.UseTables({TestTable.TNAME, TestTableForeign.TNAME})
	private static class TestTableDetailed {
		
		
		int tableCreationid1;
		
		short tableCreationid2;
		
		String tableCreationFixedStr;
		
		String tableCreationVariableStr;
		
		String tableCreationUndefStr;
		
		int tableCreationForeignId;
		
		int value;
		
		public TestTableDetailed () {}
		
	}
	
	@Test
	public void create () {
		int created = 0;
		Connection con = null;
		try {
			con = DbConnection.mock();
			Mapper mapper = new Mapper(con);
			
			mapper.create(TestTableForeign.class);
			created++;
			
			//caso tabela tanha sido criada incorretamente tentar acessar seus membros gerara uma excecao
			con.createStatement().executeQuery("SELECT * FROM "+TestTableForeign.TNAME+" WHERE tableCreationForeignId = 0 AND value = 0");
			
			mapper.create(TestTable.class);
			created++;
			
			con.createStatement().executeQuery("SELECT * FROM "+TestTable.TNAME+" WHERE tableCreationId1 = 0 AND\n"
																											+ "tableCreationid2 = 0 AND\n"
																											+ "tableCreationFixedStr = 'test' AND\n"
																											+ "tableCreationVariableStr = 'test' AND\n"
																											+ "tableCreationUndefStr = 'test' AND\n"
																											+ "tableCreationForeignId = 0");
			
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} finally {
			try {
				switch (created) {
					case 2:
						con.createStatement().executeUpdate("DROP TABLE "+TestTable.TNAME);
					case 1:
						con.createStatement().executeUpdate("DROP TABLE "+TestTableForeign.TNAME);
				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void insert () {
		Connection con = null;
		int created = 0;
		ResultSet rs = null;
		try {
			con = DbConnection.mock();
			Mapper mapper = new Mapper(con);
			
			//criar tableas
			con.createStatement().executeUpdate("CREATE TABLE "+TestTableForeign.TNAME+" ( tableCreationForeignId INTEGER,\n"
																												+"\tvalue INTEGER,\n"
																												+"\tPRIMARY KEY (tableCreationForeignId)\n)");
			created++;
			con.createStatement().executeUpdate("CREATE TABLE "+TestTable.TNAME+" ("
																										+"\n\t tableCreationid1 INTEGER"
																										+",\n\t tableCreationid2 SMALLINT"
																										+",\n\t tableCreationFixedStr CHAR(10)"
																										+",\n\t tableCreationVariableStr VARCHAR(15)"
																										+",\n\t tableCreationUndefStr VARCHAR(255)"
																										+",\n\t tableCreationForeignId INT"
																										+",\n\t PRIMARY KEY (tableCreationid1, tableCreationid2)"
																										+",\n\t FOREIGN KEY (tableCreationForeignId) REFERENCES "+TestTableForeign.TNAME+"(tableCreationForeignId)\n)");
			created++;
			
			//gerar dados
			TestTableForeign tf1 = new TestTableForeign(0, 2);
			TestTable tc1 = new TestTable(1, (short)2, "test", "test2", "test3", 0);
			TestTable tc2 = new TestTable(2, (short)1, "test", "test2", "test6", 0);
			ArrayList<TestTable> objs = new ArrayList<TestTable>(2);
			Iterator<TestTable> it;
			objs.add(tc1);
			objs.add(tc2);
			
			mapper.create(tf1);
			mapper.create(tc1);
			mapper.create(tc2);
			
			rs = con.createStatement().executeQuery("SELECT * FROM "+TestTable.TNAME+" WHERE tableCreationVariableStr = 'test2' ORDER BY 1 ASC");
			
			//verificar retorno dos dados
			it = objs.iterator();
			while (rs.next() && it.hasNext()) {
				TestTable tc = it.next();
				Assert.assertEquals(tc.tableCreationid1, rs.getInt(1));
				Assert.assertEquals(tc.tableCreationid2, rs.getShort(2));
				Assert.assertEquals(tc.tableCreationFixedStr, rs.getString(3).trim());
				Assert.assertEquals(tc.tableCreationVariableStr, rs.getString(4));
				Assert.assertEquals(tc.tableCreationUndefStr, rs.getString(5));
				Assert.assertEquals(tc.tableCreationForeignId, rs.getObject(6));
			}
			
			if (it.hasNext()) {
				Assert.fail("Not all objects where inserted");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
				switch (created) {
					case 2:
						con.createStatement().executeUpdate("DROP TABLE "+TestTable.TNAME);
					case 1:
						con.createStatement().executeUpdate("DROP TABLE "+TestTableForeign.TNAME);
				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void read () {
		Connection con = null;
		int created = 0;
		try {
			con = DbConnection.mock();
			Mapper mapper = new Mapper(con);
			List<TestTableDetailed> queryResult;

			//criar tableas
			con.createStatement().executeUpdate("CREATE TABLE "+TestTableForeign.TNAME+" ( tableCreationForeignId INTEGER,\n"
																												+"\tvalue INTEGER,\n"
																												+"\tPRIMARY KEY (tableCreationForeignId)\n)");
			created++;
			con.createStatement().executeUpdate("CREATE TABLE "+TestTable.TNAME+" ("
																										+"\n\t tableCreationid1 INTEGER"
																										+",\n\t tableCreationid2 SMALLINT"
																										+",\n\t tableCreationFixedStr CHAR(10)"
																										+",\n\t tableCreationVariableStr VARCHAR(15)"
																										+",\n\t tableCreationUndefStr VARCHAR(255)"
																										+",\n\t tableCreationForeignId INT"
																										+",\n\t PRIMARY KEY (tableCreationid1, tableCreationid2)"
																										+",\n\t FOREIGN KEY (tableCreationForeignId) REFERENCES "+TestTableForeign.TNAME+"(tableCreationForeignId)\n)");
			created++;
			
			//inserir dados na tabela
			con.createStatement().executeUpdate("INSERT INTO "+TestTableForeign.TNAME+" VALUES (0, 50)");
			con.createStatement().executeUpdate("INSERT INTO "+TestTable.TNAME+" VALUES (1, 2, 'test', 'test2', 'test3', 0)");
			con.createStatement().executeUpdate("INSERT INTO "+TestTable.TNAME+" VALUES (4, 2, 'test4', 'test2', 'test5', 0)");
			
			queryResult = mapper.read(-1, TestTableDetailed.class, new Filter("tableCreationid1", ">", 0), new Filter("tableCreationVariableStr", "=", "test2"));
			//queryResult = mapper.read(0, TestTableDetailed.class);
			if (queryResult.isEmpty()) {
				Assert.fail("Query did not return any results");
			} else {
				queryResult.sort((o1, o2) -> ((TestTableDetailed)o1).tableCreationid1-((TestTableDetailed)o2).tableCreationid2);
				Iterator<TestTableDetailed> it = queryResult.iterator();
				TestTableDetailed row;
				
				row = it.next();
				Assert.assertEquals(1, row.tableCreationid1);
				Assert.assertEquals(2, row.tableCreationid2);
				Assert.assertEquals("test", row.tableCreationFixedStr.trim());
				Assert.assertEquals("test2", row.tableCreationVariableStr);
				Assert.assertEquals("test3", row.tableCreationUndefStr);
				Assert.assertEquals(0, row.tableCreationForeignId);
				Assert.assertEquals(50, row.value);
				
				row = it.next();
				Assert.assertEquals(4, row.tableCreationid1);
				Assert.assertEquals(2, row.tableCreationid2);
				Assert.assertEquals("test4", row.tableCreationFixedStr.trim());
				Assert.assertEquals("test2", row.tableCreationVariableStr);
				Assert.assertEquals("test5", row.tableCreationUndefStr);
				Assert.assertEquals(0, row.tableCreationForeignId);
				Assert.assertEquals(50, row.value);
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} finally {
			try {
				switch (created) {
					case 2:
						con.createStatement().executeUpdate("DROP TABLE "+TestTable.TNAME);
					case 1:
						con.createStatement().executeUpdate("DROP TABLE "+TestTableForeign.TNAME);
				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void update () {
		Connection con = null;
		int created = 0;
		ResultSet rs = null;
		try {
			con = DbConnection.mock();
			Mapper mapper = new Mapper(con);
			
			//criar tabela
			con.createStatement().executeUpdate("CREATE TABLE "+TestTableForeign.TNAME+" ( tableCreationForeignId INTEGER,\n"
																						+"\tvalue INTEGER,\n"
																						+"\tPRIMARY KEY (tableCreationForeignId)\n)");
			created++;
			
			con.createStatement().executeUpdate("CREATE TABLE "+TestTable.TNAME+" ("
																				+"\n\t tableCreationid1 INTEGER"
																				+",\n\t tableCreationid2 SMALLINT"
																				+",\n\t tableCreationFixedStr CHAR(10)"
																				+",\n\t tableCreationVariableStr VARCHAR(15)"
																				+",\n\t tableCreationUndefStr VARCHAR(255)"
																				+",\n\t tableCreationForeignId INT"
																				+",\n\t PRIMARY KEY (tableCreationid1, tableCreationid2)"
																				+",\n\t FOREIGN KEY (tableCreationForeignId) REFERENCES "+TestTableForeign.TNAME+"(tableCreationForeignId)\n)");
			created++;
			
			//inserir dados na tabela
			con.createStatement().executeUpdate("INSERT INTO "+TestTableForeign.TNAME+" VALUES (0, 50)");
			con.createStatement().executeUpdate("INSERT INTO "+TestTable.TNAME+" VALUES (1, 2, 'test', 'test2', 'test3', 0)");
			
			TestTable tst = new TestTable(1, (short)2, "notest", "notest1", "notest3", 0);
			
			mapper.update(tst);
			
			rs = con.createStatement().executeQuery("SELECT * FROM "+TestTable.TNAME);
			
			rs.next();
			Assert.assertEquals(tst.tableCreationid1, rs.getInt(1));
			Assert.assertEquals(tst.tableCreationid2, rs.getShort(2));
			Assert.assertEquals(tst.tableCreationFixedStr, rs.getString(3).trim());
			Assert.assertEquals(tst.tableCreationVariableStr, rs.getString(4));
			Assert.assertEquals(tst.tableCreationUndefStr, rs.getString(5));
			Assert.assertEquals(tst.tableCreationForeignId, rs.getObject(6));
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
				switch (created) {
					case 2:
						con.createStatement().executeUpdate("DROP TABLE "+TestTable.TNAME);
					case 1:
						con.createStatement().executeUpdate("DROP TABLE "+TestTableForeign.TNAME);

				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void delete () {
		Connection con = null;
		int created = 0;
		ResultSet rs = null;
		try {
			con = DbConnection.mock();
			Mapper mapper = new Mapper(con);
			
			//criar tabela
			con.createStatement().executeUpdate("CREATE TABLE "+TestTableForeign.TNAME+" ( tableCreationForeignId INTEGER,\n"
																						+"\tvalue INTEGER,\n"
																						+"\tPRIMARY KEY (tableCreationForeignId)\n)");
			created++;
			
			con.createStatement().executeUpdate("CREATE TABLE "+TestTable.TNAME+" ("
																				+"\n\t tableCreationid1 INTEGER"
																				+",\n\t tableCreationid2 SMALLINT"
																				+",\n\t tableCreationFixedStr CHAR(10)"
																				+",\n\t tableCreationVariableStr VARCHAR(15)"
																				+",\n\t tableCreationUndefStr VARCHAR(255)"
																				+",\n\t tableCreationForeignId INT"
																				+",\n\t PRIMARY KEY (tableCreationid1, tableCreationid2)"
																				+",\n\t FOREIGN KEY (tableCreationForeignId) REFERENCES "+TestTableForeign.TNAME+"(tableCreationForeignId)\n)");
			created++;
			
			//inserir dados na tabela
			con.createStatement().executeUpdate("INSERT INTO "+TestTableForeign.TNAME+" VALUES (0, 50)");
			con.createStatement().executeUpdate("INSERT INTO "+TestTable.TNAME+" VALUES (1, 2, 'test', 'test2', 'test3', 0)");
			con.createStatement().executeUpdate("INSERT INTO "+TestTable.TNAME+" VALUES (4, 2, 'test4', 'test2', 'test5', 0)");
			
			TestTable tst = new TestTable(1, (short)2, "notest", "notest1", "notest3", 0);
			
			mapper.delete(tst);
			
			rs = con.createStatement().executeQuery("SELECT * FROM "+TestTable.TNAME);
			
			rs.next();
			if (rs.next()) {
				Assert.fail("Row was not deleted from table");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
				switch (created) {
					case 2:
						con.createStatement().executeUpdate("DROP TABLE "+TestTable.TNAME);
					case 1:
						con.createStatement().executeUpdate("DROP TABLE "+TestTableForeign.TNAME);

				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void drop () {
		int created = 0;
		Connection con = null;
		try {
			con = DbConnection.mock();
			Mapper mapper = new Mapper(con);
			
			//criar tabela
			con.createStatement().executeUpdate("CREATE TABLE "+TestTableForeign.TNAME+" ( tableCreationForeignId INTEGER,\n"
																						+"\tvalue INTEGER,\n"
																						+"\tPRIMARY KEY (tableCreationForeignId)\n)");
			created++;
			
			mapper.delete(TestTableForeign.class);
			created--;
			
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} finally {
			try {
				switch (created) {
					case 1:
						con.createStatement().executeUpdate("DROP TABLE "+TestTableForeign.TNAME);
				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
