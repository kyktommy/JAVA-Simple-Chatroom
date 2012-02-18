import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class Connection implements Runnable {
	
	Socket socket;
	ChatServer chatServer;
	String heading;
	
	public Connection(Socket socket, ChatServer chatServer) {
		this.socket = socket;
		this.chatServer = chatServer;
		this.heading = socket.getInetAddress().getHostAddress() + ":" +socket.getPort();
	}

	@Override
	public void run() {
		
		try {
			//stream for receive
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			for(;;) {
				line = br.readLine();
				if(line == null) break;
				
				this.chatServer.chatRoom.appendToTextArea(line);
				this.chatServer.broadcast(this.socket, line);
			}
			
			//close 
			br.close();
			this.socket.close();
			this.chatServer.removeSocket(socket);
			
		} catch (IOException ex) {
			System.err.println(ex);
		}
		
	}
}
