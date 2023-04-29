package telran.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class ArrayList<T> implements List<T> {
private static final int DEFAULT_CAPACITY = 16;
private T[] array;
private int size;

@SuppressWarnings("unchecked")
public ArrayList(int capacity) {
	array = (T[]) new Object[capacity];
}
public ArrayList() {
	this(DEFAULT_CAPACITY);
}
	@Override
	public boolean add(T obj) {
		if(size == array.length) {
			realocate();
		}
		array[size] = obj;
		size++;
		return true;
	}

	private void realocate() {
		array = Arrays.copyOf(array, array.length * 2);
		
	}
	@Override
	public int size() {
		
		return size;
	}

	@Override
	public void add(int index, T obj) {
		if(size == array.length) {
			realocate();	
		};
		System.arraycopy(array, index, array, index+1, size-index);
		array[index] = obj;
		size++;
		
	}

	@Override
	public T remove(int index) {
		T arr = array[index];
		System.arraycopy(array, index+1, array, index, size-index-1);
		size--;
		return arr;
	}

	@Override
	public T get(int index) {
		T arr = array[index];
		return arr;
	}
	
	@Override
	public int lastIndexOf(T pattern) {
		int res = -1;
		int index = 0;
		for(int i = 0; i < size; i++) {
			if(isEqual(array[index], pattern)) {
				res = index;
			}
			index++;
		}
		return res;
	}
	
	@Override
	public int indexOf(T pattern) {
		int res = -1;
		int index = 0;
		while(index < size && res == -1) {
			if(isEqual(array[index], pattern)) {
				res = index;
			}
			index++;
		}
		return res;
	}
	
	@Override
	public boolean remove(T pattern) {
		boolean res = false;
		int index = indexOf(pattern);
		if(index > -1) {
			res = true;
			remove(index);
		}
		return res;
	}
	
	private boolean isEqual(T obj, T pattern) {
		return pattern == null? obj == pattern : obj.equals(pattern);
	}
	@Override
	public T[] toArray(T[] arr) {
		if(arr.length < size) {
			arr = Arrays.copyOf(arr, size);
		}
		System.arraycopy(array, 0, arr, 0, size);
		if(arr.length > size) {
			arr[size] = null;
		}
		return arr;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void sort() {
		sort((Comparator<T>) Comparator.naturalOrder());
		
	}
	@Override
	public void sort(Comparator<T> comp) {
		boolean flag = true;
		int n = size;
		do {
			flag = false;
			n--;
			for(int i = 0; i<n; i++) {
				if(comp.compare(array[i], array[i+1])>0) {
					swap(i);
					flag = true;
				}
			}
		}while(flag);
		
	}
	private void swap(int i) {
		T temp = array[i];
		array[i] = array[i+1];
		array[i+1] = temp;
		
	}
	@Override
	public int indexOf(Predicate<T> predicate) {
		int res = -1;
		int index = 0;
		while(index < size && res == -1) {
			if(predicate.test(array[index])) {
				res = index;
			}
			index++;
		}
		return res;
	}
	@Override
	public int lastIndexOf(Predicate<T> predicate) {
		int res = -1;
		int index = size - 1;
		while(index >=0 && res == -1) {
			if(predicate.test(array[index])) {
				res = index;
			}
			index--;
		}
		return res;
	}
	@Override
	public boolean removeIf(Predicate<T> predicate) {
		int oldsize = size;
		for(int i = size - 1; i >= 0; i--) {
			if(predicate.test(array[i])) {
				remove(i);
			}
		}
		return oldsize > size;
	}
	
	
	
}