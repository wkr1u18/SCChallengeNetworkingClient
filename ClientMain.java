/**
 * Client code for simple chat over sockets program
 * @author Wojciech Rozowski (wkr1u18)
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain implements Runnable {
	private Socket clientSocket = null;
	private Thread t = null;
	private Scanner consoleInput = null;
	private PrintWriter out = null;
	private ClientThread client = null;
	
	public ClientMain(String hostName, int portNumber) {
		System.out.println("Connecting to server...");
		try {
			//Opens a socket
			clientSocket = new Socket(hostName, portNumber);
			System.out.println("Succesfully connected to server.");
			start();
		}
		catch (IOException ioe) {
			System.out.println(ioe);
		}
	}
	
	public void start() throws IOException{
		consoleInput = new Scanner(System.in);
		//Creates output PrintWriter and input BufferedReader objects using Socket methods
		out = new PrintWriter(clientSocket.getOutputStream(),true);
		if (t==null) {
			//creates new ClientThread object and initialises it 
			client = new ClientThread(this, clientSocket);
			t = new Thread(this);
			t.start();
		}
	}
	
	public void stop() {
		System.out.println("Stopping client. Press any key to quit...");
		//Close thread
		if(t != null) {
			t.stop();
			t = null;
		}
		try {
			//Closes consoleInput Scanner
			if (consoleInput != null) {
				consoleInput.close();
			}
			
			//Closes out PrintWriter
			if (out != null) {
				out.close();
			}
			
			//Closes clientScoket Socket
			if(clientSocket!=null) {
				clientSocket.close();
			}
		}
		catch(IOException ioe) {
			System.out.println(ioe);
		}
		//Closes the ClientThread
		client.close();
		System.exit(0);
	}
	
	public void handle(String msg) {
		if(msg.equals(".quitconfirm")) {
			stop();
		}
		else {
			System.out.println(msg);
		}
	}
	
	//Thread run mehod
	public void run() {
		while (t!=null) {
			try {
				//Send the input from consoleInput Scanner object
				out.println(consoleInput.nextLine());
			}
			catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner textIn = new Scanner(System.in);
		System.out.println("Welcome to client. Please specify host name and port number");
		ClientMain myClient = new ClientMain(textIn.nextLine(), textIn.nextInt());
	}
}