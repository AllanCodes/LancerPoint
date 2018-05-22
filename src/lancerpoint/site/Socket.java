package lancerpoint.site;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * Contains socket post and get functions.
 * <p>
 */
public class Socket {

    /**
     * Does a https socket post.
     * <p>
     *
     * @param hostname Host name to connect to.
     * @param post     URL to post to.
     * @param cookie   Cookie to use.
     * @param sendData Data to send.
     * @return         Response
     */
    public static ArrayList<String> httpsSocketPost(String hostname, String post, String cookie, String sendData) {
        ArrayList<String> response = new ArrayList<String>();

        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sock = (SSLSocket) factory.createSocket(hostname, 443);

            BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8"));
            buff.write("POST " + post + " HTTP/1.1" + "\r\n");
            buff.write("Host: " + hostname + "\r\n");
            buff.write("Cookie: " + cookie + "\r\n");
            buff.write("Connection: close" + "\r\n");
            buff.write("Content-Type: application/x-www-form-urlencoded" + "\r\n");
            buff.write("Content-Length: " + sendData.length() + "\r\n");

            buff.write("\r\n");
            buff.write(sendData);

            buff.flush();

            BufferedReader read = new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-8"));
            String line = null;

            while ((line = read.readLine()) != null) {
                response.add(line);
            }

            read.close();
            buff.close();
            sock.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * Does a https socket get using the inputed fields.
     * <p>
     *
     * @param hostname Host name for socket to connect to. Assumes port is 443 due to https.
     * @param get      URL we need to get.
     * @param cookie   Cookie used in the get.
     * @return         Response
     * @throws IOException
     */
    public static ArrayList<String> httpsSocketGet(String hostname, String get, String cookie) {
        ArrayList<String> response = new ArrayList<String>();

        SSLSocketFactory factory = null;
        SSLSocket sock = null;

        try {
            factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            sock = (SSLSocket) factory.createSocket(hostname, 443);

            BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8"));
            buff.write("GET " + get + " HTTP/1.1" + "\r\n");
            buff.write("Host: " + hostname + "\r\n");

            if (cookie != null) {
                buff.write("Cookie: " + cookie + "/r/n");
            }

            buff.write("Connection: close" + "\r\n");

            buff.write("\r\n");

            buff.flush();

            BufferedReader read = new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-8"));
            String line = null;

            while ((line = read.readLine()) != null) {
                response.add(line);
            }

             read.close();
             buff.close();
             sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

}