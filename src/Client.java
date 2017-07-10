import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

	Socket socket = null;
	DataOutputStream os = null;
	DataInputStream is = null;
	
	public Client() {
	}
	
	public boolean setupSocket(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			os = new DataOutputStream(socket.getOutputStream());
            is = new DataInputStream(socket.getInputStream());
            return true;
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
		}
	}
	public Socket getSocket() {
		return this.socket;
	}
	public DataInputStream getData() {
		return this.is;
	}
	public DataOutputStream sendData() {
		return this.os;
	}
	public void close() {		
		try {
			this.socket.close();
			this.is.close();
			this.os.close();
		} catch (IOException e) {}
	}
}
