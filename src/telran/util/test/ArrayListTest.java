package telran.util.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import telran.util.*;

import org.junit.jupiter.api.Test;

class ArrayListTest {
	private static final int BIG_LENGTH = 10000;
	List<Integer> list;
	Integer[] numbers = {10, -20, 7, 50, 100, 30};
	@BeforeEach
	void setUp() {
		list = new telran.util.ArrayList<>(1);
		for( int i = 0; i < numbers.length; i++) {
			list.add(numbers[i]);
		}
	}

	@Test
	void testGetIndex() {
		assertEquals(10, list.get(0));
	}
	@Test
	void testAdd() {
		assertTrue(list.add(numbers[1]));
		assertEquals(numbers.length+1, list.size());
	}
	@Test
	void testAddIndex() {
		Integer [] expected0_500 = {500, 10, -20, 7, 50, 100, 30};
		Integer [] expected0_500_3_700 = {500, 10, -20, 700, 7, 50, 100, 30};
		Integer [] expected0_500_3_700_8_300 = {500, 10, -20, 700, 7, 50, 100, 30, 300};
		list.add(0, 500);
		runTest(expected0_500);
		list.add(3, 700);
		runTest(expected0_500_3_700);
		list.add(8, 300);
		runTest(expected0_500_3_700_8_300);
		
	}
	@Test
	void testRemoveIndex() {
		Integer [] expectedNo10 = { -20, 7, 50, 100, 30};
		Integer [] expectedNo10_50 = { -20, 7,  100, 30};
		Integer [] expectedNo10_50_30 = { -20, 7,  100};
		assertEquals(10, list.remove(0));
		runTest(expectedNo10);
		assertEquals(50, list.remove(2));
		runTest(expectedNo10_50);
		assertEquals(30, list.remove(3));
		runTest(expectedNo10_50_30);
		
	}
	@Test
	void testIndexOf() {
		list.add(2, 10);
		assertEquals(0, list.indexOf(10));
	}
	@Test
	void testLastIndexOf() {
		list.add(5, 10);
		assertEquals(5, list.lastIndexOf(10));
	}
	
	
	@Test
	void testToArrayForBigArray() {
		Integer[] bigArray = new Integer[BIG_LENGTH];
		for(int i = 0; i < BIG_LENGTH; i++) {
			bigArray[i] = 10;
		};
		Integer[] actualArray = list.toArray(bigArray);
		int size = list.size();
		for(int i = 0; i < size; i++) {
			assertEquals(numbers[i], actualArray[i]);
		}
	}
	
	@Test
	void testToArrayForEmptyArray() {
		Integer[] emptyArray = list.toArray(new Integer[0]);
		assertArrayEquals(numbers, emptyArray);
	}
	
	@Test
	void testArraySorting() {
		Integer[] newArray = {-20, 7, 10, 30, 50, 100};
		list.sort();
		assertArrayEquals(newArray, list.toArray(new Integer[0]));
	}
	
	@Test
	void testSortPersons() {
		List<Person> persons = new ArrayList<>();
		Person p1 = new Person(123, 32, "Vasya");
		Person p2 = new Person(124, 29, "Petya");
		Person p3 = new Person(473, 45, "Pavel");
		persons.add(p1);
		persons.add(p2);
		persons.add(p3);
		persons.sort();
		Person[] expected = {p1, p2, p3};
		assertArrayEquals(expected, persons.toArray(new Person[0]));
	}
	
	@Test
	void testSortPersonsByAge() {
		List<Person> persons = new ArrayList<>();
		Person p1 = new Person(123, 32, "Vasya");
		Person p2 = new Person(124, 29, "Petya");
		Person p3 = new Person(473, 45, "Pavel");
		persons.add(p1);
		persons.add(p2);
		persons.add(p3);
		Person[] expected = {p2, p1, p3};
		persons.sort((a1, a2) -> Integer.compare(a1.getAge(), a2.getAge()));
		assertArrayEquals(expected, persons.toArray(new Person[0]));
	}
	
	@Test
	void testEvenOddSorting() {
		Integer[] expected = { -20,  10, 30, 50, 100, 7, -17};
		list.add(-17);
		//list.sort((a, b)-> evenOddCompare(a, b));
		list.sort(ArrayListTest::evenOddCompare);
		assertArrayEquals(expected, list.toArray(new Integer[0]));
	}
	
	@Test
	void testIndexOfPredicate() {
		assertEquals(1, list.indexOf(a -> a < 0));
	}
	
	@Test
	void testLastIndexOfPredicate() {
		list.add(4, -1);
		assertEquals(4, list.lastIndexOf(a -> a < 0));
	}
	@Test
	void testRemoveIf() {
		Integer [] expected = {10, 7, 50, 100, 30};
		assertFalse(list.removeIf(a -> a%2 != 0 && a<0));
		list.removeIf(a -> a < 0);
		runTest(expected);
	}
	
	static private int evenOddCompare(Integer a, Integer b) {
		int res = Math.abs(a%2) - Math.abs(b%2);
		if(res == 0) {
			res = a % 2 == 0 ? a-b : b - a;
		}
		return res;
	}

	private void runTest(Integer[] expected) {
		int size = list.size();
		Integer[] actual = new Integer[expected.length];
		for(int i = 0; i < size; i++) {
			actual[i] = list.get(i);
		}
		assertArrayEquals(expected, actual);
		
	}
}
