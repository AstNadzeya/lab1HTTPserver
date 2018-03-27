package serverData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadedEchoServer extends Thread{
	
	/** Создание обработчика 
	 * @param i the incoming socket 
	 * @param с the counter for the handlers (used in prompts)
	 * 
	 */
	
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
			

			out.println("Hello! Write something...");

			boolean done = false;
			while (!done) {

		
				String str = in.readLine();
				if (str == null)
					done = true;
				else {
					int rec = request.parseRequest();
					out.print(request.getHttpReply(rec));
					
					
					out.println("Echo: " + str);
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

