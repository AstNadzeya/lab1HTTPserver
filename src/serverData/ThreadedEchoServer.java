package serverData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadedEchoServer extends Thread{
	
	/** Создание обработчика 
	 * @param i the incoming socket 
	 * @param с the counter for the handlers (used in prompts)
	 * 
	 */
	
	private Socket incoming;
	private int counter;
	
	public ThreadedEchoServer ( Socket i, int c)
	{
		incoming = i; counter = c;
	}
	
	
	public void run()
	{
		try {
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(incoming.getInputStream()));
			PrintWriter out = new PrintWriter(incoming.getOutputStream(), true);

			out.println("Hello! Press BYE to exit");

			boolean done = false;
			while (!done) {

				String str = in.readLine();
				if (str == null)
					done = true;
				else {
					out.println("Echo: " + str);
					if (str.trim().equals("BYE"))
						done = true;
				
					}
				}
				
			incoming.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}

