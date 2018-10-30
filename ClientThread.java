/**
 * Client code for simple chat over sockets program
 * @author Wojciech Rozowski (wkr1u18)
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread{
	private Socket clientSocket = null;
	private ClientMain masterClient = null;
	private BufferedReader in = null;
	
	//Constructor taking reference to ClientMain object and to Socket object
	public ClientThread(ClientMain initialMasterClient, Socket initialClientSocket) {
		//Initialises the instance objects
		masterClient = initialMasterClient;
		clientSocket = initialClientSocket;
		open();
		start();
	}
	
	//Opens the Buffered Reader object
	public void open() {
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
		}
		catch (IOException ioe) {
			System.out.println(ioe);
		}
	}
	
	//Closes the Buffered Reader object
	public void close() {
		try {
			if(in!=null) {
				in.close();
			}
		}
		catch (IOException ioe) {
			System.out.println(ioe);
		}
	}
	

	
	public void run() {
		System.out.println("Client thread is running");
		while(true) {
			try {
				masterClient.handle(in.readLine());
			}
			catch (Exception e) {
				System.out.println(e);
				masterClient.stop();
			}
		}
	}
}
