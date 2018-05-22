package lancerpoint.utility;

/**
 * GradedCourse class used to represent graded courses when grabbing trandscript grades.
 */
public class GradedCourse {
	
	private final String courseName;
	private final String description;
	private final String grade;
	private final String term;
	private final String units;
	
	/**
	 * GradedCourse constructor that needs information about a course.
	 * <p>
	 * 
	 * @param courseName  Course name
	 * @param description Description of course
	 * @param grade       Grade received in course
	 * @param term        Term course was taken
	 * @param units       Units received from course
	 */
	public GradedCourse(String courseName, String description, String grade, String term, String units) {
		this.courseName = courseName;
		this.description = description;
		this.grade = grade;
		this.term = term;
		this.units = units;
	}
	
	/**
	 * Returns the name of the course.
	 * <p>
	 * 
	 * @return Name of course
	 */
	public String getCourseName() {
		return this.courseName;
	}
	
	/**
	 * Returns the description of the course.
	 * <p>
	 * 
	 * @return Description of course
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Returns the grade received from the course.
	 * <p>
	 * 
	 * @return Grade received in couse
	 */
	public String getGrade() {
		return this.grade;
	}
	
	/**
	 * Returns the term in which the course was taken.
	 * <p>
	 * 
	 * @return Term course was taken
	 */
	public String getTerm() {
		return this.term;
	}
	
	/**
	 * Returns the number of units received from the course.
	 * <p>
	 * 
	 * @return Units received.
	 */
	public String getUnits() {
		return this.units;
	}

	@Override
	public String toString() {
		return "Course name: " + this.courseName + " Description: " + this.description + " Grade: " + this.grade
				+ " Term: " + this.term + " Units: " + this.units;
	}
	
}
