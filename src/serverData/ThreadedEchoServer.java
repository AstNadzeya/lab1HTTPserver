package serverData;

import java.io.*;
import java.net.Socket;

public class ThreadedEchoServer extends Thread{
	

	
	private Socket client;
	private int counter;
	
	public ThreadedEchoServer ( Socket i, int c)
	{
		client = i; counter = c;
	}
	
	
	public void run()
	{
		try {
			
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(client.getInputStream()));

			InputStream is = client.getInputStream();
			HttpParser request = new HttpParser(is);
			
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			

//			out.println("Hello! Write something...");

			boolean done = false;
			while (!done) {

		
				String str = in.readLine();
				if (str == null)
					done = true;
				else {
					int rec = request.parseRequest();
					out.println(request.getHttpReply(rec));
					out.println(request.getDateHeader());
					
//					out.println("Echo: " + str);
					out.println(str);
					if (str.trim().equals("bye"))
						done = true;
				
					}
				}
				
			client.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}

