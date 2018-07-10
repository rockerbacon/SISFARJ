package database;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import database.Mapper.Filter;

public class DbMapperMocker implements DbMapperManager {
	
	private byte numberCount, dateCount, stringCount;
	private HashMap<String, ArrayList<Object>> tableMap;
	
	private Object valueFor (Field field) {
		Object value = null;
		
		switch(field.getType().getSimpleName().toUpperCase()) {
			case "INTEGER":
			case "INT":
			case "FLOAT":
			case "BYTE":
			case "SHORT":
			case "LONG":
			case "DOUBLE":
				value = this.numberCount;
				this.numberCount++;
			break;
			
			case "DATE":
				try {
					SimpleDateFormat df = new SimpleDateFormat("DD/MM/YYYY");
					value = df.parse("01/01/2"+String.format("%03d", this.dateCount));
					this.dateCount++;
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
			break;
			
			case "STRING":
				value = "test"+this.stringCount;
				this.stringCount++;
			break;
			
			case "CHAR":
			case "CHARACTER":
				value = 'm';
			break;
			
		}
		
		return value;
	}
	
	/**
	 * Cria db artificial contendo todas as tabelas especificadas, cada uma preenchida com instancias com campos com valores predeterminados:
	 * 		numeros(int, float, etc): valores unicos em [0, n)
	 * 		datas: datas no formato 01/01/2xxx, onde xxx sao valores unicos em [0,n)
	 * 		Strings: strings no formato "testx" onde x sao valores unicos em [0,n)
	 * 		Chars: valor sempre 'm'
	 * @param n
	 * @param tables
	 */
	public DbMapperMocker(byte n, Class<?>... tables) throws IllegalArgumentException {
		
		this.tableMap = new HashMap<String, ArrayList<Object>>();
		
		String className = "";
		
		try {
			
			for (Class<?> table : tables) {
				className = table.getCanonicalName();
				Mapper.UseTables dclTable = table.getDeclaredAnnotation(Mapper.UseTables.class);
				Field[] fields = table.getDeclaredFields();
				Constructor<?> constructor = table.getConstructor();
				ArrayList<Object> items = new ArrayList<Object>(n);
				
				this.dateCount = 0;
				this.numberCount = 0;
				this.stringCount = 0;
				
				if (dclTable == null || dclTable.value().length != 1) {
					throw new IllegalArgumentException("Class "+table.getCanonicalName()+" must declare one and only one table in order to be mocked");
				}
				
				for (byte i = (byte)0; i < n; i++) {
					Object obj = constructor.newInstance();
					for (Field field : fields) {
						if (!Modifier.isStatic(field.getModifiers())) {
							field.set(obj, this.valueFor(field));
						}
					}
					items.add(obj);
				}
				
				this.tableMap.put(dclTable.value()[0], items);
			}
		} catch (InvocationTargetException|NoSuchMethodException|IllegalAccessException e) {
			throw new IllegalArgumentException("Class "+className+" must have an accessible default constructor in order to be mocked");
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void create(Class<?> table) throws SQLException, IllegalArgumentException {
		//do nothing
	}

	@Override
	public void create(Object object) throws IllegalArgumentException, SQLException {
		Class<?> objClass = object.getClass();
		Mapper.UseTables dclTable = objClass.getDeclaredAnnotation(Mapper.UseTables.class);
		
		if (dclTable == null || dclTable.value().length != 1) {
			throw new IllegalArgumentException("Class "+objClass.getCanonicalName()+" must declare one and only one table in order to be created");
		}
		
		this.tableMap.get(dclTable.value()[0]).add(object);
	}

	@Override
	public <Type> List<Type> read(int maxRows, Class<Type> factory, Filter... filters) throws IllegalArgumentException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object object) throws SQLException, IllegalArgumentException {
		Class<?> objClass = object.getClass();
		Mapper.UseTables dclTable = objClass.getDeclaredAnnotation(Mapper.UseTables.class);
		Field[] fields = objClass.getDeclaredFields();
		LinkedList<Field> pks = new LinkedList<Field>();
	
		if (dclTable == null || dclTable.value().length != 1) {
			throw new IllegalArgumentException("Class "+objClass.getCanonicalName()+" must declare one and only one table in order to be updated");
		}
		
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				if (field.getDeclaredAnnotation(Mapper.PrimaryKey.class) != null) {
					pks.add(field);
				}
			}
		}
		
		try {
			ArrayList<Object> list = this.tableMap.get(dclTable.value()[0]);
			for (int i = 0; i < list.size(); i++) {
				boolean found = true;
				for (Field pk : pks) {
					if (!pk.get(object).equals(pk.get(list.get(i)))) {
						found = false;
						break;
					}
				}
				if (found) {
					list.remove(i);
					list.add(object);
					break;
				}
			}
			
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Not all fields in class "+objClass.getCanonicalName()+" are accessible by mapper");
		}
	}

	@Override
	public void delete(Class<?> table) throws SQLException, IllegalArgumentException {
		Class<?> objClass = table.getClass();
		Mapper.UseTables dclTable = objClass.getDeclaredAnnotation(Mapper.UseTables.class);
	
		if (dclTable == null || dclTable.value().length != 1) {
			throw new IllegalArgumentException("Class "+objClass.getCanonicalName()+" must declare one and only one table in order to be dropped");
		}
		
		this.tableMap.remove(dclTable.value()[0]);
		
	}

	@Override
	public void delete(Object object) throws SQLException, IllegalArgumentException {
		Class<?> objClass = object.getClass();
		Mapper.UseTables dclTable = objClass.getDeclaredAnnotation(Mapper.UseTables.class);
		Field[] fields = objClass.getDeclaredFields();
		LinkedList<Field> pks = new LinkedList<Field>();
	
		if (dclTable == null || dclTable.value().length != 1) {
			throw new IllegalArgumentException("Class "+objClass.getCanonicalName()+" must declare one and only one table in order to be updated");
		}
		
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				if (field.getDeclaredAnnotation(Mapper.PrimaryKey.class) != null) {
					pks.add(field);
				}
			}
		}
		
		try {
			ArrayList<Object> list = this.tableMap.get(dclTable.value()[0]);
			for (int i = 0; i < list.size(); i++) {
				boolean found = true;
				for (Field pk : pks) {
					if (!pk.get(object).equals(pk.get(list.get(i)))) {
						found = false;
						break;
					}
				}
				if (found) {
					list.remove(i);
					break;
				}
			}
			
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Not all fields in class "+objClass.getCanonicalName()+" are accessible by mapper");
		}
		
	}

}
