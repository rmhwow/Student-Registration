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
		//initialize variables		
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
		//create boolean 	
		boolean done = false;
		//if the parameter is null, throw an exception		
		if(item == null){
			throw new IllegalArgumentException();
		}	
//		plus one here maybe?
		//if the length and the current size are the same, double the array		
		if(currentSize == array.length){
				doubleArray();
			}
		//iterate through the array		
		for(int i = 1; i <= currentSize; i++){
			//if the array element and the item have the same priority			
			if(array[i].compareTo(item) == 0){
				//assign the item's list to a variable				
				Queue<E> list = item.getList();
				//while that list isn't empty, add the items to the array
				//objects list and assign done to true				
				while(!list.isEmpty()){
					array[i].add(list.dequeue());
					done = true;
				}
			}
		}
		
		// check it later
		//if done is false, then increase the current size, assign the item to 
		//the last index in the array and call percolateUp		
		if(!done){
				currentSize++;
				array[currentSize] = item;
				percolateUp();	
		}
		
		}

	/**
	 * Internal method to percolate up in the heap. 
	 * 
	 */
	private void percolateUp(){
		//assign the child to the last element		
		int child = currentSize;
		boolean done = false;
		//while the variable is false		
		while(!done){
			//assign the parent to the correct index			
			int parent = child/2;
			//if the parent is 0, percolating is done			
			if (parent == 0){
				done = true;
			}
			//otherwise if the object at index child's priority is less than 
			//the parent's priority, percolating is done			
			else if(array[child].compareTo(array[parent]) <= 0){
				done = true;
			}
			//if the child is bigger, swap the two elements and continue loop			
			else {
				PriorityQueueItem<E> temp = array[child];
				array[child] = array[parent];
				array[parent] = temp;
				child = parent;
				
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
		//return the first element, but don't remove it		
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
		// Remove first element and save into a variable
		PriorityQueueItem<E> temp = array[1];
//		this might have to be currentSize -1 or +1
		// Insert the last element into the first index
		array[1] = array[currentSize];
//		percolateDown(1);
//		am I supposed to be using buildHeap? 	
		buildHeap();
		//decrease the current size by one		
		currentSize--;
		//return the element deleted 		
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
		//until the queue is empty, dequeue the queue		
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
//		use left and right variables
			boolean donedown = false;
			//assign the parent to the hole index			
			int parent = hole;
			//while the boolean is false and the index*2 +1 is less than size			
			while(!donedown && (hole*2) +1 <= currentSize){
			//if the left node is less than right node
			if(array[hole*2].compareTo(array[hole*2 +1]) == -1){
				//if the parent node is less than the right node				
				if(array[parent].compareTo(array[(hole*2) +1]) == -1){
					//swap spots					
					PriorityQueueItem<E> swap = array[parent];
					array[parent] = array[(hole*2)+1];
					array[(hole*2)+1] = swap;
					//increase the hole index and assign parent					
					hole = (hole*2) +1;
					parent = hole;
				}
				//otherwise if the right node is less than the parent
				//percolating down is complete				
				else if(array[(hole*2) +1].compareTo(array[parent]) <= 0){
					donedown = true;
				}
				//if the left node is bigger than the right			
			}else if(array[hole*2].compareTo(array[(hole*2) +1]) > 0 ){
				//if the parent node is less than the left				
				if(array[parent].compareTo(array[(hole*2)]) == -1){
					//swap spots					
					PriorityQueueItem<E> swap = array[parent];
					array[parent] = array[(hole*2)];
					array[(hole*2)] = swap;
					//increase the hole index and assign parent					
					hole = hole*2;
					parent = hole;
				}
				//otherwise if the left node is less than the parent
				//percolating down is complete	
				else if(array[hole*2].compareTo(array[parent]) <= 0){
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
		//assign new array to a new copy
		newArray = (PriorityQueueItem<E>[]) Array.newInstance(PriorityQueueItem.class, array.length * 2);
		//assign correct indexes and then assign the new Array to the array var		
		for (int i = 0;i < array.length;i++)
			newArray[i] = array[i];
		array = newArray;
		}

	@Override
	public boolean isEmpty()
		{
		//if the size is 0 return true, otherwise false		
		if(currentSize == 0)
			return true;
		return false;
		}
	}