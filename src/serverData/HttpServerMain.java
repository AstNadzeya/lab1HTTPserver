package serverData;

import java.io.*;
import java.net.*;
import java.util.Date;


public class HttpServerMain {

public static void main(String[] args) {


		try {

			int i = 1;
			ServerSocket server = new ServerSocket(8080);
//			ServerSocket server = new ServerSocket(8080, 0, InetAddress.getByName("localhost"));

			while (true) {
				
				System.out.println("Spawning: " + i);
				Thread t = new ThreadedEchoServer(server.accept(), i);
				t.start();
				i++;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}


	}

}

//ServerSocket server = new ServerSocket(99);
//System.out.println("Listening for connection on port 99 ....");
//while (true) {
//Socket clientSocket = server.accept();
//InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
//BufferedReader reader = new BufferedReader(isr);
//String line = reader.readLine();
//while (!line.isEmpty()) {
//System.out.println(line);
//line = reader.readLine();
//}
//}


//class HttpServerMain extends Thread {
//	Socket s;
//	int num;
//
//	public static void main(String args[]) {
//		try {
//			int i = 0; // счётчик подключений
//
//			ServerSocket server = new ServerSocket(8080, 0, InetAddress.getByName("localhost"));
//
//			System.out.println("server is started");
//
//			while (true) {
//
//				new HttpServerMain(i, server.accept());
//				i++;
//			}
//		} catch (Exception e) {
//			System.out.println("init error: " + e);
//		} 
//	}
//
//	public HttpServerMain(int num, Socket s) {
//
//		this.num = num;
//		this.s = s;
//
//		setDaemon(true);
//		setPriority(NORM_PRIORITY);
//		start();
//	}
//
//	public void run() {
//		try {
//
////			InputStream is = s.getInputStream();
////			OutputStream os = s.getOutputStream();
//
//			BufferedReader is = new BufferedReader(
//					new InputStreamReader(s.getInputStream()));
//			PrintWriter os = new PrintWriter(s.getOutputStream(), true);
////			byte buf[];
//
//			while (true) {
////		
//
//				String in = is.readLine();
//				
//								
//				// завершаем соединение
//				if (in.trim().equals("bye")) {
//					s.close();
//
//				}
//
//			}
//
//		} catch (Exception e) {
//			System.out.println("init error: " + e);
//		} 
//	}
//}

// public class HttpServerMain {
//
// public static void main(String[] args) {
//
//
// try {
//
// int i = 1;
// ServerSocket server = new ServerSocket(80);
//
// while (true) {
// Socket client = server.accept();
// System.out.println("Spawning: " + i);
// Thread t = new ThreadedEchoServer(client, i);
// t.start();
// i++;
// }
//
// } catch (Exception e) {
//
// e.printStackTrace();
// }
//
// new Window();
// }
//
// }

// ServerSocket server = new ServerSocket(99);
// System.out.println("Listening for connection on port 99 ....");
// while (true) {
// Socket clientSocket = server.accept();
// InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
// BufferedReader reader = new BufferedReader(isr);
// String line = reader.readLine();
// while (!line.isEmpty()) {
// System.out.println(line);
// line = reader.readLine();
// }
// }
