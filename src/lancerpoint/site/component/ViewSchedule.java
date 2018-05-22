package lancerpoint.site.component;

import java.util.ArrayList;
import java.util.HashMap;

import lancerpoint.site.CookieGrabber;
import lancerpoint.site.Socket;
import lancerpoint.utility.ScheduledCourse;
import lancerpoint.utility.Utility;

/**
 * Class with methods needed to view a schedule of a user off LancerPoint.
 */
public class ViewSchedule {
    
    private static final String[] GET_URLS = { "/ssomanager/c/SSB?pkg=bwskcrse.P_CrseSchdDetl",
    	"bwskcrse.P_CrseSchdDetl",
    	"/prod/bwskcrse.P_CrseSchdDetl"
    };
    
    /**
     * Grabs and returns available terms for schedule viewing.
     * <p>
     * 
     * @param username Username
     * @param password Password
     * @return         HashMap<String, String> of term, value.
     */
    public static HashMap<String, String> getScheduleTerms(String username, String password) {
		return Utility.getAvailableTerms(username, password, GET_URLS);
	}
    
    /**
     * Returns the schedule in an array of ScheduledCourse.
     * <p>
     * 
     * @param username Username
     * @param password Password
     * @param term     Term value
     * @return         ArrayList<ScheduledCourse> representing the schedule.
     */
    public static ArrayList<ScheduledCourse> getSchedule(String username, String password, String term) {
    	String loginTicket = Login.getLoginTicket(username, password);
    	
    	ArrayList<ScheduledCourse> fullSchedule = new ArrayList<ScheduledCourse>();
    	
    	if (loginTicket != null) {
	    	HashMap<String, String> cookies = CookieGrabber.getSESSID(GET_URLS, loginTicket);
	  
	    	boolean check = false;
	    	String totalCredits = null;
	    	
	    	if (cookies.size() == 3) { //Need 3 cookies to continue.
		    	ArrayList<String> response = Socket.httpsSocketPost("selfservice.pasadena.edu",
		              "/prod/bwskcrse.P_CrseSchdDetl",
		             "SESSID=" + cookies.get("SESSID") + "; IDMSESSID=" + cookies.get("IDMSESSID"),
		             "term_in=" + term);
		    	
		    	response = Utility.remove5A8(response);
		    	
		    	for (int i = 0; i < response.size(); i++) {
		    		String line = response.get(i);
		    		
		    		//System.out.println(line);
		    		
		    		String nextLine = response.get(i+1); //Throws error when no classes.
		    		
		    		if (line.contains("<SPAN class=\"infotext\">") && line.contains("Course details not available")) {
		    			System.out.println("Course details not available for the selected term.");
		    			break;
		    		}
		    		
		    		if (line.contains("<TR>") && nextLine.contains("<TD CLASS=\"dddefault\">") && !(nextLine.contains("<TD CLASS=\"dddefault\">&nbsp;"))) {
		    			check = true;
		    		} else if (line.contains("<TR>") && nextLine.contains("<TD CLASS=\"dddefault\">&nbsp;")) {
		    			continue;
		    		} else if (line.contains("Total Credits")) {
		    			totalCredits = Utility.parse("<TD CLASS=\"dddefault\"><B>", "<", nextLine).trim();
		    			System.out.println(totalCredits);
		    			break;
		    		}
		    		
		    		if (check) {
		    			String course = null,credits = null,days = null, days2 = null ,time = null, time2 = null,location = null,instructor = null;
		    			String fifteen = response.get(i + 15);
		    			
		    			for (int j = 0; j < 15; j++) {
		    				String now = response.get(i + j);
		    				
		    				if (j == 0 && !response.get(i + 18).contains("Total Credits") && fifteen.contains("<TD CLASS=\"dddefault\">&nbsp;")) {
		    					days2 = Utility.parse("<TD CLASS=\"dddefault\">", "<", response.get(i+23));
		    					time2 = Utility.parse("<TD CLASS=\"dddefault\">", "<", response.get(i+24));
		    				}
		    				if (response.get(i+4).contains("Fully Online")) {
		    					days = "TBA";
		    					time = "TBA";
		    					location = "TBA";
		    				}
		    				
		    				if (j == 2) course = Utility.parse("<TD CLASS=\"dddefault\">", "<", now);
		    				if (j == 5) credits = Utility.parse("<TD CLASS=\"dddefault\">", "<", now).trim();
		    				if (!(response.get(i+4).contains("Fully Online"))) {
		    					if (j == 9) days = Utility.parse("<TD CLASS=\"dddefault\">", "<", now);
		        				if (j == 10) time = Utility.parse("<TD CLASS=\"dddefault\">", "<", now);
		        				if (j == 11) location = Utility.parse("<TD CLASS=\"dddefault\">", "<", now);	
		    				}
		    				if (j == 12) {
		    					instructor = Utility.parse("<TD CLASS=\"dddefault\">", "<", now);
		    					
		    					ScheduledCourse sched = new ScheduledCourse(course, credits, days, days2, time, time2, location, instructor, true);
		    					fullSchedule.add(sched);
		    				}
		    				
		    			}
		    		}
		    		check = false;
		    	}
	    	}
    	}
    				
    	return fullSchedule;
    }


}