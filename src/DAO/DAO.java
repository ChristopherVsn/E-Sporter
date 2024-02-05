package DAO;

import java.util.List;
import java.util.Optional;

public interface DAO<T, T1> {

	List<T> getAll() throws Exception;

	Optional<T> getById(T1... id) throws Exception;

	boolean add(T value) throws Exception;

	boolean update(T value) throws Exception;

	boolean delete(T value) throws Exception;

}
