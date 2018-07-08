package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mapper {
	
	public @interface PrimaryKey {}
	public @interface ForeignKey {
		String references();
		String on() default "";
	}
	public @interface StringSize {
		boolean fixed() default false;
		int size() default -1;
	}
	
	private Connection connection;
	
	/**
	 * Inicializa um mapper para a conexao especificada
	 * @param connection: Conexao utilizada pelo mapper
	 */
	public Mapper (Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * Interface que permite compatibilidade com funcoes de mapeamento
	 */
	public static interface Managed {
		/**
		 * @return: Lista com nomes de todas as tabelas que armazenem as informacoes contidas no objeto
		 */
		public List<String> getTableDependancies ();
		/**
		 * @return: Nova instancia de objeto a ser mapeado
		 */
		public Object newInstance();
	}
	
	/**
	 * Interface para implementacao de funcoes de filtro
	 */
	public static class Filter {
		private String paramFilter;
		private Object paramValue;
		/**
		 * @param attr: Nome do atributo do objeto a ser filtrado
		 * @param sqlOp: Operador sql usado na comparacao dos valores
		 * @param value: Valor utilizado para comparar com atributo
		 */
		public Filter (String attr, String sqlOp, Object value) {
			this.paramFilter = attr+sqlOp+"?";
			this.paramValue = value;
		}
		
		public String getParamFilter () { return this.paramFilter; }
		public Object getParamValue () { return this.paramValue; }
	}
	
	/**
	 * Mapeia o tipo de um campo de uma classe para um tipo em SQL
	 * @param field: Campo a ser mapeado
	 * @return: String representando tipo equivalente em SQL
	 * @throws IllegalArgumentException
	 */
	private static String mapToSqlType (Field field) throws IllegalArgumentException {
		String className = field.getType().getSimpleName();
		String sqlName;
		switch (className) {
		
			case "String":
				StringSize annotation = field.getDeclaredAnnotation(StringSize.class);
				if (annotation == null) {
					sqlName = "VARCHAR";
				} else {
					if (annotation.fixed()) {
				
						if (annotation.size() == -1) {
							throw new IllegalArgumentException("Field "+field.getName()+" is annotated as String of fixed size but has no size defined");
						}
					
						sqlName = "CHAR("+annotation.size()+")";
						
					} else {
						sqlName = "VARCHAR";
						if (annotation.size() != -1) {
							sqlName += "("+annotation.size()+")";
						}
					}
				}
			break;
		
			case "char":
			case "Char":
				sqlName = "CHARACTER";
			break;
			
			case "boolean":
			case "Boolean":
				sqlName = "BOOLEAN";
			break;
			
			case "short":
			case "Short":
				sqlName = "SMALLINT";
			break;
			
			case "int":
			case "Integer":
				sqlName = "INTEGER";
			break;
			
			case "long":
			case "Long":
				sqlName = "BIGINT";
			break;
			
			case "float":
			case "Float":
				sqlName = "FLOAT";
			break;
			
			case "double":
			case "Double":
				sqlName = "DOUBLE PRECISION";
			break;
			
			case "Date":
				sqlName = "TIMESTAMP";
			break;
			
			default:
				throw new IllegalArgumentException(className+" cannot be mapped to a sql type");
			
		}
		return sqlName;
	}
	
	/**
	 * Gera prepared statement de acordo com os filtros
	 * @param query: string de query ja construida
	 * @param parameters: array com parametros ordenados
	 * @return
	 */
	private PreparedStatement generateQuery (String query, List<Object> parameters) throws SQLException {
		int i = 0;
		PreparedStatement statement = this.connection.prepareStatement(query);
		
		Iterator<Object> it = parameters.iterator();
		while (it.hasNext()) {
			statement.setObject(i++, it.next());
		}
		
		return statement;
	}
	
	/**
	 * Cria tabela para classe
	 * @param table: Classe que tera sua tabela criada
	 * @throws SQLException
	 * @throws IllegalArgumentException: Caso tabela nao possua campos ou algum dos campos nao possa ser mapeado
	 */
	public void create (Class<? extends Mapper.Managed> table) throws SQLException, IllegalArgumentException {
		
		StringBuilder query = new StringBuilder();
		StringBuilder primaryKeys = new StringBuilder();
		Field[] fields = table.getDeclaredFields();
		ForeignKey fk;
		
		if (fields.length == 0) {
			throw new IllegalArgumentException("Class "+table.getSimpleName()+" has no fields");
		}
		
		query.append("CREATE TABLE ");
		query.append(table.getName());
		query.append(" (\n");
		
		primaryKeys.append("PRIMARY KEY(");
		
		for (Field field : fields) {
			query.append("\t");
			query.append(field.getName());
			query.append(' ');
			query.append(Mapper.mapToSqlType(field));
			query.append(",\n");
			
			//if annotated as primary key add to primary keys
			if (field.getDeclaredAnnotation(PrimaryKey.class) != null) {
				primaryKeys.append(field.getName());
				primaryKeys.append(", ");
			}
			
			//if annoted as foreign key add constraint
			if ( (fk = field.getDeclaredAnnotation(ForeignKey.class)) != null) {
				query.append("\tFOREIGN KEY(");
				query.append(field.getName());
				query.append(") REFERENCES ");
				query.append(fk.references());
				query.append("(");
				if (fk.on().isEmpty())
					query.append(field.getName());
				else
					query.append(fk.on());
				query.append("),\n");
			}
		}
		//remove trailling characters
		primaryKeys.setLength(primaryKeys.length()-2);
		
		primaryKeys.append(")\n");
		query.append(primaryKeys);
		query.append("\n)");
		
		this.connection.prepareStatement(query.toString()).executeUpdate();
		
	}
	
	/**
	 * Insere objeto na base de dados
	 * @param object: objeto a ser inserido
	 * @throws IllegalArgumentException: If the object's class was not correctly created
	 * @throws SQLException
	 */
	public void create(Mapper.Managed object) throws IllegalArgumentException, SQLException {
		StringBuilder query;
		Class<?> objClass = object.getClass();
		List<String> tables = object.getTableDependancies();
		Field[] fields = objClass.getDeclaredFields();
		ArrayList<Object> values = new ArrayList<Object>(fields.length);
		String table;
		
		//check consistency
		if (tables.size() == 0) {
			throw new IllegalArgumentException("manager has specified no table dependancies");
		} else if (tables.size() != 1) {
			throw new IllegalArgumentException("manager has specified multiple table dependancies and insertion can only be done on one table at a time");
		}
		if (fields.length == 0) {
			throw new IllegalArgumentException("object has no attributes");
		}
		
		table = tables.get(0);
		try {
			query = new StringBuilder();
			
			query.append("INSERT INTO TABLE ");
			query.append(table);
			query.append(" VALUES (");
			
			query.append('?');
			values.add(fields[0].get(object));
			for (int i = 1; i < fields.length; i++) {
				query.append(",?");
				values.add(fields[i].get(object));
			}
			
			query.append(')');
			
			this.generateQuery(query.toString(), values).executeUpdate();
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Mapeia classe de acordo com os filtros executando query no formato:
	 * 		SELECT	colum1, colum2, ..., columN
	 * 		FROM	table1 NATURAL JOIN table2 NATURAL JOIN ... tableN
	 * 		WHERE	condition1 AND condition2 AND ... conditionN
	 * @param maxRows: Numero maximo de linhas a serem trazidas, 0 indica que nao ha limite
	 * @param factory: Fabrica de objetos que instancia objetos a serem mapeados
	 * @param filter: Filtro obrigatorio
	 * @param others: Filtros opcionais
	 */
	public LinkedList<Object> read(int maxRows, Mapper.Managed factory, Filter filter, Filter... others) throws NoSuchFieldException, IllegalArgumentException, SQLException {
		
		Object prop = factory.newInstance();
		Class<?> outputClass = prop.getClass();
		Field[] fields = outputClass.getDeclaredFields();
		List<String> tables = factory.getTableDependancies();
		
		StringBuilder query = new StringBuilder();
		ArrayList<Object> params = new ArrayList<Object>(others.length+1);
		ResultSet queryResult;
		Iterator<String> it;
		
		LinkedList<Object> rows;
		
		if (fields.length == 0) {
			throw new IllegalArgumentException("object generated by factory has no attributes");
		} else if (tables.size() == 0) {
			throw new IllegalArgumentException("factory has not declared table dependancies");
		}
		//checa se todos os filtros estao validos, excecao sera lancada se classe nao possuir algum dos parametros do filtro
		outputClass.getDeclaredField(filter.getParamFilter());
		for (Filter f : others) {
			outputClass.getDeclaredField(f.getParamFilter());
		}
		
		//preencher select da query
		query.append("SELECT\t");
		query.append(fields[0]);
		for (int i = 0; i < fields.length; i++) {
			query.append(",\n\t\t\t");
			query.append(fields[i].getName());
		}
		
		//preencher from da query
		it = tables.iterator();
		query.append("\nFROM\t");
		query.append(it.next());
		while (it.hasNext()) {
			query.append(" NATURAL JOIN\n\t\t");
			query.append(it.next());
		}
		
		//preencher where da query e parametros
		query.append("\nWHERE\t");
		query.append(filter.getParamFilter());
		
		params.add(filter.getParamValue());
		
		for (int i = 0; i < others.length; i++) {
			query.append(" AND\n\t\t");
			query.append(others[i].getParamFilter());
			
			params.add(others[i].getParamValue());
		}
		
		queryResult = this.generateQuery(query.toString(), params).executeQuery();
		rows = new LinkedList<Object>();
		
		try {
			queryResult.next();
			while (!queryResult.isAfterLast() && maxRows != 0) {
				//instanciar objeto da linha atual
				Object obj = factory.newInstance();
				for (Field field : fields) {
					field.set(obj, queryResult.getObject(field.getName()));
				}
				
				rows.add(obj);
				
				queryResult.next();
				maxRows--;
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return rows;
	}
	
	/**
	 * Atualiza objeto na base de dados
	 * Objeto sera procurado na base de acordo com suas chaves primarias e entao seus valores serao modificados
	 * @param object: objeto a ser atualizado com os valores a serem atualizados
	 */
	public void update (Mapper.Managed object) throws SQLException {
		StringBuilder query = new StringBuilder();
		Class<?> objClass = object.getClass();
		Field[] fields = objClass.getDeclaredFields();
		LinkedList<Field> pks = new LinkedList<Field>();
		ArrayList<Object> params = new ArrayList<Object>(fields.length);
		Iterator<Field> it;
		Field field;
		
		query.append("UPDATE ");
		query.append(objClass.getSimpleName());
		query.append(" SET\n");
		
		try {
		
			query.append("\n\t\t");
			query.append(fields[0].getName());
			query.append("=?");
			params.add(fields[0].get(object));
			for (int i = 1; i < fields.length; i++) {
				if (fields[i].getDeclaredAnnotation(PrimaryKey.class) != null) {
					pks.add(fields[i]);
				} else {
					query.append(",\n\t\t");
					query.append(fields[i].getName());
					query.append("=?");
					params.add(fields[i].get(object));
				}
			}
			
			it = pks.iterator();
			query.append("\nWHERE\n\t\t");
			query.append(it.next().getName());
			query.append("=?");
			while (it.hasNext()) {
				field = it.next();
				query.append(" AND\n\t\t");
				query.append(field.getName());
				query.append("=?");
				params.add(field.get(object));
			}
			
			this.generateQuery(query.toString(), params).executeUpdate();
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Executa drop na tabela da classe especificada
	 * @param table: Classe que utiliza a tabela a ser dropada
	 * @throws SQLException
	 */
	public void delete (Class<? extends Mapper.Managed> table) throws SQLException {
		this.connection.prepareStatement("DROP TABLE "+table.getSimpleName()).executeUpdate();
	}
	/**
	 * Exclui objeto da base de dados
	 * @param object: Objeto a ser excluido
	 */
	public void delete (Mapper.Managed object) throws SQLException {
		StringBuilder query = new StringBuilder();
		Class<?> objClass = object.getClass();
		Field[] fields = objClass.getDeclaredFields();
		LinkedList<Object> params = new LinkedList<Object>();
		
		query.append("DELETE FROM ");
		query.append(objClass.getSimpleName());
		query.append(" WHERE\n");
		
		try {
			for (Field field : fields) {
				if (field.getDeclaredAnnotation(PrimaryKey.class) != null) {
					query.append("\t\t");
					query.append(field.getName());
					query.append("=? AND\n");
					params.add(field.get(object));
				}
			}
			//remove trailing characters
			query.setLength(query.length()-4);
			
			this.generateQuery(query.toString(), params).executeUpdate();
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
}
