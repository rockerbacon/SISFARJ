package database;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import java.sql.Types;

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
		int size() default 255;
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface UseTables {
		String[] value();
	}
	
	public static interface Script<T> {
		public T mapTo(T object);
		public Script<T> mapFrom(T object);
	}
	
	protected Connection connection;
	
	
	protected Mapper() {}
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
	protected static String mapToSqlType (Field field) throws IllegalArgumentException {
		String className = field.getType().getSimpleName();
		String sqlName;
		switch (className.toUpperCase()) {
		
			case "STRING":
				StringSize annotation = field.getDeclaredAnnotation(StringSize.class);
				if (annotation == null) {
					sqlName = "VARCHAR(255)";
				} else {
					if (annotation.fixed()) {
						sqlName = "CHAR("+annotation.size()+")";
					} else {
						sqlName = "VARCHAR("+annotation.size()+")";
					}
				}
			break;
		
			case "CHAR":
				sqlName = "CHARACTER";
			break;
			
			case "BOOLEAN":
			case "BYTE":
			case "SHORT":
				sqlName = "SMALLINT";
			break;
			
			case "INT":
			case "INTEGER":
				sqlName = "INTEGER";
			break;
			
			case "LONG":
				sqlName = "BIGINT";
			break;
			
			case "FLOAT":
				sqlName = "FLOAT";
			break;
			
			case "DOUBLE":
				sqlName = "DOUBLE PRECISION";
			break;
			
			case "DATE":
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
	protected PreparedStatement generateQuery (String query, List<Object> parameters) throws SQLException {
		int i = 0;
		PreparedStatement statement = this.connection.prepareStatement(query);
		
		if (parameters != null) {
			Iterator<Object> it = parameters.iterator();
			while (it.hasNext()) {
				Object obj= it.next();
				if (obj != null && obj.getClass().equals(Date.class))				
					statement.setObject(++i, obj, Types.TIMESTAMP);
				else
					statement.setObject(++i, obj);
			}
		}
		
		return statement;
	}
	
	protected static String[] getDeclaredTables (Class<?> c) throws IllegalArgumentException {
		UseTables declTables = c.getDeclaredAnnotation(UseTables.class);
		if (declTables == null || declTables.value().length == 0) {
			throw new IllegalArgumentException("Class "+c.getCanonicalName()+" has no declared tables");
		}
		return declTables.value();
	}
	
	protected static Object retrieveData (ResultSet resultSet, String columnLabel, Class<?> returnType) throws SQLException, IllegalArgumentException {
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
		HashMap<String, LinkedList<Field>> foreignKeys = new HashMap<String, LinkedList<Field>>();
		Iterator<Map.Entry<String, LinkedList<Field>>> it;
		
		//check consistency
		if (fields.length == 0) {
			throw new IllegalArgumentException("Class "+table.getCanonicalName()+" has no fields");
		}
		if (tables.length != 1) {
			System.out.println(tables.length);
			throw new IllegalArgumentException("Class "+table.getCanonicalName()+" must only use one table to be created");
		}
		
		query.append("CREATE TABLE ");
		query.append(tables[0]);
		query.append(" (\n");
		
		primaryKeys.append("\tPRIMARY KEY(");
	
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
					if (!foreignKeys.containsKey(fk.references())) {
						foreignKeys.put(fk.references(), new LinkedList<Field>());
					}
					foreignKeys.get(fk.references()).add(field);
				}
			}
		}
		//remove trailling characters
		primaryKeys.setLength(primaryKeys.length()-2);
		
		primaryKeys.append(")");
		query.append(primaryKeys);
		
		it = foreignKeys.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, LinkedList<Field>> entry = it.next();
			String foreignTable = entry.getKey();
			Iterator<Field> fieldIt = entry.getValue().iterator();
			StringBuilder references = new StringBuilder();
			Field currFk;
			
			currFk = fieldIt.next();
			fk = currFk.getAnnotation(Mapper.ForeignKey.class);
			query.append(",\n\tFOREIGN KEY(");
			query.append(currFk.getName());
			if (fk.on().isEmpty())
				references.append(currFk.getName());
			else
				references.append(fk.on());
			
			while(fieldIt.hasNext()) {
				currFk = fieldIt.next();
				fk = currFk.getAnnotation(Mapper.ForeignKey.class);
				query.append(", ");
				query.append(currFk.getName());
				references.append(", ");
				if (fk.on().isEmpty())
					references.append(currFk.getName());
				else
					references.append(fk.on());	
			}
			
			query.append(") REFERENCES ");
			query.append(foreignTable);
			query.append("(");
			query.append(references);
			query.append(")");
		}
		
		query.append("\n)");
		
		//System.out.println(query.toString());	//debug
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
			throw new IllegalArgumentException("Object of Type "+objClass.getCanonicalName()+" must only use one table to be inserted");
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
				throw new IllegalArgumentException("Class "+objClass.getCanonicalName()+" only has static fields");
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
		ResultSet queryResult = null;
		int i;
		
		List<Type> rows = (maxRows > 0)? new ArrayList<Type>(maxRows) : new LinkedList<Type>();
		
		if (fields.length == 0) {
			throw new IllegalArgumentException("object generated by factory has no fields");
		}
		
		//skip static fields
		i = 0;
		while (Modifier.isStatic(fields[i].getModifiers()) && i < fields.length) { i++; };
		if (i == fields.length) {
			throw new IllegalArgumentException("Class "+factory.getCanonicalName()+" only has static fields");
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
			while (queryResult.next() && maxRows != 0) {
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
				
				maxRows--;
			}
		} catch (IllegalAccessException|InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException|NoSuchMethodException e) {
			throw new IllegalArgumentException("Class "+factory.getCanonicalName()+" needs default constructor for query to be made");
		} finally {
			if (queryResult != null) queryResult.close();
		}
		
		return rows;
	}
	
	/**
	 * Atualiza objeto na base de dados
	 * Objeto sera procurado na base de acordo com suas chaves primarias e entao seus valores serao modificados
	 * @param object: objeto a ser atualizado com os valores a serem atualizados
	 */
	
	public void update (Object object) throws SQLException, IllegalArgumentException {
		StringBuilder query = new StringBuilder();
		Class<?> objClass = object.getClass();
		Field[] fields = objClass.getDeclaredFields();
		String[] tables = Mapper.getDeclaredTables(objClass);
		LinkedList<Field> pks = new LinkedList<Field>();
		ArrayList<Object> params = new ArrayList<Object>(fields.length);
		Iterator<Field> it;
		int i;
		Field field;
		Object param;
		
		if (tables.length != 1) {
			throw new IllegalArgumentException("Object of Type "+objClass.getCanonicalName()+" must only use one table to be updated");	
		}
		
		query.append("UPDATE ");
		query.append(tables[0]);
		query.append(" SET\n");
		
		try {
			
			//skip static fields
			i = 0;
			while (Modifier.isStatic(fields[i].getModifiers()) && i < fields.length) { i++; };
			if (i == fields.length) {
				throw new IllegalArgumentException("Class "+objClass.getCanonicalName()+" only has static fields");
			}
			
			while (fields[i].getDeclaredAnnotation(PrimaryKey.class) != null) {
				pks.add(fields[i]);
				i++;
			}
			
			if (i != fields.length) {
		
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
							param = fields[i].get(object);
							params.add(fields[i].get(object));
						}
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
			throw new IllegalArgumentException("Not all fields in class "+objClass.getCanonicalName()+" are accessible by mapper");
		}
	}
	
	/**
	 * Executa drop na tabela da classe especificada
	 * @param table: Classe que utiliza a tabela a ser dropada
	 * @throws SQLException
	 */
	
	public void delete (Class<?> table) throws SQLException, IllegalArgumentException {
		String[] tables = Mapper.getDeclaredTables(table);
		if (tables.length != 1) {
			throw new IllegalArgumentException("Class "+table.getCanonicalName()+" must only use one table to be dropped");
		}
		this.connection.prepareStatement("DROP TABLE "+tables[0]).executeUpdate();
	}
	/**
	 * Exclui objeto da base de dados
	 * @param object: Objeto a ser excluido
	 */
	
	public void delete (Object object) throws SQLException, IllegalArgumentException {
		StringBuilder query = new StringBuilder();
		Class<?> objClass = object.getClass();
		Field[] fields = objClass.getDeclaredFields();
		String[] tables = Mapper.getDeclaredTables(objClass);
		LinkedList<Object> params = new LinkedList<Object>();
		
		if (tables.length != 1) {
			throw new IllegalArgumentException("Object of Type "+objClass.getCanonicalName()+" must only use one table to be deleted");
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
			throw new IllegalArgumentException("Not all fields in class "+objClass.getCanonicalName()+" are accessible by mapper");
		}
		
	}
	
}