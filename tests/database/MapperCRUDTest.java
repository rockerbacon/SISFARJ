package database;

import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.*;
import org.junit.jupiter.api.Assertions;

public class MapperCRUDTest {
	
	private static class TestTableCreationForeign implements Mapper.Managed {
		
		@Mapper.PrimaryKey
		int tableCreationForeignId;
		
		public TestTableCreationForeign() {}
		
		public TestTableCreationForeign(int id) {
			this.tableCreationForeignId = id;
		}
		
		public int getTableCreationForeignId() {
			return tableCreationForeignId;
		}

		@Override
		public List<String> getTableDependancies () {
			ArrayList<String> tables = new ArrayList<String>(1);
			tables.add(TestTableCreationForeign.class.getSimpleName());
			return tables;
		}
		
		@Override
		public Object newInstance () {
			return new TestTableCreationForeign();
		}
	}

	private static class TestTableCreation implements Mapper.Managed {
		
		@Mapper.PrimaryKey
		int tableCreationid1;
		@Mapper.PrimaryKey
		short tableCreationid2;
		
		@Mapper.StringSize(fixed=true, size=10)
		String tableCreationFixedStr;
		
		@Mapper.StringSize(size=15)
		String tableCreationVariableStr;
		
		String tableCreationUndefStr;
		
		@Mapper.ForeignKey(references="TestTableCreationForeign")
		int tableCreationForeignId;
		
		private TestTableCreation() {}
		
		public TestTableCreation (int tableCreationid1, short tableCreationid2, String tableCreationFixedStr, String tableCreationVariableStr, String tableCreationUndefStr, int tableCreationForeignId) {
			this.tableCreationid1 = tableCreationid1;
			this.tableCreationid2 = tableCreationid2;
			this.tableCreationFixedStr = tableCreationFixedStr;
			this.tableCreationVariableStr = tableCreationVariableStr;
			this.tableCreationUndefStr = tableCreationUndefStr;
			this.tableCreationForeignId = tableCreationForeignId;
		}
		
		
		public int getTableCreationid1() {
			return tableCreationid1;
		}

		public short getTableCreationid2() {
			return tableCreationid2;
		}

		public String getTableCreationFixedStr() {
			return tableCreationFixedStr;
		}

		public String getTableCreationVariableStr() {
			return tableCreationVariableStr;
		}

		public String getTableCreationUndefStr() {
			return tableCreationUndefStr;
		}

		public int getTableCreationForeignId() {
			return tableCreationForeignId;
		}

		@Override
		public List<String> getTableDependancies () {
			ArrayList<String> tables = new ArrayList<String>(1);
			tables.add(TestTableCreation.class.getSimpleName());
			return tables;
		}
		
		@Override
		public Object newInstance () {
			return new TestTableCreation();
		}
	}
	
	@Test
	public void create () {
		int created = 0;
		Connection con = null;
		try {
			con = DbConnection.connect();
			Mapper mapper = new Mapper(con);
			
			mapper.create(TestTableCreationForeign.class);
			created++;
			
			//caso tabela tanha sido criada incorretamente tentar acessar seus membros gerara uma excecao
			con.createStatement().executeQuery("SELECT * FROM "+TestTableCreationForeign.class.getSimpleName()+" WHERE tableCreationForeignId = 0");
			
			mapper.create(TestTableCreation.class);
			created++;
			
			con.createStatement().executeQuery("SELECT * FROM "+TestTableCreation.class.getSimpleName()+" WHERE tableCreationId1 = 0 AND\n"
																											+ "tableCreationid2 = 0 AND\n"
																											+ "tableCreationFixedStr = 'test' AND\n"
																											+ "tableCreationVariableStr = 'test' AND\n"
																											+ "tableCreationUndefStr = 'test' AND\n"
																											+ "tableCreationForeignId = 0");
			
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail();
		} finally {
			try {
				switch (created) {
					case 2:
						con.createStatement().executeUpdate("DROP TABLE "+TestTableCreation.class.getSimpleName());
					case 1:
						con.createStatement().executeUpdate("DROP TABLE "+TestTableCreationForeign.class.getSimpleName());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void insert () {
		Connection con = null;
		int created = 0;
		try {
			con = DbConnection.connect();
			Mapper mapper = new Mapper(con);
			
			//criar tableas
			con.createStatement().executeUpdate("CREATE TABLE "+TestTableCreationForeign.class.getSimpleName()+" ( tableCreationForeignId INTEGER,\n"
																												+"\tPRIMARY KEY (tableCreationForeignId)\n)");
			created++;
			con.createStatement().executeUpdate("CREATE TABLE "+TestTableCreation.class.getSimpleName()+" ("
																										+"\n\t tableCreationid1 INTEGER"
																										+",\n\t tableCreationid2 SMALLINT"
																										+",\n\t tableCreationFixedStr CHAR(10)"
																										+",\n\t tableCreationVariableStr VARCHAR(15)"
																										+",\n\t tableCreationUndefStr VARCHAR"
																										+",\n\t tableCreationForeignId INT"
																										+",\n\t PRIMARY KEY (tableCreationid1, tableCreationid2)"
																										+",\n\t FOREIGN KEY (tableCreationForeignId) REFERENCES "+TestTableCreationForeign.class.getSimpleName()+"(tableCreationForeignId)\n)");
			created++;
			
			//gerar dados
			TestTableCreationForeign tf1 = new TestTableCreationForeign(0);
			TestTableCreation tc1 = new TestTableCreation(1, (short)2, "test", "test2", "test3", 0);
			TestTableCreation tc2 = new TestTableCreation(2, (short)1, "test", "test2", "test6", 0);
			ArrayList<TestTableCreation> objs = new ArrayList<TestTableCreation>(2);
			Iterator<TestTableCreation> it;
			objs.add(tc1);
			objs.add(tc2);
			
			mapper.create(tf1);
			mapper.create(tc1);
			mapper.create(tc2);
			
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM "+TestTableCreation.class.getSimpleName()+" WHERE tableCreationVariableStr = 'test2' ORDER BY 1 ASC");
			
			//verificar retorno dos dados
			rs.next();
			it = objs.iterator();
			while (!rs.isAfterLast() && it.hasNext()) {
				TestTableCreation tc = it.next();
				Assertions.assertEquals(tc.getTableCreationid1(), rs.getInt(1));
				Assertions.assertEquals(tc.getTableCreationid2(), rs.getShort(2));
				Assertions.assertEquals(tc.getTableCreationFixedStr(), rs.getString(3).trim());
				Assertions.assertEquals(tc.getTableCreationVariableStr(), rs.getString(4));
				Assertions.assertEquals(tc.getTableCreationUndefStr(), rs.getString(5));
				Assertions.assertEquals(tc.getTableCreationForeignId(), rs.getInt(6));
				rs.next();
			}
			
			if (it.hasNext()) {
				Assert.fail("Not all objects where inserted");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail();
		} finally {
			try {
				switch (created) {
					case 2:
						con.createStatement().executeUpdate("DROP TABLE "+TestTableCreation.class.getSimpleName());
					case 1:
						con.createStatement().executeUpdate("DROP TABLE "+TestTableCreationForeign.class.getSimpleName());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
