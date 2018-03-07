package serverData;

import java.io.*;
import java.net.*;

public class HttpServerMain {

	public static void main(String[] args) {

		try {
			
			int i = 1;
			ServerSocket s = new ServerSocket(8080);
			
			while(true)
			{
				Socket incoming = s.accept();
				System.out.println("Spawning: " + i);
				Thread t = new ThreadedEchoServer(incoming, i);
				t.start();
				i++;
			}

		}
		catch (Exception e) {

			e.printStackTrace();
		}

	}

}