package lancerpoint.site.component;

import java.util.ArrayList;
import java.util.HashMap;

import lancerpoint.site.CookieGrabber;
import lancerpoint.site.Socket;
import lancerpoint.utility.Utility;

/**
 * Class with functions to grab registration dates off LancerPoint.
 */
public class ViewRegistration {
	
	private static final String[] GET_URLS = {
		"/ssomanager/c/SSB?pkg=bwskrsta.P_RegsStatusDisp",
		"bwskrsta.P_RegsStatusDisp",
		"/prod/bwskrsta.P_RegsStatusDisp"
	};
	
	/**
	 * Grabs available terms that we can view registration dates.
	 * <p>
	 * 
	 * @param username Username
	 * @param password Password
	 * @return HashMap<String, String> of term, value.
	 */
	public static HashMap<String, String> getRegistrationTerms(String username, String password) {
		return Utility.getAvailableTerms(username, password, GET_URLS);
	}
	
	/**
	 * Grabs and returns registration date for inputed username and password.
	 * <p>
	 * 
	 * @param username Username
	 * @param password Password
	 * @param term     Term number
	 * @return Registration start and end date.
	 */
	public static String getRegistrationDate(String username, String password, String term) {
		StringBuilder date = new StringBuilder();
	    
		String loginTicket = Login.getLoginTicket(username, password);
		
		if (loginTicket != null) {
			HashMap<String, String> cookies = CookieGrabber.getSESSID(GET_URLS, loginTicket);
			
	    	if (cookies.size() == 3) {
		    	ArrayList<String> response = Socket.httpsSocketPost("selfservice.pasadena.edu",
			              "/prod/bwskrsta.P_RegsStatusDisp",
			             "SESSID=" + cookies.get("SESSID") + "; IDMSESSID=" + cookies.get("IDMSESSID"),
			             "term_in=" + term);
		    	
		    	response = Utility.remove5A8(response);
		    	
		    	for (int i=0; i<response.size(); i++) {
		    		if (response.get(i).contains(">End Time<")) {
		    			date.append("Begins: " + Utility.parse(">", "<", response.get(i+3)));
		    			date.append(" at " + Utility.parse(">", "<", response.get(i+4)));
		    			date.append("\nEnds: " + Utility.parse(">", "<", response.get(i+5)));
		    			date.append(" at " + Utility.parse(">", "<", response.get(i+6)));
		    		}
		    	}
	    	}
		}
		
		return date.toString();
	}

}
