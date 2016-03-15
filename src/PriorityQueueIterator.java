///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  StudentCenter.java
// File:             PriorityQueueIterator.java
// Semester:         CS 367 Spring 2016
//
// Author:           Morgan O'Leary oleary4@wisc.edu
// CS Login:         o-leary
// Lecturer's Name:  Jim Skrentny

//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PriorityQueueIterator<T> implements Iterator<PriorityQueueItem<T>>
	{

	private PriorityQueue<T> priorityQueue;
	/**
	 * Constructs a PriorityQueueIterator by utilizing a copy of the
	 * PriorityQueue. Hint : The local priorityQueue object need not be
	 * preserved, and hence you can use the dequeue() operation.
	 * 
	 * @param pq
	 */
	public PriorityQueueIterator(PriorityQueue<T> pq)
		{
		// This copies the contents of the passed parameter to the local object.
		// Hint : see copy constructor in PriorityQueue
		priorityQueue = new PriorityQueue<T>(pq);
		}

	/**
	 * Returns true if the iteration has more elements.
	 * 
	 * @return true/false
	 */
	@Override
	public boolean hasNext()
		{
			if(priorityQueue.isEmpty()){
				return false;
			}
			return true;
		}

	/**
	 * Returns the next element in the iteration. The iterator should return the
	 * PriorityQueueItems in order of decreasing priority.
	 * 
	 * @return the next element in the iteration
	 * @throws NoSuchElementException
	 *             if the iteration has no more elements
	 */
	@Override
	public PriorityQueueItem<T> next()
		{
		if(!hasNext()){
			throw new NoSuchElementException();
		}
		return priorityQueue.dequeue();
		}

	/**
	 * Unsupported in this iterator for this assignment
	 */
	@Override
	public void remove()
		{
		// Do not implement
		throw new UnsupportedOperationException();
		}

	}
