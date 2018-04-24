package serverData;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.awt.event.*;
import javax.swing.*;


public class HttpServerFrame extends JFrame{
	
	private BufferedReader in;
	private PrintWriter out;
	private JTextField from;
	private JTextField to;
	private JTextField httpServer;
	private JTextArea req;
	private JTextArea resp;
	
	public static final int WIDTH = 300;
	public static final int HEIGHT = 300;
	
	public HttpServerFrame(){
		setSize(WIDTH, HEIGHT);
		setTitle("HTTP server");
		
		getContentPane().setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0;
		gbc.weighty = 0;
		
		gbc.weightx = 0;
		add(new JLabel("HTTP server: "), gbc, 0, 2, 1, 1);
		gbc.weightx = 100;
		httpServer = new JTextField(20);
		add(httpServer, gbc, 1, 2, 1, 1);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 100;
		req = new JTextArea();
		add(new JScrollPane(req), gbc, 0, 3, 2, 1);
		
		resp = new JTextArea();
		add(new JScrollPane(resp), gbc, 0, 4, 2, 1);
		
		gbc.weighty = 0;
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				new Thread(){
					public void run(){
						sendResponse();
					}
				}.start();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(sendButton);
		add(buttonPanel, gbc, 0, 5, 2, 1);
		
	}
	
	private void add(Component c, GridBagConstraints gbc, int x, int y, int w, int h){
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		getContentPane().add(c, gbc);
		
	}
	
	
	////////закинуть  GET////////////////
	public void sendResponse(){
		try{
			Socket s = new Socket(httpServer.getText(), 8080);
			out = new PrintWriter(s.getOutputStream());
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			String hostName = InetAddress.getLocalHost().getHostName();
			
			recieve();
			send("Hello " + hostName);
			recieve();
			StringTokenizer tokenizer = new StringTokenizer(req.getText(), "\n");
			
		}catch(IOException e){
			resp.append("Error: " + e);
		}
	}
	
	public void send(String s) throws IOException{
		resp.append(s);
		resp.append("\n");
		out.print(s);
		out.print("\r\n");
		out.flush();
	}
	
	public void recieve() throws IOException{
		String line = in.readLine();
		if (line != null){
			resp.append(line);
			resp.append("\n");
		}
	}
	/////////////////////////////////////
	
}
