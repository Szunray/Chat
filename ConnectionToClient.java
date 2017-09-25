import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
public class ConnectionToClient {
	public Socket s;
	public Scanner in;
	public PrintWriter out;
	
	public ConnectionToClient(Socket s){
		try {
			in = new Scanner (s.getInputStream());
			out = new PrintWriter(s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
