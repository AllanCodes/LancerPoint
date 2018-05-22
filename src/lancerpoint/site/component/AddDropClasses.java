package lancerpoint.site.component;

import java.util.ArrayList;
import java.util.HashMap;

import lancerpoint.site.CookieGrabber;
import lancerpoint.site.Socket;
import lancerpoint.utility.Utility;

/**
 * Waiting until Nov. 18 to test registration.
 */
public class AddDropClasses {
    
    private static final String[] GET_URLS = {
    	"/ssomanager/c/SSB?pkg=bwskfreg.P_AddDropCrse",
    	"bwskfreg.P_AddDropCrse",
    	"/prod/bwskfreg.P_AddDropCrse"
    };
    
    /**
	 * Grabs available terms that we can add and drop classes.
	 * <p>
	 * 
	 * @param username Username
	 * @param password Password
	 * @return HashMap<String, String> of term, value.
	 */
    public static HashMap<String, String> getRegistrationTerms(String username, String password) {
		return Utility.getAvailableTerms(username, password, GET_URLS);
	}
    
    public static boolean addClass(String username, String password, ArrayList<Integer> CRNs, String term) {
    	//Can get schedule of passed term to see what their current schedule of classes are.
    	String loginTicket = Login.getLoginTicket(username, password);
    	
    	if (loginTicket != null) {
    		HashMap<String, String> cookies = CookieGrabber.getSESSID(GET_URLS, loginTicket);
    		
    		if (cookies.size() >= 3) {
    			String data = "term_in=" + term + "&RSTS_IN=DUMMY&assoc_term_in=DUMMY&CRN_IN=DUMMY&start_date_in=DUMMY&end_date_in=DUMMY&SUBJ=DUMMY&CRSE=DUMMY&SEC=DUMMY&LEVL=DUMMY&CRED=DUMMY&GMOD=DUMMY&TITLE=DUMMY&MESG=DUMMY&REG_BTN=DUMMY";
    			
    			for (int i=0; i<CRNs.size(); i++) {
    				data += "&RSTS_IN=RW&CRN_IN=" + CRNs.get(i) + "&assoc_term_in=&start_date_in=&end_date_in=";
    			}
    			
    			data += "&RSTS_IN=RW&CRN_IN=&assoc_term_in=&start_date_in=&end_date_in=&regs_row=0&wait_row=0&add_row=10&REG_BTN=Submit+Changes";		
    					
    			Socket.httpsSocketPost("selfservice.pasadena.edu",
		                "/prod/bwckcoms.P_Regs",
		                "SESSID=" + cookies.get("SESSID") + "; IDMSESSID=" + cookies.get("IDMSESSID"),
		                data);
    			
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public static boolean dropClass(String username, String password, ArrayList<Integer> CRNs, String term) {
    	//Can get schedule of passed term to see what their current schedule of classes are.
    	String loginTicket = Login.getLoginTicket(username, password);
    	
    	if (loginTicket != null) {
    		HashMap<String, String> cookies = CookieGrabber.getSESSID(GET_URLS, loginTicket);
    		
    		if (cookies.size() >= 3) {
    			String data = "term_in=" + term + "&RSTS_IN=DUMMY&assoc_term_in=DUMMY&CRN_IN=DUMMY&start_date_in=DUMMY&end_date_in=DUMMY&SUBJ=DUMMY&CRSE=DUMMY&SEC=DUMMY&LEVL=DUMMY&CRED=DUMMY&GMOD=DUMMY&TITLE=DUMMY&MESG=DUMMY&REG_BTN=DUMMY&MESG=DUMMY";
    			
    			for (int i=0; i<CRNs.size(); i++) {
    				data += "&RSTS_IN=DW&assoc_term_in=" + term + "&CRN_IN=" + CRNs.get(i) + "&start_date_in=&end_date_in=&SUBJ=&CRSE=&SEC=0&LEVL=Credit&CRED=&GMOD=&TITLE=";
    			}
    			
    			data += "&RSTS_IN=RW&CRN_IN=&assoc_term_in=&start_date_in=&end_date_in=&regs_row=4&wait_row=0&add_row=10&REG_BTN=Submit+Changes";
    			
    			Socket.httpsSocketPost("selfservice.pasadena.edu",
		                "/prod/bwckcoms.P_Regs",
		                "SESSID=" + cookies.get("SESSID") + "; IDMSESSID=" + cookies.get("IDMSESSID"),
		                data);
    			
    			return true;
    		}
    	}
    	
    	return false;
    }
    
}