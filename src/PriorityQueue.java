///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  StudentCenter.java
// File:             PriorityQueue.java
// Semester:         CS 367 Spring 2016
//
// Author:           Morgan O'Leary oleary4@wisc.edu
// CS Login:         o-leary
// Lecturer's Name:  Jim Skrentny

//////////////////////////// 80 columns wide //////////////////////////////////
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * PriorityQueue implemented as a Binary Heap backed by an array. Implements
 * QueueADT. Each entry in the PriorityQueue is of type PriorityQueueNode<E>.
 * First element is array[1]
 * 
 * @author CS367
 *
 * @param <E>
 */
public class PriorityQueue<E> implements QueueADT<PriorityQueueItem<E>>
	{
	private final int DEFAULT_CAPACITY = 100;

	// Number of elements in heap
	private int currentSize;

	/**
	 * The heap array. First element is array[1]
	 */
	private PriorityQueueItem<E>[] array;

	/**
	 * Construct an empty PriorityQueue.
	 */
	public PriorityQueue()
		{
		
		// the below code initializes the array.. similar code used for
		// expanding.
		array = (PriorityQueueItem<E>[]) Array.newInstance(PriorityQueueItem.class, DEFAULT_CAPACITY + 1);
		}

	/**
	 * Copy constructor
	 * 
	 * @param pq
	 *            PriorityQueue object to be copied
	 */
	public PriorityQueue(PriorityQueue<E> pq)
		{
		this.currentSize = pq.currentSize;
		this.array = Arrays.copyOf(pq.array, currentSize + 1);
		}

	/**
	 * Adds an item to this PriorityQueue. If array is full, double the array
	 * size.
	 * 
	 * @param item
	 *            object of type PriorityQueueItem<E>.
	 */

	@Override
	public void enqueue(PriorityQueueItem<E> item)
		{
		boolean done = false;
		if(item == null){
			throw new IllegalArgumentException();
		}	
//		plus one here maybe?
		if(currentSize == array.length){
				doubleArray();
			}
		
		for(int i = 1; i <= currentSize; i++){
			if(array[i].compareTo(item) == 0){
				System.out.println("These are the same priority");
				Queue<E> list = item.getList();
				
				while(!list.isEmpty()){
					array[i].add(list.dequeue());
					done = true;
				
				}
			}
		}
		
		// check it later
		if(!done){
				currentSize++;
				array[currentSize] = item;
				percolateUp();	
//				seeArray();
		}
		
		}
	private void seeArray(){
		System.out.println("this is what's inside the array");
		for (int i = 1; i <= currentSize; i++){
			System.out.println(array[i].getPriority());
		}
	}
	
	private void percolateUp(){
		int child = currentSize;
		boolean done = false;
		while(!done){
			int parent = child/2;
			if (parent == 0){
				done = true;
			}else if(array[child].compareTo(array[parent]) <= 0){
				done = true;
			}else {
				PriorityQueueItem<E> temp = array[child];
				array[child] = array[parent];
				array[parent] = temp;
				
			}
		}
	}

	/**
	 * Returns the number of items in this PriorityQueue.
	 * 
	 * @return the number of items in this PriorityQueue.
	 */
	public int size()
		{
		return currentSize;
		
		}

	/**
	 * Returns a PriorityQueueIterator. The iterator should return the
	 * PriorityQueueItems in order of decreasing priority.
	 * 
	 * @return iterator over the elements in this PriorityQueue
	 */
	public Iterator<PriorityQueueItem<E>> iterator()
		{
		// TODO write appropriate code - see PriortyQueueIterator constructor
		return new PriorityQueueIterator(this);
		}

	/**
	 * Returns the smallest item in the priority queue.
	 * 
	 * @return the largest item.
	 * @throws NoSuchElementException
	 *             if empty.
	 */
	@Override
	public PriorityQueueItem<E> peek()
		{
		if(isEmpty()){
			throw new EmptyQueueException();
		}
		return array[1];
		}

	/**
	 * Removes and returns the smallest item in the priority queue. Switch last
	 * element with removed element, and percolate down.
	 * 
	 * @return the smallest item.
	 * @throws NoSuchElementException
	 *             if empty.
	 */
	@Override
	public PriorityQueueItem<E> dequeue()
		{
		// TODO
		// Remove first element
		PriorityQueueItem<E> temp = array[1];
//		this might have to be currentSize -1 or +1
		// Replace with last element, percolate down
		array[1] = array[currentSize];
		percolateDown(1);
//		am I supposed to be using buildHeap? 
//		buildHeap();
		currentSize--;
		return temp;
		}

	/**
	 * Heapify Establish heap order property from an arbitrary arrangement of
	 * items. ie, initial array that does not satisfy heap property. Runs in
	 * linear time.
	 */
	private void buildHeap()
		{
		for (int i = currentSize / 2; i > 0; i--)
			percolateDown(i);
		}

	/**
	 * Make this PriorityQueue empty.
	 */
	public void clear()
		{
			while(!isEmpty()){
				dequeue();
			}
		}

	/**
	 * Internal method to percolate down in the heap. <a
	 * href="https://en.wikipedia.org/wiki/Binary_heap#Extract">Wiki</a>}
	 * 
	 * @param hole
	 *            the index at which the percolate begins.
	 */
	private void percolateDown(int hole)
		{
			boolean donedown = false;
			int parent = hole;
			while(!donedown && (hole*2) +1 <= currentSize){

			if(array[hole*2].compareTo(array[hole*2 +1]) == -1){
				if(array[parent].compareTo(array[(hole*2) +1]) == -1){
					PriorityQueueItem<E> swap = array[parent];
					array[parent] = array[(hole*2)+1];
					array[(hole*2)+1] = swap;
					hole = (hole*2) +1;
					parent = hole;
				}else if(array[(hole*2) +1].compareTo(array[parent]) <= 0){
					donedown = true;
				}
			}else if(array[hole*2].compareTo(array[(hole*2) +1]) > 0 ){
				if(array[parent].compareTo(array[(hole*2)]) == -1){
					PriorityQueueItem<E> swap = array[parent];
					array[parent] = array[(hole*2)];
					array[(hole*2)] = swap;
					hole = hole*2;
					parent = hole;
				}else if(array[hole*2].compareTo(array[parent]) <= 0){
					donedown = true;
				}
			}
			}
		}
			

			
	

	/**
	 * Internal method to expand array.
	 */
	private void doubleArray()
		{
		PriorityQueueItem<E>[] newArray;

		newArray = (PriorityQueueItem<E>[]) Array.newInstance(PriorityQueueItem.class, array.length * 2);

		for (int i = 0;i < array.length;i++)
			newArray[i] = array[i];
		array = newArray;
		}

	@Override
	public boolean isEmpty()
		{
		if(currentSize == 0)
			return true;
		return false;
		}
	}