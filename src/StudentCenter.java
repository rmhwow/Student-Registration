///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Program 3
// Files:            Queue.java,PriorityQueue.java, PriorityQueueIterator.java,
//					 PriorityQueueItem.java, Course.java, StudentCenter.java						 
// Semester:         CS 367 Spring 2016
//
// Author:           Morgan O'Leary
// Email:            oleary4@wisc.edu
// CS Login:         o-leary
// Lecturer's Name:  Jim Skrentny
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * Student Center abstraction for our simulation. Execution starts here.
 * 
 * @author CS367
 *
 */
public class StudentCenter
	{
	private static String studentName;
	private static String studentId;
	private static String StudentHeader;
	private static int DEFAULT_POINTS = 100;
	private static Student newStudent;
	// Global lists of all courses and students
	private static List<Course> courseList = new ArrayList<Course>();
	private static List<Student> studentList = new ArrayList<Student>();

	public static void main(String[] args)
		{
		if(args.length != 3)
			{
			System.err.println("Bad invocation! Correct usage: " + 
			"java StudentCentre <StudentCourseData file>" + 
					"<CourseRosters File> + <StudentCourseAssignments File>");
			System.exit(1);
			}

		boolean didInitialize = readData(args[0]);

		if(!didInitialize)
			{
			System.err.println("Failed to initialize the application!");
			System.exit(1);
			}

		generateAndWriteResults(args[1], args[2]);
		}

	/**
	 * 
	 * @param fileName
	 *            - input file. Has 3 sections - #Points/Student, #Courses, and
	 *            multiple #Student sections. See P3 page for more details.
	 * @return true on success, false on failure.
	 * 
	 */
	public static boolean readData(String fileName)
	{
		try
		{
			// make sure to call course.addStudent() appropriately.
			boolean done = false;
			String[] courses = null;
			File importFile = new File(fileName);
			Scanner filescnr = new Scanner(importFile);
			
			String inputLine = null;
			if (filescnr.hasNextLine())
				inputLine = filescnr.nextLine();
			
			while(inputLine != null && !done){
//				System.out.println(inputLine);
				if(inputLine.contains("#Points/Student")){
					if (filescnr.hasNextLine()){
						inputLine = filescnr.nextLine();
						StudentHeader = inputLine.trim();
					}
					inputLine = filescnr.nextLine();
					continue;
				}
				if(inputLine.contains("#Courses") && !done){
					String line = filescnr.nextLine();
					while (!line.contains("#Student")){
						String secondCourse = line.trim();
						String[] courseInfo = secondCourse.split(" ");
						Course newSecondCourse = new Course(courseInfo[0],
								courseInfo[1], Integer.parseInt(courseInfo[2]));
						courseList.add(newSecondCourse);
						line = filescnr.nextLine();
					}
						inputLine = line;
						continue;

					
				}
				if(inputLine.contains("#Student") ){
					inputLine = filescnr.nextLine().trim();
					while(!inputLine.contains("#Student") && !done){
						if(inputLine.charAt(0) >='A' && inputLine.charAt(0) <= 'Z' && inputLine.charAt(1) != 'S'){
								studentName = inputLine;
						}if(inputLine.charAt(0) >='0' && inputLine.charAt(0) <= '9'){
								studentId = inputLine;
						}
						if(inputLine.charAt(0) == 'C' && inputLine.charAt(1) == 'S'){
							 courses = inputLine.split(" ");
						}
						if(studentName != null && studentId != null){
							newStudent = new Student(studentName, 
								studentId, Integer.parseInt(StudentHeader));
							System.out.println(newStudent.getName() + " " + newStudent.getid());
						}
						if(courses != null && newStudent != null){
							for(int i = 0; i < courseList.size(); i++){
								if(courseList.get(i).getCourseCode().equals(courses[0])){
									newStudent.addToCart(courseList.get(i));
//									System.err.println(newStudent);
									courseList.get(i).addStudent(newStudent, Integer.parseInt(courses[1]));
								}
							}
						}
						
						if(filescnr.hasNextLine()){
							inputLine = filescnr.nextLine().trim();
						} else{
							done = true;
							break;
						}
					}
					studentName = null;
					studentId = null;
					newStudent = null;
					continue;
				}
				if(filescnr.hasNextLine()){
					inputLine = filescnr.nextLine();
				}else{
					done = true;
				}
			}
			filescnr.close();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("File Parse Error");
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param fileName1
	 *            - output file with a line for each course
	 * @param fileName2
	 *            - output file with a line for each student
	 */
	public static void generateAndWriteResults(String fileName1, String fileName2)
		{
		try
			{
			// List Students enrolled in each course
			PrintWriter writer = new PrintWriter(new File(fileName1));
			for (Course c : courseList)
				{
				writer.println("-----" + c.getCourseCode() + " " + c.getCourseName() + "-----");

				// Core functionality
				c.processRegistrationList();

				// List students enrolled in each course
				int count = 1;
				for (Student s : c.getCourseRegister())
					{
					writer.println(count + ". " + s.getid() + "\t" + s.getName());
					s.enrollCourse(c);
					count++;
					}
				writer.println();
				}
			writer.close();

			// List courses each student gets
			writer = new PrintWriter(new File(fileName2));
			for (Student s : studentList)
				{
				writer.println("-----" + s.getid() + " " + s.getName() + "-----");
				int count = 1;
				for (Course c : s.getEnrolledCourses())
					{
					writer.println(count + ". " + c.getCourseCode() + "\t" + c.getCourseName());
					count++;
					}
				writer.println();
				}
			writer.close();
			}
		catch(FileNotFoundException e)
			{
			e.printStackTrace();
			}
		}

	/**
	 * Look up Course from classCode
	 * 
	 * @param courseCode
	 * @return Course object
	 */
	private static Course getCourseFromCourseList(String courseCode)
		{
		for (Course c : courseList)
			{
			if(c.getCourseCode().equals(courseCode))
				{
				return c;
				}
			}
		return null;
		}
	}