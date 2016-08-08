

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Class to represent every Course in our Course Registration environment
 * 
 *
 */

public class Course
	{

	private String courseCode;
	private String name;

	// Number of students allowed in the course
	private int maxCapacity;
	// Number of students enrolled in the course
	private int classCount;
	private int count = 0;

	// The PriorityQueue structure
	private PriorityQueue<Student> registrationQueue;

	// List of students who are finally enrolled in the course
	private List<Student> courseRoster;

	public Course(String classCode, String name, int maxCapacity)
		{
			this.courseCode = classCode;
			this.name = name;
			this.maxCapacity = maxCapacity;
			courseRoster = new ArrayList<Student>();
			registrationQueue = new PriorityQueue<Student>();
			classCount = 0;
		}


	/**
	 * Creates a new PriorityqueueItem - with appropriate priority(coins) and
	 * this student in the item's queue. Add this item to the registrationQueue.
	 * 
	 * @param student
	 *            the student
	 * @param coins
	 *            the number of coins the student has
	 */
	public void addStudent(Student student, int coins)
		{
		// This method is called from Studentcenter.java
		
		// Enqueue a newly created PQItem.
		PriorityQueueItem<Student> newStudent = 
				new PriorityQueueItem<Student>(coins);
		//assign the student object to the queue of the newStudent Item		
		newStudent.getList().enqueue(student);
		// Checking if a PriorityQueueItem with the same priority already exists
		// is done in the enqueue method.
		//add the new student to the registration queue		
		registrationQueue.enqueue(newStudent);
		//increase the class count
		classCount++;
		}

	/**
	 * Populates the courseRoster from the registration list.
	 * Use the PriorityQueueIterator for this task.
	 */
	public void processRegistrationList()
		{
		//create an iterator		
		Iterator<PriorityQueueItem<Student>> itr =  registrationQueue.iterator();
		//while there's a next position and the count is less than the capacity
		//of students allowed		
		while(itr.hasNext() && count < maxCapacity){
			//assign the iterator's queue to a variable			
			Queue<Student> queue = itr.next().getList();
			//while that queue isn't empty and the count is less than capacity			
			while(!queue.isEmpty() && count < maxCapacity){
				//add the information to the course roster				
				courseRoster.add((queue.dequeue()));
				//increase the count of students				
				count++;
			}
		}

		}
	/**
	 * returns the course name
	 */
	public String getCourseName()
		{
	
		return this.name;
		}
	/**
	 * returns the course code
	 */
	public String getCourseCode()
		{
		
		return this.courseCode;
		}
	/**
	 * returns the course roster
	 */
	public List<Student> getCourseRegister()
		{
				return this.courseRoster;
		}
	}
