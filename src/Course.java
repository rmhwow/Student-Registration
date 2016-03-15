///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  StudentCenter.java
// File:             Course.java
// Semester:         CS 367 Spring 2016
//
// Author:           Morgan O'Leary oleary4@wisc.edu
// CS Login:         o-leary
// Lecturer's Name:  Jim Skrentny

//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Class to represent every Course in our Course Registration environment
 * 
 * @author CS367
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
		// Checking if a PriorityQueueItem with the same priority already exists
		// is done in the enqueue method.
		registrationQueue.enqueue(newStudent);

		// TODO : see function header
		classCount++;
		}

	/**
	 * Populates the courseRoster from the registration list.
	 * Use the PriorityQueueIterator for this task.
	 */
	public void processRegistrationList()
		{
		// TODO : populate courseRoster from registrationQueue
		// Use the PriorityQueueIterator for this task.
		Iterator<PriorityQueueItem<Student>> itr =  registrationQueue.iterator();
		while(itr.hasNext()){
			if(!itr.next().getList().isEmpty()){
				courseRoster.add((itr.next().getList().dequeue()));
			}
		}

		}

	public String getCourseName()
		{
	
		return this.name;
		}

	public String getCourseCode()
		{
		
		return this.courseCode;
		}

	public List<Student> getCourseRegister()
		{
				return this.courseRoster;
		}
	}
