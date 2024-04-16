package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

//the Subject class when we use Observer
public class MyArrayList<T>{

	private static int DEFAULT_CAPACITY = 6;
	private Object[] theItems;
	private int theSize;
	private Set<Observer> obSet = new HashSet<>(); // for the observer des patt

	public MyArrayList() {
		clear();
		obSet = new HashSet<>();
	}
	
	public MyArrayList(Observer b) {
		attach(b);
	}

	public int size() {
		return theSize;
	}

	public void add(Question value) {
		if(theItems == null)
			theItems = new Object[1];
			
		if (theSize == theItems.length) {
			theItems = Arrays.copyOf(theItems, theSize + 1);// DEFAULT_CAPACITY + 1);
			//DEFAULT_CAPACITY++;
		}
		theItems[theSize++] = value;
	}

	public Object remove(int idx) {
		Object removedItem = theItems[idx];

		for (int i = idx; i < size() - 1; i++)
			theItems[i] = theItems[i + 1];
		theSize--;
		//DEFAULT_CAPACITY--;

		return removedItem;
	}

	public void clear() {
		this.theSize = 0;
	}

	//add listener
	public void attach(Observer o) {
		obSet.add(o);
	}

	public void click() {
		myNotify();
	}

	public void detach(Observer o) {
		obSet.remove(o);
	}

	//notify
	public void myNotify() {
		for (Observer o : obSet)
			o.update();
	}

	public Iterator<Question> iterator() {
		myNotify();
		return new ConcreteIterator();
	}

	

	// Iterator design pattern
	private class ConcreteIterator implements Iterator<Question> {

		private int cur = 0;
		private boolean okToRemove = false;
		

		@Override
		public boolean hasNext() {
			return cur < theItems.length;
		}

		@Override
		public Question next() {
			if (!hasNext())
				throw new NoSuchElementException();
			okToRemove = true;
			return (Question) theItems[cur++];
		}

		@Override
		public void remove() {
			if (!okToRemove)
				throw new IllegalStateException();
			Object[] newItems = Arrays.copyOf(theItems, cur - 1);
			theItems = newItems;
			okToRemove = false;
		}

	}

	

}
