package database;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import database.Mapper.Filter;

public class MapperMocker extends Mapper {
	
	int numberOfMocks;
	
	public MapperMocker (int numberOfMocks) {
		this.numberOfMocks = numberOfMocks;
	}
	
	public static interface Script {
		public Object mock();
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
	public <Type> List<Type> read(int maxRows, Class<Type> factory, Filter... filters) throws IllegalArgumentException {
		Constructor<Type> constr;
		if (!Script.class.isAssignableFrom(factory)) {
			throw new IllegalArgumentException("Cannot mock object of type "+factory.getName()+" since it does not implement MapperMocker.Script");
		}
		ArrayList<Type> list = new ArrayList<Type>(numberOfMocks);
		try {
			constr = factory.getConstructor();
			Script mockFactory = (Script)constr.newInstance();
			for (int i = 0; i < numberOfMocks; i++) {
				@SuppressWarnings("unchecked")	//checado em catch de ClassCastException
				Type obj = (Type)mockFactory.mock();
				list.add(obj);
			}
		} catch (NoSuchMethodException|InvocationTargetException|InstantiationException|IllegalAccessException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Class "+factory.getName()+" must implement an accessible explicit default constructor in order to be mapped");
		} catch (ClassCastException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Mismatch in type Script implementation in "+factory.getName());
		}
		return list;
	}
	
	@Override
	public void update(Object object, Object newObj) {
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
