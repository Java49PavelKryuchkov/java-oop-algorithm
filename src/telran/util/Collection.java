package telran.util;

public interface Collection<T> {
	boolean add(T obj);
	int size();
	public T[] toArray(T[] array);
	boolean remove(T pattern);
}