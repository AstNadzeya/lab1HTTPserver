package serverData;

import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class HttpParser {

	private static final String[][] httpReplies = { { "200", "OK" }, { "202", "Accepted" },
			{ "301", "Moved Permanently" }, { "302", "Found" }, { "307", "Temporary Redirect" },
			{ "400", "Bad Request" }, { "403", "Forbidden" }, { "404", "Not Found" }, { "406", "Not Acceptable" },
			{ "408", "Request Timeout" }, { "410", "Gone" }, { "417", "Expectation Failed" },
			{ "500", "Internal Server Error" }, { "502", "Bad Gateway" }, {"501", "Not Implemented"},
			{ "503", "Sevice Unavailable" },{ "504", "Gateway Timeout" }, { "505", "HTTP Version Not Supported" } };
	private BufferedReader reader;
	private String method, url;
	private Hashtable headers, params;
	private int[] ver;

	public HttpParser(InputStream in) {
		reader = new BufferedReader(new InputStreamReader(in));
		method = "";
		url = "";
		headers = new Hashtable();
		params = new Hashtable();
		ver = new int[2];
	}

	public int parseRequest() throws IOException {
		String initial, prms[], cmd[], temp[];
		int ret, idx, i;

		ret = 200;
		initial = reader.readLine();
		if (initial == null || initial.length() == 0)
			return 0;
		if (Character.isWhitespace(initial.charAt(0))) {
			return 400;
		}

//		cmd = initial.split("\\s");
		cmd = initial.split("\\");
		if (cmd.length != 3) {
			return 400;
		}

		if (cmd[2].indexOf("HTTP/") == 0 && cmd[2].indexOf('.') > 5) {
			temp = cmd[2].substring(5).split(".");
			try {
				ver[0] = Integer.parseInt(temp[0]);
				ver[1] = Integer.parseInt(temp[1]);
			} catch (NumberFormatException nfe) {
				ret = 400;
			}
		} else
			ret = 400;

		if (cmd[0].equals("GET") || cmd[0].equals("HEAD")) {
			method = cmd[0];

			idx = cmd[1].indexOf('?');
			if (idx < 0)
				url = cmd[1];
			else {
				url = URLDecoder.decode(cmd[1].substring(0, idx), "ISO-8859-1");
				prms = cmd[1].substring(idx +1).split("&");
				
				params = new Hashtable();
				for (i = 0; i < prms.length; i++) {
					temp = prms[i].split("=");
					if (temp.length == 2) {
						// we use ISO-8859-1 as temporary charset and then
						// String.getBytes("ISO-8859-1") to get the data
						params.put(URLDecoder.decode(temp[0], "ISO-8859-1"), URLDecoder.decode(temp[1], "ISO-8859-1"));
					} else if (temp.length == 1 && prms[i].indexOf('=') == prms[i].length() - 1) {
						// handle empty string separatedly
						params.put(URLDecoder.decode(temp[0], "ISO-8859-1"), "");
					}
				}
			}
			parseHeaders();
			if (headers == null)
				ret = 400;
		} else if (cmd[0].equals("POST")) {
			ret = 501;
		} else if (ver[0] == 1 && ver[1] >= 1) {
			if (cmd[0].equals("OPTIONS") || cmd[0].equals("PUT") || cmd[0].equals("DELETE") || cmd[0].equals("TRACE")
					|| cmd[0].equals("CONNECT")) {
				ret = 501; // not implemented
			}
		} else {
			ret = 400;
		}

		if (ver[0] == 1 && ver[1] >= 1 && getHeader("Host") == null) {
			ret = 400;
		}

		return ret;
	}

	public void parseHeaders() throws IOException {
		String line;
		int idx;

		line = reader.readLine();
		while (!line.equals("")) {
			idx = line.indexOf(':');
			if (idx < 0) {
				headers = null;
				break;
			} else {
				headers.put(line.substring(0, idx).toLowerCase(), line.substring(idx + 1).trim());
			}
			line = reader.readLine();
		}
	}

	public String getMethod() {
		return method;
	}

	public String getHeader(String key) {
		if (headers != null)
			return (String) headers.get(key.toLowerCase());
		else
			return null;
	}

	public Hashtable getHeaders() {
		return headers;
	}

	public String getRequestURL() {
		return url;
	}

	public String getParam(String key) {
		return (String) params.get(key);
	}

	public Hashtable getParams() {
		return params;
	}

	public String getVersion() {
		return ver[0] + "." + ver[1];
	}

	public int compareVersion(int major, int minor) {
		if (major < ver[0])
			return -1;
		else if (major > ver[0])
			return 1;
		else if (minor < ver[1])
			return -1;
		else if (minor > ver[1])
			return 1;
		else
			return 0;
	}

	public static String getHttpReply(int codevalue) {
		String key, ret;
		int i;

		ret = null;
		key = "" + codevalue;
		for (i = 0; i < httpReplies.length; i++) {
			if (httpReplies[i][0].equals(key)) {
				ret = codevalue + " " + httpReplies[i][1];
				break;
			}
		}

		return ret;
	}
	
	public static String getDateHeader() {
	    SimpleDateFormat format;
	    String ret;

	    format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.UK);
	    format.setTimeZone(TimeZone.getTimeZone("GMT"));
	    ret = "Date: " + format.format(new Date()) + " GMT";

	    return ret;
	  }
	
}
