

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
public class StudentCenter {
	private static String studentName;
	private static String studentId;
	private static String StudentHeader;
	private static int DEFAULT_POINTS = 100;
	private static Student newStudent;
	// Global lists of all courses and students
	private static List<Course> courseList = new ArrayList<Course>();
	private static List<Student> studentList = new ArrayList<Student>();

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Bad invocation! Correct usage: "
					+ "java StudentCentre <StudentCourseData file>"
					+ "<CourseRosters File> + <StudentCourseAssignments File>");
			System.exit(1);
		}

		boolean didInitialize = readData(args[0]);

		if (!didInitialize) {
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
	public static boolean readData(String fileName) {
		try {
			//create a boolean for while loops			
			boolean done = false;			
			String[] courses = null;
			//initialize the file			
			File importFile = new File(fileName);
			Scanner filescnr = new Scanner(importFile);

			String inputLine = null;
			//if the scanner has a next line, assign it to a variable before
			//the while loop begins			
			if (filescnr.hasNextLine())
				inputLine = filescnr.nextLine();
			//read each line from the file until there aren't any more lines
			while (inputLine != null && !done) {
				//if the line has the header points/students				
				if (inputLine.contains("#Points/Student")) {
					//make sure there's a next line					
					if (filescnr.hasNextLine()) {
						//assign that line to the variable inputLine						
						inputLine = filescnr.nextLine();
						//assign the number to a variable to instantiate student						
						StudentHeader = inputLine.trim();
					}
					//assign the next line to my variable					
					inputLine = filescnr.nextLine();
					//go back to the top of the while loop					
					continue;
				}
				//if the line is the header and the boolean is false				
				if (inputLine.contains("#Courses") && !done) {
					//grab the next line, the start of a student				
					String line = filescnr.nextLine();
					//while that line doesn't contain the next header					
					while (!line.contains("#Student")) {
						//assign the course to a variable & take out space						
						String secondCourse = line.trim();
						//split the course string						
						String[] courseInfo = secondCourse.split(" ");
						//create a new course object						
						Course newSecondCourse = new Course(courseInfo[0],
								courseInfo[1], Integer.parseInt(courseInfo[2]));
						//add the course to the course list						
						courseList.add(newSecondCourse);
						//grab the next line						
						line = filescnr.nextLine();
					}
					//assign the line variable to input line					
					inputLine = line;
					//go back to the top of the while loop										
					continue;

				}
				//if the line is the header				
				if (inputLine.contains("#Student")) {
					//assign the input to the next line with no space					
					inputLine = filescnr.nextLine().trim();
					//make sure the student object is cleared					
					Student newStudent = null;
					//while the iteration doesn't have the next header					
					while (!inputLine.contains("#Student") && !done) {
						//if the line is a name, assign to a name variable						
						if (inputLine.charAt(0) >= 'A'
								&& inputLine.charAt(0) <= 'Z'
								&& inputLine.charAt(1) != 'S') {
							studentName = inputLine;
						}
						//if the line is a number, assign it to an ID						
						if (inputLine.charAt(0) >= '0'
								&& inputLine.charAt(0) <= '9') {
							studentId = inputLine;
							newStudent = new Student(studentName, studentId,
									Integer.parseInt(StudentHeader));
						}
						//if the line starts with CS assign it to a course
						if (inputLine.charAt(0) == 'C'
								&& inputLine.charAt(1) == 'S') {
							courses = inputLine.split(" ");
							//if there is a course							
							if (courses != null) {
								//iterate through the course list								
								for (int i = 0; i < courseList.size(); i++) {
									//if the course code matches the course 
									//course code									
									if (courseList.get(i).getCourseCode()
											.equals(courses[0])) {
										//add the course to the student and add
										//the student to the course										
										newStudent.addToCart(courseList.get(i));
										if(newStudent.deductCoins(Integer.parseInt(courses[1]))){
											courseList.get(i).addStudent(
												newStudent,
												Integer.parseInt(courses[1]));
										}
										break;
									}

								}

							}

							
						}
						//if the scanner has a next line, assign variable						
						if (filescnr.hasNextLine()) {
							inputLine = filescnr.nextLine().trim();
						}
						//if there is not another line, assign done to true						
						else {
							done = true;
							//end student loop							
							break;
						}

					}
					//add student to student list					
					studentList.add(newStudent);
					//clear all variables
					studentName = null;
					studentId = null;
					courses = null;
					//continue with large loop					
					continue;
				}
				//if the scanner has another line, assign the next line to a var				
				if (filescnr.hasNextLine()) {
					inputLine = filescnr.nextLine();
				}
				//otherwise assign done to true to end the loop				
				else {
					done = true;
				}
			}
			//close the scanner			
			filescnr.close();
		}

		catch (Exception e) {
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
	public static void generateAndWriteResults(String fileName1,
			String fileName2) {
		try {
			// List Students enrolled in each course
			PrintWriter writer = new PrintWriter(new File(fileName1));
			for (Course c : courseList) {
				writer.println("-----" + c.getCourseCode() + " "
						+ c.getCourseName() + "-----");

				// Core functionality
				c.processRegistrationList();

				// List students enrolled in each course
				int count = 1;
				for (Student s : c.getCourseRegister()) {
					writer.println(count + ". " + s.getid() + "\t"
							+ s.getName());
					s.enrollCourse(c);
					count++;
				}
				writer.println();
			}
			writer.close();

			// List courses each student gets
			writer = new PrintWriter(new File(fileName2));
			for (Student s : studentList) {
				writer.println("-----" + s.getid() + " " + s.getName()
						+ "-----");
				int count = 1;
				for (Course c : s.getEnrolledCourses()) {
					writer.println(count + ". " + c.getCourseCode() + "\t"
							+ c.getCourseName());
					count++;
				}
				writer.println();
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Look up Course from classCode
	 * 
	 * @param courseCode
	 * @return Course object
	 */
	private static Course getCourseFromCourseList(String courseCode) {
		for (Course c : courseList) {
			if (c.getCourseCode().equals(courseCode)) {
				return c;
			}
		}
		return null;
	}
}