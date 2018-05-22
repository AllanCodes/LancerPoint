package lancerpoint.site.component;

import java.util.ArrayList;
import java.util.HashMap;

import lancerpoint.site.CookieGrabber;
import lancerpoint.site.Socket;
import lancerpoint.utility.GradedCourse;
import lancerpoint.utility.Utility;

/**
 * Class with a method used to grab transcript grades.
 */
public class ViewTranscript {
	
	private static final String[] GET_URLS = {
		"/ssomanager/c/SSB?pkg=bwskotrn.P_ViewTermTran",
		"bwskotrn.P_ViewTermTran",
		"/prod/bwskotrn.P_ViewTermTran"
	};

	protected static String getRealName(String username, String password) {
		String loginTicket = Login.getLoginTicket(username, password);
		
		if (loginTicket != null) {
			HashMap<String, String> cookies = CookieGrabber.getSESSID(GET_URLS, loginTicket);
	        
	        if (cookies.size() == 3) { //Three cookies must be grabbed. SESSID, IDMSESSID, and JSESSIONID
		        ArrayList<String> response = Socket.httpsSocketPost("selfservice.pasadena.edu",
		                "/prod/bwskotrn.P_ViewTran",
		                "SESSID=" + cookies.get("SESSID") + "; IDMSESSID=" + cookies.get("IDMSESSID"),
		                "levl=&tprt=W");
		        
		        Utility.remove5A8(response);
		        
		        for (int i=0; i<response.size(); i++) {
		        	if (response.get(i).contains("staticheaders")) {
		        		return Utility.parse(" ", "<", response.get(i+1).substring(8));
		        	}
		        }
	        }
		}
		
		return "";
	}

	/**
	 * Grabs the grades off the transcript of the inputed user.
	 * <p>
	 * 
	 * @param username Username
	 * @param password Password
	 * @return         ArrayList<GradedCourse> respresenting all course grades.
	 */
    public static ArrayList<GradedCourse> getTranscriptGrades(String username, String password) {
    	String loginTicket = Login.getLoginTicket(username, password);
    	
        ArrayList<GradedCourse> courseGrades = new ArrayList<GradedCourse>();
        
        if (loginTicket != null) {
	    	HashMap<String, String> cookies = CookieGrabber.getSESSID(GET_URLS, loginTicket);
	        
	        try {/* *************** added try catch method ************ */
				if (cookies.size() == 3) { //Three cookies must be grabbed. SESSID, IDMSESSID, and JSESSIONID
				    ArrayList<String> response = Socket.httpsSocketPost("selfservice.pasadena.edu",
				            "/prod/bwskotrn.P_ViewTran",
				            "SESSID=" + cookies.get("SESSID") + "; IDMSESSID=" + cookies.get("IDMSESSID"),
				            "levl=&tprt=W");

				    String currentTerm = null;
				    boolean foundClass = false;
				    
				    response = Utility.remove5A8(response);

				    for (int i=0; i<response.size(); i++) {
				        String next = response.get(i);

				        if (next.contains("Term: ")) {
				            currentTerm = Utility.parse("Term: ", "<", next);
				        } else if (next.contains("<TR>") && response.get(i+1).contains("<TD CLASS=\"dddefault\">")) {
				            foundClass = true;
				        } else if (next.contains("Total Institution:")) {
				            String totalUnits = Utility.parse("text\">", "<", response.get(i+4)).trim();
				            String gpa = Utility.parse("text\">", "<", response.get(i+6)).trim();

				            courseGrades.add(new GradedCourse("GPA", "Cumalative GPA", gpa, "All", totalUnits));
				            
				            break;
				        }

				        if (foundClass) {
				            String courseName = null;
				            String description = null;
				            String grade = null;
				            String units = null;

				            courseName = Utility.parse(">", "<", response.get(i+1)) + " " + Utility.parse(">", "<", response.get(i+2));
				            
				            if (response.get(i+4).contains("COLSPAN=\"5\""))
				                description = Utility.parse(">", "<", response.get(i+4));
				                grade = Utility.parse(">", "<", response.get(i+5));
				                units = Utility.parse("text\">", "<", response.get(i+6)).trim();
				            
				                GradedCourse gc = new GradedCourse(courseName, description, grade, currentTerm, units);
				                courseGrades.add(gc);

				            foundClass = false;
				        }
				    }
				}
			} catch (Exception e) {
				e.printStackTrace();
				courseGrades.add(0, null);
			}

        }

        return courseGrades;
    }

}