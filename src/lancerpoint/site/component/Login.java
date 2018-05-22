package lancerpoint.site.component;
import java.util.ArrayList;
import java.util.HashMap;

import lancerpoint.site.Socket;
import lancerpoint.utility.Utility;

/**
 * Login class. Purpose is to grab the login ticket after login in for use.
 */
public class Login {

	/**
	 * Grabs initial JSESSIONID and lt value needed to login.
	 * <p>
	 * 
	 * @return HashMap<String, String> of JSESSIONID and lt cookies.
	 */
    private static HashMap<String, String> getInitialData() {
        ArrayList<String> response = Socket.httpsSocketGet("login.pasadena.edu",
                "/cas-web/login?service=https%3A%2F%2Flancerpoint.pasadena.edu%2Fc%2Fportal%2Flogin",
                null);
        
        HashMap<String, String> cookies = new HashMap<String, String>();

        for (String res : response) {
            if (res.contains("Set-Cookie: JSESSIONID=")) { //Found the JSESSIONID line
                cookies.put("JSESSIONID", Utility.parse("Set-Cookie: JSESSIONID=", ";", res)); //Parsing the JSESSIONID
            } else if (res.contains("\"lt\" value=\"")) { //Found the lt value
                cookies.put("lt", Utility.parse("\"lt\" value=\"", "\"", res)); //Parsing the lt value
            }
        }
        
        return cookies;
    }

    /**
     * Logs into LanerPoint and grabs the login ticket needed to grab all information throughout the site.
     * <p>
     * 
     * @param username Username
     * @param password Password
     * @return         The login ticket cookie.
     */
    public static String getLoginTicket(String username, String password) {
    	HashMap<String, String> cookies = getInitialData();
    	
        ArrayList<String> response = Socket.httpsSocketPost("login.pasadena.edu",
                "/cas-web/login;" + "jsessionid=" + cookies.get("JSESSIONID") + "?service=https%3A%2F%2Flancerpoint.pasadena.edu%2Fc%2Fportal%2Flogin",
                "JSESSIONID=" + cookies.get("JSESSIONID"),
                "username=" + username + "&password=" + password + "&lt=" + cookies.get("lt") + "&_eventId=submit&submit_form=LOGIN");

        String ticket = null;
        boolean success = false;

        for (String res : response) {
            if (res.contains("Moved Temporarily")) {
                success = true;
            } 
            if (success
            		&& res.contains("ticket=")) {
            	//System.out.println(res);
                ticket = res.substring(65);
            }
        }

        return ticket;
    }
    
    /**
     * Grabs real name of user through view transcript since Login screen does not contain it.
     * <p>
     * 
     * @param username
     * @param password
     * @return Real name of user.
     */
    public static String getRealName(String username, String password) {
    	return ViewTranscript.getRealName(username, password);
    }

}