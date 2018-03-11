package serverData;

import java.io.*;
import java.net.*;
import java.util.Date;


public class HttpServerMain {

	public static void main(String[] args) throws IOException{

		
		ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        while (true) {
            try (Socket socket = server.accept()) {
                Date today = new Date();
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
            }
        }

		
		
//		ServerSocket server = new ServerSocket(99);
//        System.out.println("Listening for connection on port 99 ....");
//        while (true) {
//            Socket clientSocket = server.accept();
//            InputStreamReader isr =  new InputStreamReader(clientSocket.getInputStream());
//            BufferedReader reader = new BufferedReader(isr);
//            String line = reader.readLine();            
//            while (!line.isEmpty()) {
//                System.out.println(line);
//                line = reader.readLine();
//            }
//        }


	}

}



//public class HttpServerMain {
//
//	public static void main(String[] args) {
//
//		try {
//			
//			int i = 1;
//			ServerSocket s = new ServerSocket(80);
//			
//			while(true)
//			{
//				Socket client = s.accept();
//				System.out.println("Spawning: " + i);
//				Thread t = new ThreadedEchoServer(client, i);
//				t.start();
//				i++;
//			}
//
//		}
//		catch (Exception e) {
//
//			e.printStackTrace();
//		}
//
//	}
//
//}


