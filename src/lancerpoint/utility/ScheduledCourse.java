package lancerpoint.utility;

//TODO - ALLAN DO THIS SHIT
public class ScheduledCourse {
	
	private String course, credits, days, days2, time, time2, location, instructor;
	private boolean available = true; //true = can access
	
	public ScheduledCourse(String course, String credits, String days, String days2, String time,String time2, String location,String instructor, boolean available) {
		this.course = course;
		this.credits = credits;
		this.days = days;
		this.days2 = days2;
		this.time = time;
		this.time2 = time2;
		this.location = location;
		this.instructor = instructor;
		this.available = available;
	}
	
	public String getCourse () {
		return this.course;
	}
	
	public String getCredits() {
		return this.credits;
	}
	
	public String getDays() {
		return this.days;
	}
	
	public String getDays2() {
		return this.days2;
	}
	
	public String getTime() {
		return this.time;
	}
	
	public String getTime2() {
		return this.time2;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public String getInstructor() {
		return this.instructor;
	}
	
	public boolean getAvailability() {
		return this.available;
	}
	
	@Override
	public String toString() {
		
		return "Course: " + course + "\nCredits: " + credits + "\nDays: " + days + "/" + days2 + "\nTime: " + time + "/" + time2 + "\nLocation: " + location + "\nInstructor: " + instructor;
	}
	
}
