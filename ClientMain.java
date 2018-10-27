/**
 * Server code for simple chat over sockets program
 * @author Wojciech Rozowski (wkr1u18)
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
	public static void main(String[] args) {
		//Get host name and port number
		Scanner myScanner = new Scanner(System.in);
		String hostName;
		int port;
		System.out.println("Enter host name:");
		hostName = myScanner.nextLine();
		System.out.println("Enter port number");
		port = myScanner.nextInt();
		
		try {
			//Opens a socket
			Socket mySocket = new Socket(hostName,port);
			//Creates output PrintWriter and input BufferedReader objects using Socket methods
			PrintWriter out = new PrintWriter(mySocket.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
			String userInput;
			//While user types types next line, send it to server
			while((userInput = myScanner.nextLine())!=null) {
				out.println(userInput);
				System.out.println("echo: " + in.readLine());
			}
		}
		catch (Exception e) {
			System.out.println("Unhandled exception");
		}
	}
}
