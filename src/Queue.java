
/**
 * An ordered collection of items, where items are added to the rear and removed
 * from the front.
 */
public class Queue<E> implements QueueADT<E>
	{

	private static int initsize = 10;
	private E[] items;
	private int numItems;
	private int rearIndex = 0;
	private int frontIndex = 0;

	public Queue()
		{
		numItems = 0;
		items = (E[])new Object [initsize]; 
		}

	/**
	 * Adds an item to the rear of the queue.
	 * 
	 * @param item
	 *            the item to add to the queue.
	 * @throws IllegalArgumentException
	 *             if item is null.
	 */
	public void enqueue(E item)
		{
			if(item == null){
				throw new IllegalArgumentException();
			}
			if(items.length == numItems){
				expandCapacity();
			}
			items[rearIndex] = item;
			rearIndex = incrementIndex(rearIndex);	
			numItems++;
			
		}
	/**
	 * Increments in the index of the queue.
	 * 
	 * @param i
	 *            the Index to be increment.
	 * returns 0 is the i is the same as the queue length, otherwise it 
	 * increments by one. 
	 */
	private int incrementIndex(int i ){
		if(i == items.length){
			return 0;
		}
		return i + 1;
	}

	/**
	 * Removes an item from the front of the Queue and returns it.
	 * 
	 * @return the front item in the queue.
	 * @throws EmptyQueueException
	 *             if the queue is empty.
	 */
	public E dequeue()
		{
			if(numItems == 0){
				throw new EmptyQueueException();
			}
			E temp = items[frontIndex];
		

			frontIndex = incrementIndex(frontIndex);
			numItems--;
			
			return temp;
		}

	/**
	 * Returns the item at front of the Queue without removing it.
	 * 
	 * @return the front item in the queue.
	 * @throws EmptyQueueException
	 *             if the queue is empty.
	 */
	public E peek()
		{
			if(isEmpty()){
				throw new EmptyQueueException();
			}
			return items[frontIndex];
		}

	/**
	 * Returns true iff the Queue is empty.
	 * 
	 * @return true if queue is empty; otherwise false.
	 */
	public boolean isEmpty()
		{
			if(numItems == 0){
				return true;
			}
			return false;
		}

	/**
	 * Removes all items in the queue leaving an empty queue.
	 */
	public void clear()
		{
			while(!isEmpty()){
				dequeue();
			}

		}

	/**
	 * Returns the number of items in the Queue.
	 * 
	 * @return the size of the queue.
	 */
	public int size()
		{
		return numItems;
		}
	/**
	 * Returns an array twice the size of the current Queue.
	 * 
	 * @return a new Array with the items in place twice the 
	 * size of the original.
	 */
	private void expandCapacity()
		{
		//expanding should be done using the naive copy-all-elements approach
		E[] tempArray = (E[]) (new Object [items.length*2]);
		System.arraycopy(items, frontIndex, tempArray, frontIndex, 
				items.length-frontIndex);
		items = tempArray;
		}
	}
