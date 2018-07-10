package database;

import java.sql.SQLException;
import java.util.List;

import database.Mapper.Filter;

public interface DbMapperManager {
	public void create (Class<?> table) throws SQLException, IllegalArgumentException;
	public void create(Object object) throws IllegalArgumentException, SQLException;
	public <Type> List<Type> read(int maxRows, Class<Type> factory, Filter... filters) throws IllegalArgumentException, SQLException;
	public void update (Object object) throws SQLException, IllegalArgumentException;
	public void delete (Class<?> table) throws SQLException, IllegalArgumentException;
	public void delete (Object object) throws SQLException, IllegalArgumentException;
}
