package lancerpoint.site;

import java.util.ArrayList;
import java.util.HashMap;

import lancerpoint.utility.Utility;

/**
 * Class designed to grab cookies.
 * <p>
 */
public class CookieGrabber {
	
	/**
	 * Grabs a cookie of inputed cookie name.
	 * <p>
	 * 
	 * @param hostname     Host to connect to
	 * @param get          Get URL
	 * @param cookieName   Name of cookie to grab
	 * @return             Value of inputed cookie name
	 */
	public static String grabCookieGet(String hostname, String get, String sendCookie, String getCookie) {
		ArrayList<String> response = Socket.httpsSocketGet(hostname, get, sendCookie);
		
		String cookieGrabbed = null;

        for (String res : response) {
            if (res.contains(getCookie + "=")) {
            	if (res.contains(";")) {
            		cookieGrabbed = Utility.parse(getCookie + "=", ";", res); //Format - COOKIE=<cookie>;
            	} else {
            		cookieGrabbed = res.substring((res.indexOf("=") + 1), res.length()); //Format - COOKIE=<cookie>
            	}
            	
                break;
            }
        }
        
        return cookieGrabbed;
	}
	
	/**
	 * Many functions on the site require a SESSID cookie that requires the client to first
	 * retrieve JSESSIONID and IDMSESSID cookies. This method grabs all of these cookies
	 * and returns them within a HashMap<String, String> for other methods to use.
	 * <p>
	 * 
	 * @param getURLs     URLs that correspond to a specific task (view transcript, schedule, ect.)
	 * 					  that lead us to grab the desired cookies that we need.
	 * @param loginTicket Ticket originally grabbed when logged in. Needed to access cookies.
	 * @return            Cookies with a HashMap.
	 */
	public static HashMap<String, String> getSESSID(String[] getURLs, String loginTicket) {
    	HashMap<String, String> cookies = new HashMap<String, String>();
    	
    	String jSessionID = CookieGrabber.grabCookieGet("beis.pasadena.edu",
                getURLs[0],
    			null,
    			"JSESSIONID");
    	
    	cookies.put("JSESSIONID", jSessionID);
    	
    	String idmSessID = CookieGrabber.grabCookieGet("beis.pasadena.edu",
                "/ssomanager/c/SSB;jsessionid=" + jSessionID + "?pkg=" + getURLs[1] + "&ticket=" + loginTicket,
    			null,
    			"IDMSESSID");
    	
    	cookies.put("IDMSESSID", idmSessID);
    	
    	String sessID = CookieGrabber.grabCookieGet("selfservice.pasadena.edu",
                getURLs[2],
    			"IDMSESSID=" + idmSessID + "; JSESSIONID=" + jSessionID,
    			"SESSID");
    	
    	cookies.put("SESSID", sessID);
    	
    	return cookies;
    }

	/**
	 * Gets only the IDMSESSIONID and JSESSIONID cookies.
	 * <p>
	 * 
	 * @param getURLs     URLs that correspond to a specific task (view transcript, schedule, ect.)
	 * 					  that lead us to grab the desired cookies that we need.
	 * @param loginTicket Ticket originally grabbed when logged in. Needed to access cookies.
	 * @return            Cookies with a HashMap.
	 */
	public static HashMap<String, String> getIDMJSESSION(String[] getURLs, String loginTicket) {
    	HashMap<String, String> cookies = new HashMap<String, String>();
    	
    	String jSessionID = CookieGrabber.grabCookieGet("beis.pasadena.edu",
                getURLs[0],
    			null,
    			"JSESSIONID");
    	
    	cookies.put("JSESSIONID", jSessionID);
    	
    	String idmSessID = CookieGrabber.grabCookieGet("beis.pasadena.edu",
                "/ssomanager/c/SSB;jsessionid=" + jSessionID + "?pkg=" + getURLs[1] + "&ticket=" + loginTicket,
    			null,
    			"IDMSESSID");
    	
    	cookies.put("IDMSESSID", idmSessID);
    	
    	return cookies;
    }
	
}
