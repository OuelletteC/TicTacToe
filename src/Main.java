import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		int selection = 0;
		while (selection != 1 && selection != 2) {
			System.out.println("Press 1 to host, 2 to join a host");
			try {
				selection = Integer.parseInt(in.next());
			} 
			catch (InputMismatchException e) {} 
			catch (NumberFormatException e) {}
		}
		
		if (selection == 1) host();
		else join();
	}
	
	public static void host() {
		
		// Showing the host their public IP
		URL whatismyip;
		String ip = "";
		try {
			
			whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader read = new BufferedReader(new InputStreamReader(
	                whatismyip.openStream()));
			ip = read.readLine(); //you get the IP as a String
			System.out.println("Your public IP is: " + ip);
			read.close();
		} catch (MalformedURLException e1) {	
		} catch (IOException e) {}
		
		Server server = new Server();
		Board board = new Board();
		Scanner in = new Scanner(System.in);

		// Asking host for port number to host on
		int port = 0;
		while (!server.setupServer(port)) {
			try {
				System.out.println("Enter the port number you want to host on");
				port = Integer.parseInt(in.next());
			} 
			catch (InputMismatchException e) {} 
			catch (NumberFormatException e) {}
		}
			
		System.out.println("You are hosting on IP: " + ip + ", and port: " + port);
		
		PrintStream os = server.sendData();
		DataInputStream is = server.getData();
		
		int turn = 1;
		
		while (!board.isWin()) {
			if (turn == 0) {
				int selection = 0;
				char c = 'X';
				while (!board.move(selection, c)) {
					System.out.println("Pick a square (1-9)");
					try {
						selection = Integer.parseInt(in.next());
					} 
					catch (InputMismatchException e) {} 
					catch (NumberFormatException e) {}
				}
				// pass to socket
				os.write(selection);
				// print board
				board.printBoard();
			}
			else {
				int selection = 0;
				System.out.println("Waiting for other player to make a move...");
				while (selection == 0) {
					try {
						selection = is.read();
					} catch (IOException e) {}
					if (selection != 0) board.move(selection, 'O');
				}
				// print board
				board.printBoard();
			}
			turn = (turn+1) % 2;
		}
		if (turn == 1) System.out.println("You Win!");
		else System.out.println("You Lose :(");
		server.close();
	}
	
	public static void join() {
		
		Scanner in = new Scanner(System.in);
		Client client = new Client();
		
		String ip = null;
		int port = 0;
		while (!client.setupSocket(ip, port)) {
			try {
				System.out.println("Enter the host IP address");
				ip = in.next();
				System.out.println("Enter the host port number");
				port = Integer.parseInt(in.next());
			} 
			catch (InputMismatchException e) {} 
			catch (NumberFormatException e) {}
		}
		
		Board board = new Board();
		
		DataOutputStream os = client.sendData();
		DataInputStream is = client.getData();
		
		int turn = 0;
		
		while (!board.isWin()) {
			if (turn == 0) {
				int selection = 0;
				char c = 'X';
				while (!board.move(selection, c)) {
					System.out.println("Pick a square (1-9)");
					try {
						selection = Integer.parseInt(in.next());
					} 
					catch (InputMismatchException e) {} 
					catch (NumberFormatException e) {}
				}
				// pass to socket
				try {
					os.writeByte(selection);
				} catch (IOException e) {}
				// print board
				board.printBoard();
			}
			else {
				int selection = 0;
				System.out.println("Waiting for other player to make a move...");
				while (selection == 0) {
					try {
						selection = is.read();
					} catch (IOException e) {}
					if (selection != 0) board.move(selection, 'O');
				}
				// print board
				board.printBoard();
			}
			turn = (turn+1) % 2;
		}
		if (turn == 1) System.out.println("You Win!");
		else System.out.println("You Lose :(");
		client.close();
	}
	
}
