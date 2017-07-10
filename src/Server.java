import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	ServerSocket server;
	Socket socket;
	PrintStream os;
	DataInputStream is;
	
	public Server() {
	}
	public boolean setupServer(int port) {
		if (port == 0) return false;
		try {
			server = new ServerSocket(port);
			socket = server.accept();
			is = new DataInputStream(socket.getInputStream());
			os = new PrintStream(socket.getOutputStream());
			return true;
		}
		catch (IOException e) {
			return false;
		}
	}
	public DataInputStream getData() {
		return this.is;
	}
	public PrintStream sendData() {
		return this.os;
	}
	public void close() {
		try {
			this.socket.close();
			this.is.close();
			this.os.close();
			this.server.close();
		} catch (IOException e) {}
	}
}
