package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mapper {
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface PrimaryKey {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface ForeignKey {
		String references();
		String on() default "";
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface StringSize {
		boolean fixed() default false;
		int size() default -1;
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface UseTables {
		String[] value();
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
	 * @throws IllegalArgumentException se compo nao pode ser mapeado
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
	 * @param parameters: lista com parametros ordenados
	 * @return
	 */
	private PreparedStatement generateQuery (String query, List<Object> parameters) throws SQLException {
		int i = 0;
		PreparedStatement statement = this.connection.prepareStatement(query);
		
		if (parameters != null) {
			Iterator<Object> it = parameters.iterator();
			while (it.hasNext()) {
				statement.setObject(++i, it.next());
			}
		}
		
		return statement;
	}
	
	private static String[] getDeclaredTables (Class<?> c) throws IllegalArgumentException {
		UseTables declTables = c.getDeclaredAnnotation(UseTables.class);
		if (declTables == null || declTables.value().length == 0) {
			throw new IllegalArgumentException("Class "+c.getName()+" has no declared tables");
		}
		return declTables.value();
	}
	
	private static Object retrieveData (ResultSet resultSet, String columnLabel, Class<?> returnType) throws SQLException, IllegalArgumentException {
		Object data = resultSet.getObject(columnLabel);
		String dataType = data.getClass().getSimpleName();
		String returnTypeName = returnType.getSimpleName();
		
		switch (dataType) {
			case "Integer":
				Integer intVal = (Integer)data;
				switch (returnTypeName) {
					case "byte":
					case "Byte":
						data = new Byte(intVal.byteValue());
					break;
					
					case "short":
					case "Short":
						data = new Short(intVal.shortValue());
					break;
					
					case "Integer":
					case "int":
					break;
					
					case "long":
					case "Long":
						data = new Long(intVal.longValue());
					break;
					
					default:
						throw new IllegalArgumentException("Cannot convert type "+dataType+" to "+returnTypeName);
					
				}
			break;
			
			case "Long":
				Long longVal = (Long)data;
				switch (returnTypeName) {
					case "byte":
					case "Byte":
						data = new Byte(longVal.byteValue());
					break;
					
					case "short":
					case "Short":
						data = new Short(longVal.shortValue());
					break;
					
					case "Integer":
					case "int":
						data = new Integer(longVal.intValue());
					break;
					
					case "long":
					case "Long":
					break;
					
					default:
						throw new IllegalArgumentException("Cannot convert type "+dataType+" to "+returnTypeName);
					
				}
			break;
			
			case "Float":
				Float floatVal = (Float)data;
				switch (returnTypeName) {
					case "float":
					case "Float":
					break;
					
					case "double":
					case "Double":
						data = new Double(floatVal.doubleValue());
					break;
					
					default:
						throw new IllegalArgumentException("Cannot convert type "+dataType+" to "+returnTypeName);
				}
			break;
			
			case "Double":
				Double doubleVal = (Double)data;
				switch (returnTypeName) {
					case "float":
					case "Float":
						data = new Float(doubleVal.floatValue());
					break;
					
					case "double":
					case "Double":
					break;
					
					default:
						throw new IllegalArgumentException("Cannot convert type "+dataType+" to "+returnTypeName);
				}
			break;
			
			case "String":
			case "Date":
			case "Char":
			case "char":
			case "Boolean":
			case "boolean":
				if (!dataType.toUpperCase().equals(returnTypeName.toUpperCase())) {
					throw new IllegalArgumentException("Cannot convert type "+dataType+" to "+returnTypeName);
				}
			break;
		}
		
		return data;
	}
	
	/**
	 * Cria tabela para classe
	 * @param table: Classe que tera sua tabela criada
	 * @throws SQLException
	 * @throws IllegalArgumentException: Caso tabela nao possua campos ou algum dos campos nao possa ser mapeado
	 */
	public void create (Class<?> table) throws SQLException, IllegalArgumentException {
		
		StringBuilder query = new StringBuilder();
		StringBuilder primaryKeys = new StringBuilder();
		Field[] fields = table.getDeclaredFields();
		String[] tables = Mapper.getDeclaredTables(table);
		ForeignKey fk = null;
		
		//check consistency
		if (fields.length == 0) {
			throw new IllegalArgumentException("Class "+table.getName()+" has no fields");
		}
		if (tables.length != 1) {
			System.out.println(tables.length);
			throw new IllegalArgumentException("Class "+table.getName()+" must only use one table to be created");
		}
		
		query.append("CREATE TABLE ");
		query.append(tables[0]);
		query.append(" (\n");
		
		primaryKeys.append("PRIMARY KEY(");
	
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {
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
	public void create(Object object) throws IllegalArgumentException, SQLException {
		StringBuilder query;
		Class<?> objClass = object.getClass();
		Field[] fields = objClass.getDeclaredFields();
		ArrayList<Object> values = new ArrayList<Object>(fields.length);
		String[] tables = Mapper.getDeclaredTables(objClass);
		int i;
		
		//check consistency
		if (fields.length == 0) {
			throw new IllegalArgumentException("object has no attributes");
		}
		if (tables.length != 1) {
			throw new IllegalArgumentException("Object of Type "+objClass.getName()+" must only use one table to be inserted");
		}
		
		try {
			query = new StringBuilder();
			
			query.append("INSERT INTO ");
			query.append(tables[0]);
			query.append(" VALUES (");
			
			//skip static fields
			i = 0;
			while (Modifier.isStatic(fields[i].getModifiers()) && i < fields.length) { i++; };
			if (i == fields.length) {
				throw new IllegalArgumentException("Class "+objClass.getName()+" only has static fields");
			}
			
			query.append('?');
			values.add(fields[i].get(object));
			for ( i++; i < fields.length; i++) {
				if (!Modifier.isStatic(fields[i].getModifiers())) {
					query.append(",?");
					values.add(fields[i].get(object));
				}
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
	 * @param maxRows: Numero maximo de linhas a serem trazidas, -1 indica que nao ha limite
	 * @param factory: Fabrica de objetos que instancia objetos a serem mapeados
	 * @param filter: Filtro obrigatorio
	 * @param others: Filtros opcionais
	 */
	public <Type> List<Type> read(int maxRows, Class<Type> factory, Filter... filters) throws IllegalArgumentException, SQLException {
		
		Constructor<?> constructor;
		Field[] fields = factory.getDeclaredFields();
		String[] tables = Mapper.getDeclaredTables(factory);
		StringBuilder query = new StringBuilder();
		ArrayList<Object> params = null;
		ResultSet queryResult;
		int i;
		
		List<Type> rows = (maxRows > 0)? new ArrayList<Type>(maxRows) : new LinkedList<Type>();
		
		if (fields.length == 0) {
			throw new IllegalArgumentException("object generated by factory has no fields");
		}
		
		//skip static fields
		i = 0;
		while (Modifier.isStatic(fields[i].getModifiers()) && i < fields.length) { i++; };
		if (i == fields.length) {
			throw new IllegalArgumentException("Class "+factory.getName()+" only has static fields");
		}
		
		//preencher select da query
		query.append("SELECT\t");
		query.append(fields[i].getName());
		for (i++; i < fields.length; i++) {
			if (!Modifier.isStatic(fields[i].getModifiers())) {
				query.append(",\n\t");
				query.append(fields[i].getName());
			}
		}
		
		//preencher from da query
		query.append("\nFROM\t");
		query.append(tables[0]);
		for (i = 1; i < tables.length; i++) {
			query.append(" NATURAL JOIN\n\t");
			query.append(tables[i]);
		}
		
		//preencher where da query e parametros
		if (filters.length != 0) {
			params = new ArrayList<Object>(filters.length);
			
			query.append("\nWHERE\t");
			query.append(filters[0].getParamFilter());
			params.add(filters[0].getParamValue());
			for (i = 1; i < filters.length; i++) {
				query.append(" AND\n\t");
				query.append(filters[i].getParamFilter());
				params.add(filters[i].getParamValue());
			}
		}
		
		queryResult = this.generateQuery(query.toString(), params).executeQuery();
		
		try {
			constructor = factory.getConstructor();
			queryResult.next();
			while (!queryResult.isAfterLast() && maxRows != 0) {
				//instanciar objeto da linha atual
				@SuppressWarnings("unchecked")
				Type obj = (Type)constructor.newInstance();
				for (Field field : fields) {
					if (!Modifier.isStatic(field.getModifiers())) {
						Object data = Mapper.retrieveData(queryResult, field.getName(), field.getType());
						field.set(obj, data);
					}
				}
				
				rows.add(obj);
				
				queryResult.next();
				maxRows--;
			}
		} catch (IllegalAccessException|InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException|NoSuchMethodException e) {
			throw new IllegalArgumentException("Class "+factory.getName()+" needs default constructor for query to be made");
		}
		
		return rows;
	}
	
	/**
	 * Atualiza objeto na base de dados
	 * Objeto sera procurado na base de acordo com suas chaves primarias e entao seus valores serao modificados
	 * @param object: objeto a ser atualizado com os valores a serem atualizados
	 */
	public void update (Object object) throws SQLException {
		StringBuilder query = new StringBuilder();
		Class<?> objClass = object.getClass();
		Field[] fields = objClass.getDeclaredFields();
		String[] tables = Mapper.getDeclaredTables(objClass);
		LinkedList<Field> pks = new LinkedList<Field>();
		ArrayList<Object> params = new ArrayList<Object>(fields.length);
		Iterator<Field> it;
		int i;
		Field field;
		
		if (tables.length != 1) {
			throw new IllegalArgumentException("Object of Type "+objClass.getName()+" must only use one table to be updated");	
		}
		
		query.append("UPDATE ");
		query.append(tables[0]);
		query.append(" SET\n");
		
		try {
			
			//skip static fields
			i = 0;
			while (Modifier.isStatic(fields[i].getModifiers()) && i < fields.length) { i++; };
			if (i == fields.length) {
				throw new IllegalArgumentException("Class "+objClass.getName()+" only has static fields");
			}
		
			query.append("\n\t");
			query.append(fields[i].getName());
			query.append("=?");
			params.add(fields[i].get(object));
			for (i++; i < fields.length; i++) {
				if (!Modifier.isStatic(fields[i].getModifiers())) {
					if (fields[i].getDeclaredAnnotation(PrimaryKey.class) != null) {
						pks.add(fields[i]);
					} else {
						query.append(",\n\t");
						query.append(fields[i].getName());
						query.append("=?");
						params.add(fields[i].get(object));
					}
				}
			}
			
			it = pks.iterator();
			field = it.next();
			query.append("\nWHERE\n\t");
			query.append(field.getName());
			query.append("=?");
			params.add(field.get(object));
			while (it.hasNext()) {
				field = it.next();
				query.append(" AND\n\t");
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
	public void delete (Class<?> table) throws SQLException {
		String[] tables = Mapper.getDeclaredTables(table);
		if (tables.length != 1) {
			throw new IllegalArgumentException("Class "+table.getName()+" must only use one table to be dropped");
		}
		this.connection.prepareStatement("DROP TABLE "+tables[0]).executeUpdate();
	}
	/**
	 * Exclui objeto da base de dados
	 * @param object: Objeto a ser excluido
	 */
	public void delete (Object object) throws SQLException {
		StringBuilder query = new StringBuilder();
		Class<?> objClass = object.getClass();
		Field[] fields = objClass.getDeclaredFields();
		String[] tables = Mapper.getDeclaredTables(objClass);
		LinkedList<Object> params = new LinkedList<Object>();
		
		if (tables.length != 1) {
			throw new IllegalArgumentException("Object of Type "+objClass.getName()+" must only use one table to be deleted");
		}
		
		query.append("DELETE FROM ");
		query.append(tables[0]);
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