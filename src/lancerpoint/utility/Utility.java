package lancerpoint.utility;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import lancerpoint.site.CookieGrabber;
import lancerpoint.site.Socket;
import lancerpoint.site.component.Login;

/**
 * General utility class.
 */
public class Utility {
	
	/**
     * 
     * @param location		The URL redirect
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decoded(String location) throws UnsupportedEncodingException {
    	
    	String result = java.net.URLDecoder.decode(location, "UTF-8");
    	return result;
    }

	/**
     * Returns the string between the inputed String s and String e in String mixed.
     *
     * Example:
     *         String s = "My dog's name is Bob.";
     *         parse("is ", ".", s); //This would equal "Bob" as it returns what's between "is " and "."
     *
     * @param s     Start of index to parse
     * @param e     End limit to parse
     * @param mixed Entire string
     * @return      Parsed string
     */
    public static String parse(String s, String e, String mixed) {
        try {
            return mixed.substring(mixed.indexOf(s) + s.length(), mixed.indexOf(e, mixed.indexOf(s) + s.length()));
        } catch(Exception ee) {
            ee.printStackTrace();
            return null;
        }
    }
    
    /**
     * Grabs and returns available terms for schedule viewing.
     * <p>
     * 
     * @param username Username
     * @param password Password
     * @param getUrls  Get urls
     * @return         HashMap<String, String> of term, value.
     */
    public static HashMap<String, String> getAvailableTerms(String username, String password, String[] getUrls) {
		String loginTicket = Login.getLoginTicket(username, password);
		
		HashMap<String, String> data = new HashMap<String, String>();
		
		if (loginTicket != null) {
			HashMap<String, String> cookies = CookieGrabber.getIDMJSESSION(getUrls, loginTicket);
			
	    	ArrayList<String> response = Socket.httpsSocketGet("selfservice.pasadena.edu",
	    			getUrls[2],
	    			"IDMSESSID=" + cookies.get("IDMSESSID") + "; JSESSIONID=" + cookies.get("JSESSIONID"));
	
	        for (String res : response) {
	            if (res.contains("<OPTION VALUE")) {
	            	data.put(res.substring(23), Utility.parse("\"", "\"", res));
	            }
	        }
		}
        
        return data;
	}
    
    /**
     * Removes the random "5a8" strings in LancerPoint web pages.
     * <p>
     * 
     * @param response Response to fix
     * @return         Fixed response.
     */
    public static ArrayList<String> remove5A8(ArrayList<String> response) {
    	ArrayList<String> removed = new ArrayList<String>();
    	
    	for (int i=0; i<response.size(); i++) {
    		if (response.get(i).matches("5a.*"))  {// .matches("5a.*") is the right method
    			String r = removed.get(removed.size()-1) + response.get(i+1);
    			removed.remove(removed.size()-1);
    			removed.add(r);
    			i++;
    		} else {
    			removed.add(response.get(i));
    		}
    	}
    	
    	return removed;
    }
    
}
