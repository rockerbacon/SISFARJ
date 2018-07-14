package database;

import java.util.ArrayList;
import java.util.List;

import database.Mapper.Filter;

public class MapperMocker extends Mapper {
	
	int numberOfMocks;
	
	public MapperMocker (int numberOfMocks) {
		this.numberOfMocks = numberOfMocks;
	}

	@Override
	public void create(Class<?> table) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Cannot create table with a mock mapper");
	}
	
	@Override
	public void create (Object object) {
		//do nothing
	}
	
	@Override
	public <Type> List<Type> read(int maxRows, Class<Type> factory, Filter... filters) {
		ArrayList<Type> list = new ArrayList<Type>(numberOfMocks);
		for (int i = 0; i < numberOfMocks; i++) {
			list.add(null);
		}
		return list;
	}
	
	@Override
	public void update(Object object) {
		//do nothing
	}
	
	@Override
	public void delete (Class<?> table) {
		throw new UnsupportedOperationException("Cannot create table with a mock mapper");	
	}
	
	@Override
	public void delete (Object object) {
		//do nothing
	}
	
}
